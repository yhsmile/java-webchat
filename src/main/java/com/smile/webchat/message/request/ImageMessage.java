/**   
* @Title: ImageMessage.java
* @Package: com.smile.webchat.message
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����3:21:10
*
*/
package com.smile.webchat.message.request;

/**
* @ClassName: ImageMessage
* @Description: ������Ϣ֮ͼƬ��Ϣ
* @author: ���
* @date: 2017��6��19�� ����3:21:10
* 
*/
public class ImageMessage extends BaseMessage {
	
	/**
	 * ͼƬ����
	 */
	private String PicUrl;
	
	/**
	 * ͼƬ��Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ���ݡ�
	 */
	private String MediaId;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
	
	

}
