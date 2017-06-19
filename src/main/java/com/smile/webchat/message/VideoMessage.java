/**   
* @Title: VideoMessage.java
* @Package: com.smile.webchat.message
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午3:24:28
*
*/
package com.smile.webchat.message;

/**
* @ClassName: VideoMessage
* @Description: 请求消息之视频消息
* @author: 杨辉
* @date: 2017年6月19日 下午3:24:28
* 
*/
public class VideoMessage extends BaseMessage {

	/**
	 * 媒体ID
	 */
	private String MediaId;
    /** 
     * 语音格式
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
