package org.sz.mbay.channel.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: AppDeployInfo
 * @Description: 封装了应用部署信息，对应与数据库中的app_deploy_info
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * @date 2014年11月25日 下午12:12:21
 * 
 */
@SuppressWarnings("serial")
public class AppDeployInfo implements Serializable {
    /** 服务名称 **/
    private String name;
    /** 服务中文名称 **/
    private String text;
    /** 服务部署的ip位置 **/
    private String ip;
    /** 服务部署的端口 **/
    private int port;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public String getIp() {
	return ip;
    }

    public void setIp(String ip) {
	this.ip = ip;
    }

    public int getPort() {
	return port;
    }

    public void setPort(int port) {
	this.port = port;
    }

}
