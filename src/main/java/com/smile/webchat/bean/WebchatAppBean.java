/**   
* @Title: WebchatAppBean.java
* @Package: com.smile.webchat.bean
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 下午5:46:58
*
*/
package com.smile.webchat.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* @ClassName: WebchatAppBean
* @Description: 
* @author: 杨辉
* @date: 2017年6月20日 下午5:46:58
* 
*/
@Component
public class WebchatAppBean {
	
	/**
	 * 微信公众平台生成的 appID
	 */
	@Value("${webchat_app_id}")
	private String appID;
	
	/**
	 * 微信公众平台生成的 appsecret
	 */
	@Value("${webchat_app_secret}")
	private String appsecret;

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	
	
	
	
	

}
