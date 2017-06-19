/**   
* @Title: TextResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午5:50:19
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: TextResponseMessage
* @Description: 文本消息
* @author: 杨辉
* @date: 2017年6月19日 下午5:50:19
* 
*/
public class TextResponseMessage extends BaseResponseMessage {

	 /**
	  *  回复的消息内容
	  */
    private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
    
    
    
}
