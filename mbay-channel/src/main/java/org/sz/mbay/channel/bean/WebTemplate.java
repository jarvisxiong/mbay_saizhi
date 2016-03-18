package org.sz.mbay.channel.bean;

import java.io.Serializable;

/**
 * @Description:网页模板表
 * @author han.han
 * @date 2014-8-18 下午10:57:55 
 *
 */
@SuppressWarnings("serial")
public class WebTemplate implements Serializable {
	
	/*唯一标识ID**/
	private int  id;
	
	/*网页类型***/
	private int webtype;
	
	/*网页访问地址**/
	private String url;
	
	/*网页缩略图图片地址***/
	private String imageurl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWebtype() {
		return webtype;
	}

	public void setWebtype(int webtype) {
		this.webtype = webtype;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	
}
