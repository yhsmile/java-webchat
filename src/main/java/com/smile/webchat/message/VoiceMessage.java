/**   
* @Title: VoiceMessage.java
* @Package: com.smile.webchat.message
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����3:23:13
*
*/
package com.smile.webchat.message;

/**
* @ClassName: VoiceMessage
* @Description: ������Ϣ֮������Ϣ
* @author: ���
* @date: 2017��6��19�� ����3:23:13
* 
*/
public class VoiceMessage extends BaseMessage {

	/**
	 * ý��ID
	 */
    private String MediaId;
    /**
     * ������ʽ
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
