//package org.sz.mbay.wallet.constant;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.androidpn.server.xmpp.push.Message;
//import org.joda.time.DateTime;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.sz.mbay.message.push.client.XmppClient;
//import org.sz.mbay.wallet.constant.Global.XmppConfig;
//import org.sz.mbay.wallet.context.SpringApplicationContext;
//import org.sz.mbay.wallet.context.WalletConfig;
//import org.sz.mbay.wallet.user.service.UserService;
//
///**
// * 消息推送中心
// * 
// * @author jerry
// */
//@Component("MessagePushCenter")
//public class MessagePushCenter {
//	
//	private static final Logger LOGGER = LoggerFactory.getLogger(MessagePushCenter.class);
//	
//	private static final XmppClient client = new XmppClient(XmppConfig.URL, XmppConfig.APPLICATION, XmppConfig.APIKEY);
//	
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private WechatTicketService exchangeTicketService;
//	
//	public static MessagePushCenter getInstance() {
//		return (MessagePushCenter) SpringApplicationContext.getBean(MessagePushCenter.class.getSimpleName());
//	}
//	
//	/**
//	 * 券包已使用推送消息
//	 * 
//	 * @param recieverTel
//	 * @param eventName
//	 * @param userTel
//	 * @param amount
//	 */
//	public static void pushTicketUsedMsg(
//			String recieverTel,
//			String eventName,
//			String userTel,
//			BigDecimal amount) {
//		final Message message = new Message();
//		message.setMessageCategory(1);
//		message.setUsername(recieverTel);
//		message.setTitle("券包已被使用");
//		StringBuffer sb = new StringBuffer();
//		sb.append("您分享的【")
//				.append(eventName)
//				.append("】兑换券已被用户【")
//				.append(userTel)
//				.append("】成功使用，返利")
//				.append(amount)
//				.append("美贝已存入您的红包账户中");
//		message.setContent(sb.toString());
//		
//		// 开线程发送
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				try {
//					client.pushUnicastMessage(message);
//				} catch (Exception e) {
//					LOGGER.error("MessagePushCenter pushTicketUsedMsg error:" + e.getMessage());
//				}
//			}
//		}).start();
//	}
//	
//	/**
//	 * 发送券包即将过期的推送消息
//	 */
//	public void pushTicketBeAboutToExpireMsg(final String userNumber) {
//		// 开线程发送
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				int expireDays = Integer.parseInt(WalletConfig.getProperty(
//						WalletConfig.TICKET_WILL_EXPIRE_REMIND_DAYS));
//				WechatTicketQO query = new WechatTicketQO();
//				query.setOwnerNumber(userNumber);
//				query.setUsed(false);
//				List<WechatTicket> list = exchangeTicketService.findPage(query, null);
//				DateTime endDate;
//				for (WechatTicket ticket : list) {
//					endDate = ticket.getWeChatCampaign().getEndingTime();
//					if (DateTime.now().isBefore(endDate) 
//							&& DateTime.now().plusDays(expireDays).isAfter(endDate)) {
//						Message message = new Message();
//						message.setMessageCategory(1);
//						message.setUsername(userService.getTelephoneByUserNumber(ticket.getTicket().getOwnerNumber()));
//						message.setTitle("券包即将过期");
//						StringBuffer sb = new StringBuffer();
//						sb.append("券包【")
//								.append(ticket.getWeChatCampaign().getEventName())
//								.append("】即将于")
//								.append(ticket.getWeChatCampaign().getEndingTime().toString("yyyy-MM-dd"))
//								.append("过期，请尽快使用！（您还可以分享给小伙伴使用哟^_^）");
//						message.setContent(sb.toString());
//						
//						try {
//							client.pushUnicastMessage(message);
//						} catch (Exception e) {
//							LOGGER.error("MessagePushCenter pushTicketBeAboutToExpireMsg error:" + e.getMessage());
//						}
//					}
//				}
//			}
//		}).start();
//	}
//}
