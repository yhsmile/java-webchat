/**   
* @Title: VoiceResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����5:53:37
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: VoiceResponseMessage
* @Description: 
* @author: ���
* @date: 2017��6��19�� ����5:53:37
* 
*/
public class VoiceResponseMessage extends BaseResponseMessage {

	// ����
    private Voice Voice;

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        Voice = voice;
    }
}
