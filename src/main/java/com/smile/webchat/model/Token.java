/**   
* @Title: Token.java
* @Package: com.smile.webchat.model
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 上午10:47:53
*
*/
package com.smile.webchat.model;

/**
* @ClassName: Token
* @Description: 凭证
* @author: 杨辉
* @date: 2017年6月20日 上午10:47:53
* 
*/
public class Token {

	/**
	 *  接口访问凭证
	 */
    private String accessToken;
    
    /** 
     * 凭证有效期，单位：秒
     * 
     */
    private int expiresIn;
    
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	
	@Override
	public String toString() {
		return "Token [accessToken=" + accessToken + ", expiresIn=" + expiresIn + "]";
	}
    
    
    
}
