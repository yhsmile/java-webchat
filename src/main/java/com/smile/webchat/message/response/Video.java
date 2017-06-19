/**   
* @Title: Video.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午5:53:59
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: Video
* @Description: 视频
* @author: 杨辉
* @date: 2017年6月19日 下午5:53:59
* 
*/
public class Video {

	/**
	 *  媒体文件id
	 */
    private String MediaId;
    /**
     *  缩略图的媒体id
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
