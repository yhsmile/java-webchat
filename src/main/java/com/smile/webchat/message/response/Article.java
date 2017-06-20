/**   
* @Title: Article.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 上午10:02:44
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: Article
* @Description: 图文model
* @author: 杨辉
* @date: 2017年6月20日 上午10:02:44
* 
*/
public class Article {

	/**
	 *  图文消息名称
	 */
    private String Title;
    /**
     *  图文消息描述
     */
    private String Description;
    /**
     *  图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
     */
    private String PicUrl;
    /**
     *  点击图文消息跳转链接
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
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
    
    
    
}
