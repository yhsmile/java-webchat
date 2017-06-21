/**   
* @Title: ViewButton.java
* @Package: com.smile.webchat.menu
* @Description: 
* @author: ���  
* @date: 2017��6��21�� ����6:07:06
*
*/
package com.smile.webchat.menu;

/**
* @ClassName: ViewButton
* @Description: view ���͵� button
* @author: ���
* @date: 2017��6��21�� ����6:07:06
* 
*/
public class ViewButton extends Button {
	
	private String type;
	
	private String url;
	
	private String appId;
	
	private String pagePath;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	
	
	

}
