/**   
* @Title: WebchatAddressBean.java
* @Package: com.smile.webchat.bean
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 下午5:46:28
*
*/
package com.smile.webchat.bean;

import org.springframework.beans.factory.annotation.Value;

/**
* @ClassName: WebchatAddressBean
* @Description: 
* @author: 杨辉
* @date: 2017年6月20日 下午5:46:28
* 
*/
public class WebchatAddressBean {
	
	/**
	 * 获取access_token的接口地址
	 */
	@Value("${token_url}")
	private String tokenUrl;
	
	/**
	 * 菜单创建（POST）地址
	 */
	@Value("${menu_create_url}")
	private String menuCreateUrl;
	
	/**
	 * 获取用户基本信息地址
	 */
	@Value("${user_info_url}")
	private String userInfoUrl;
	
	/**
	 * 网页授权地址
	 */
	@Value("${web_auto_url}")
	private String webAutoUrl;
	
	/**
	 * 网页授权获取用户基本信息地址
	 */
	@Value("${web_auto_user_info_url}")
	private String webAutoUserInfoUrl;
	

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public String getMenuCreateUrl() {
		return menuCreateUrl;
	}

	public void setMenuCreateUrl(String menuCreateUrl) {
		this.menuCreateUrl = menuCreateUrl;
	}

	public String getUserInfoUrl() {
		return userInfoUrl;
	}

	public void setUserInfoUrl(String userInfoUrl) {
		this.userInfoUrl = userInfoUrl;
	}

	public String getWebAutoUrl() {
		return webAutoUrl;
	}

	public void setWebAutoUrl(String webAutoUrl) {
		this.webAutoUrl = webAutoUrl;
	}

	public String getWebAutoUserInfoUrl() {
		return webAutoUserInfoUrl;
	}

	public void setWebAutoUserInfoUrl(String webAutoUserInfoUrl) {
		this.webAutoUserInfoUrl = webAutoUserInfoUrl;
	}
	
	
	
	
	

}
