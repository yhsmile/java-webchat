/**   
* @Title: Token.java
* @Package: com.smile.webchat.model
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����10:47:53
*
*/
package com.smile.webchat.model;

/**
* @ClassName: Token
* @Description: ƾ֤
* @author: ���
* @date: 2017��6��20�� ����10:47:53
* 
*/
public class Token {

	/**
	 *  �ӿڷ���ƾ֤
	 */
    private String accessToken;
    
    /** 
     * ƾ֤��Ч�ڣ���λ����
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
