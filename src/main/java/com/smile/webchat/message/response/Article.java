/**   
* @Title: Article.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����10:02:44
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: Article
* @Description: ͼ��model
* @author: ���
* @date: 2017��6��20�� ����10:02:44
* 
*/
public class Article {

	/**
	 *  ͼ����Ϣ����
	 */
    private String Title;
    /**
     *  ͼ����Ϣ����
     */
    private String Description;
    /**
     *  ͼƬ���ӣ�֧��JPG��PNG��ʽ���Ϻõ�Ч��Ϊ��ͼ640*320��Сͼ80*80
     */
    private String PicUrl;
    /**
     *  ���ͼ����Ϣ��ת����
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
