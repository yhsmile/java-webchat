/**   
* @Title: WebchatAppBean.java
* @Package: com.smile.webchat.bean
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����5:46:58
*
*/
package com.smile.webchat.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* @ClassName: WebchatAppBean
* @Description: 
* @author: ���
* @date: 2017��6��20�� ����5:46:58
* 
*/
@Component
public class WebchatAppBean {
	
	/**
	 * ΢�Ź���ƽ̨���ɵ� appID
	 */
	@Value("${webchat_app_id}")
	private String appID;
	
	/**
	 * ΢�Ź���ƽ̨���ɵ� appsecret
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
