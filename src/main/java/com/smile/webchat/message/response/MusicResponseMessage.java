/**   
* @Title: MusicResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����10:02:21
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: MusicResponseMessage
* @Description: ������Ϣ
* @author: ���
* @date: 2017��6��20�� ����10:02:21
* 
*/
public class MusicResponseMessage extends BaseResponseMessage {

	 // ����
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
