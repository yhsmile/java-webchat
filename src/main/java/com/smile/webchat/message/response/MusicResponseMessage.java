/**   
* @Title: MusicResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 上午10:02:21
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: MusicResponseMessage
* @Description: 音乐消息
* @author: 杨辉
* @date: 2017年6月20日 上午10:02:21
* 
*/
public class MusicResponseMessage extends BaseResponseMessage {

	 // 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
