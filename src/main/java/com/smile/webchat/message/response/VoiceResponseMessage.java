/**   
* @Title: VoiceResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午5:53:37
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: VoiceResponseMessage
* @Description: 
* @author: 杨辉
* @date: 2017年6月19日 下午5:53:37
* 
*/
public class VoiceResponseMessage extends BaseResponseMessage {

	// 语音
    private Voice Voice;

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        Voice = voice;
    }
}
