/**   
* @Title: Video.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����5:53:59
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: Video
* @Description: ��Ƶ
* @author: ���
* @date: 2017��6��19�� ����5:53:59
* 
*/
public class Video {

	/**
	 *  ý���ļ�id
	 */
    private String MediaId;
    /**
     *  ����ͼ��ý��id
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
