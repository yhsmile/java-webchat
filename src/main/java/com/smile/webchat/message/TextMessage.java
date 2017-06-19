/**   
* @Title: TextMessage.java
* @Package: com.smile.webchat.message
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午3:19:46
*
*/
package com.smile.webchat.message;

/**
* @ClassName: TextMessage
* @Description: 请求消息之文本消息
* @author: 杨辉
* @date: 2017年6月19日 下午3:19:46
* 
*/
public class TextMessage extends BaseMessage {
	
	/**
	 * 文本消息内容
	 */
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
	

}
