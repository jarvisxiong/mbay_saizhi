package org.sz.mbay.channel.bean;

import java.io.Serializable;

/**
 * 图片管理实体
 * @author Frank
 *
 */
public class ChannelDynamic implements Serializable{

	private static final long serialVersionUID = 9142855093627557973L;
	private int id; //id
    private String createTime; //创建时间
    private DynamicItem category; //种类名称: 1->上方导航图片          2->媒体报道        3->常见问题         4->直通车状态
    private String username; //创建人
    private String url; //图片地址
    private String link; //图片链接地址
    private String description; //图片描述
    private String name; //图片名称
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public DynamicItem getCategory() {
		return category;
	}
	public void setCategory(DynamicItem category) {
		this.category = category;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}