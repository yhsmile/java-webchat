/**   
* @Title: TextResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����5:50:19
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: TextResponseMessage
* @Description: �ı���Ϣ
* @author: ���
* @date: 2017��6��19�� ����5:50:19
* 
*/
public class TextResponseMessage extends BaseResponseMessage {

	 /**
	  *  �ظ�����Ϣ����
	  */
    private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
    
    
    
}
