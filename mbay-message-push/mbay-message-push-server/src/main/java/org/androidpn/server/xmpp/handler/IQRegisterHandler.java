/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.xmpp.handler;

import gnu.inet.encoding.Stringprep;
import gnu.inet.encoding.StringprepException;

import org.androidpn.server.context.SpringApplicationContext;
import org.androidpn.server.exception.UserExistsException;
import org.androidpn.server.exception.UserNotFoundException;
import org.androidpn.server.model.User;
import org.androidpn.server.service.UserService;
import org.androidpn.server.service.impl.UserServiceImpl;
import org.androidpn.server.util.CRC32Util;
import org.androidpn.server.xmpp.UnauthorizedException;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.PacketError;

/** 
 * This class is to handle the TYPE_IQ jabber:iq:register protocol.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class IQRegisterHandler extends IQHandler {
	
	private static final Log log = LogFactory.getLog(IQRegisterHandler.class);

    private static final String NAMESPACE = "jabber:iq:register";

    private UserService userService;

    private Element probeResponse;

    /**
     * Constructor.
     */
    public IQRegisterHandler() {
        probeResponse = DocumentHelper.createElement(QName.get("query", NAMESPACE));
        probeResponse.addElement("username");
        probeResponse.addElement("password");
        userService = SpringApplicationContext.getBeanByDefaultName(UserServiceImpl.class);
    }

    /**
     * Handles the received IQ packet.
     * 
     * @param packet the packet
     * @return the response to send back
     * @throws UnauthorizedException if the user is not authorized
     */
    public IQ handleIQ(IQ packet) throws UnauthorizedException {
        IQ reply = null;

        ClientSession session = sessionManager.getSession(packet.getFrom());
        if (session == null) {
            log.error("Session not found for key " + packet.getFrom());
            reply = IQ.createResultIQ(packet);
            reply.setChildElement(packet.getChildElement().createCopy());
            reply.setError(PacketError.Condition.internal_server_error);
            return reply;
        }

        if (IQ.Type.get.equals(packet.getType())) {
            reply = IQ.createResultIQ(packet);
            if (session.getStatus() == Session.STATUS_AUTHENTICATED) {
                // TODO
            } else {
                reply.setTo((JID) null);
                reply.setChildElement(probeResponse.createCopy());
            }
        } else if (IQ.Type.set.equals(packet.getType())) {
            try {
                Element query = packet.getChildElement();
                if (query.element("remove") != null) {
                    if (session.getStatus() == Session.STATUS_AUTHENTICATED) {
                        // TODO
                    } else {
                        throw new UnauthorizedException();
                    }
                } else {
                    String userId = query.elementText("username").trim();
                    String password = query.elementText("password");
                    String application = query.elementText("application");
                    String username = CRC32Util.getResult(userId + "/" + application);

                    // Verify the username                 
                    if (username != null) {
                    	Stringprep.nodeprep(username);
                    }

                    // Deny registration of users with no password
                    if (password == null || password.trim().length() == 0) {
                        reply = IQ.createResultIQ(packet);
                        reply.setChildElement(packet.getChildElement()
                                .createCopy());
                        reply.setError(PacketError.Condition.not_acceptable);
                        return reply;
                    }

                    if (application != null && application.matches("\\s*")) {
                    	application = null;
                    }

                    User user;
                    if (session.getStatus() == Session.STATUS_AUTHENTICATED) {
                        user = userService.getUserByUsername(application, session.getUsername());
                    } else {
                    	user = userService.getUserByUsername(application, username);
                        if (user == null) {
                        	user = new User();
                        }
                    }
                    user.setUsername(username);
                    user.setUserId(userId);
                    user.setPassword(password);
                    user.setApplication(application);
                    userService.saveUser(user);

                    reply = IQ.createResultIQ(packet);
                }
            } catch (Exception ex) {
                log.error(ex);
                reply = IQ.createResultIQ(packet);
                reply.setChildElement(packet.getChildElement().createCopy());
                if (ex instanceof UserExistsException) {
                    reply.setError(PacketError.Condition.conflict);
                } else if (ex instanceof UserNotFoundException) {
                    reply.setError(PacketError.Condition.bad_request);
                } else if (ex instanceof StringprepException) {
                    reply.setError(PacketError.Condition.jid_malformed);
                } else if (ex instanceof IllegalArgumentException) {
                    reply.setError(PacketError.Condition.not_acceptable);
                } else {
                    reply.setError(PacketError.Condition.internal_server_error);
                }
            }
        }

        // Send the response directly to the session
        if (reply != null) {
            session.process(reply);
        }
        return null;
    }

    /**
     * Returns the namespace of the handler.
     * 
     * @return the namespace
     */
    public String getNamespace() {
        return NAMESPACE;
    }

}
