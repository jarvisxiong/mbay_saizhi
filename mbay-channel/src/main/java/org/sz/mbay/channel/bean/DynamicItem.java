package org.sz.mbay.channel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 种类管理实体
 * @author Frank
 *
 */
public class DynamicItem implements Serializable{

	private static final long serialVersionUID = 9142855093627557973L;
    private String name; //名称
    private int id;
    private List<ChannelDynamic> channelDynamics;//直通车动态表

	public List<ChannelDynamic> getChannelDynamics() {
        return channelDynamics;
    }

    public void setChannelDynamics(List<ChannelDynamic> channelDynamics) {
        this.channelDynamics = channelDynamics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}