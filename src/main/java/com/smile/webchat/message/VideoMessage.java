/**   
* @Title: VideoMessage.java
* @Package: com.smile.webchat.message
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����3:24:28
*
*/
package com.smile.webchat.message;

/**
* @ClassName: VideoMessage
* @Description: ������Ϣ֮��Ƶ��Ϣ
* @author: ���
* @date: 2017��6��19�� ����3:24:28
* 
*/
public class VideoMessage extends BaseMessage {

	/**
	 * ý��ID
	 */
	private String MediaId;
    /** 
     * ������ʽ
     */
    private String ThumbMediaId;
    
    
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
    
    
    
}
