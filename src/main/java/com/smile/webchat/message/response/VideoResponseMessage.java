/**   
* @Title: VideoResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����5:54:44
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: VideoResponseMessage
* @Description: ��Ƶ��Ϣ
* @author: ���
* @date: 2017��6��19�� ����5:54:44
* 
*/
public class VideoResponseMessage extends BaseResponseMessage {

	// ��Ƶ
    private Video Video;

    public Video getVideo() {
        return Video;
    }

    public void setVideo(Video video) {
        Video = video;
    }
}
