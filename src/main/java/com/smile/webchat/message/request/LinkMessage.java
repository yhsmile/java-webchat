/**   
* @Title: LinkMessage.java
* @Package: com.smile.webchat.message
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午3:31:25
*
*/
package com.smile.webchat.message.request;

/**
* @ClassName: LinkMessage
* @Description: 请求消息之链接消息
* @author: 杨辉
* @date: 2017年6月19日 下午3:31:25
* 
*/
public class LinkMessage extends BaseMessage {
	
	 /**
	  *  消息标题
	  */
    private String Title;
    /**
     *  消息描述
     */
    private String Description;
    /**
     *  消息链接
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
