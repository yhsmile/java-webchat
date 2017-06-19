/**   
* @Title: VoiceMessage.java
* @Package: com.smile.webchat.message
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午3:23:13
*
*/
package com.smile.webchat.message;

/**
* @ClassName: VoiceMessage
* @Description: 请求消息之语音消息
* @author: 杨辉
* @date: 2017年6月19日 下午3:23:13
* 
*/
public class VoiceMessage extends BaseMessage {

	/**
	 * 媒体ID
	 */
    private String MediaId;
    /**
     * 语音格式
     */
    private String Format;
    
    
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
    
    
    
}
