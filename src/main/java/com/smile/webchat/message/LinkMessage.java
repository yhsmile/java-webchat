/**   
* @Title: LinkMessage.java
* @Package: com.smile.webchat.message
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����3:31:25
*
*/
package com.smile.webchat.message;

/**
* @ClassName: LinkMessage
* @Description: ������Ϣ֮������Ϣ
* @author: ���
* @date: 2017��6��19�� ����3:31:25
* 
*/
public class LinkMessage extends BaseMessage {
	
	 /**
	  *  ��Ϣ����
	  */
    private String Title;
    /**
     *  ��Ϣ����
     */
    private String Description;
    /**
     *  ��Ϣ����
     */
    private String Url;
    
    
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
    
    
    

}