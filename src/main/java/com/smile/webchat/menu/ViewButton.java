/**   
* @Title: ViewButton.java
* @Package: com.smile.webchat.menu
* @Description: 
* @author: 杨辉  
* @date: 2017年6月21日 下午6:07:06
*
*/
package com.smile.webchat.menu;

/**
* @ClassName: ViewButton
* @Description: view 类型的 button
* @author: 杨辉
* @date: 2017年6月21日 下午6:07:06
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
