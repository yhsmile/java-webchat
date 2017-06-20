/**   
* @Title: WebchatAddressBean.java
* @Package: com.smile.webchat.bean
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����5:46:28
*
*/
package com.smile.webchat.bean;

import org.springframework.beans.factory.annotation.Value;

/**
* @ClassName: WebchatAddressBean
* @Description: 
* @author: ���
* @date: 2017��6��20�� ����5:46:28
* 
*/
public class WebchatAddressBean {
	
	/**
	 * ��ȡaccess_token�Ľӿڵ�ַ
	 */
	@Value("${token_url}")
	private String tokenUrl;
	
	/**
	 * �˵�������POST����ַ
	 */
	@Value("${menu_create_url}")
	private String menuCreateUrl;
	
	/**
	 * ��ȡ�û�������Ϣ��ַ
	 */
	@Value("${user_info_url}")
	private String userInfoUrl;
	
	/**
	 * ��ҳ��Ȩ��ַ
	 */
	@Value("${web_auto_url}")
	private String webAutoUrl;
	
	/**
	 * ��ҳ��Ȩ��ȡ�û�������Ϣ��ַ
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
