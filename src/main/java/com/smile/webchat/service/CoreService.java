/**   
* @Title: CoreService.java
* @Package: com.smile.webchat.service
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����10:24:26
*
*/
package com.smile.webchat.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.smile.webchat.message.response.Image;
import com.smile.webchat.message.response.ImageResponseMessage;
import com.smile.webchat.message.response.TextResponseMessage;
import com.smile.webchat.message.response.Voice;
import com.smile.webchat.message.response.VoiceResponseMessage;
import com.smile.webchat.util.MessageUtil;

/**
* @ClassName: CoreService
* @Description: ���ķ�����
* @author: ���
* @date: 2017��6��20�� ����10:24:26
* 
*/
public class CoreService {

	/**
     * ����΢�ŷ���������
     * @param request
     * @return xml
     */
    public static String processRequest(HttpServletRequest request) {
        // xml��ʽ����Ϣ����
        String respXml = null;
        // Ĭ�Ϸ��ص��ı���Ϣ����
        String respContent = "δ֪����Ϣ���ͣ�";
        try {
            // ����parseXml��������������Ϣ
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // ���ͷ��ʺ�
            String fromUserName = requestMap.get("FromUserName");
            // ������΢�ź�
            String toUserName = requestMap.get("ToUserName");
            // ��Ϣ����
            String msgType = requestMap.get("MsgType");

            // �ظ��ı���Ϣ
            TextResponseMessage textMessage = new TextResponseMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            // �ı���Ϣ
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                respContent = "�����͵����ı���Ϣ��";
            }
            // ͼƬ��Ϣ
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "�����͵���ͼƬ��Ϣ��";
                
                ImageResponseMessage message = new ImageResponseMessage();
                message.setCreateTime(new Date().getTime());
                message.setFromUserName(toUserName);
                message.setToUserName(fromUserName);
                message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
                Image image = new Image();
                image.setMediaId("DLN8G9-InHCBWac_qZ8zOBnzB57dCO6o4WGzJaP_4TrknyNSR9K46CdJCE08fzwF");
                message.setImage(image);
                
                respXml = MessageUtil.messageToXml(message);
                return respXml;
            }
            // ������Ϣ
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "�����͵���������Ϣ��";
            }
            // ��Ƶ��Ϣ
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "�����͵�����Ƶ��Ϣ��";
            }
            // ��Ƶ��Ϣ
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
                respContent = "�����͵���С��Ƶ��Ϣ��";
            }
            // ����λ����Ϣ
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "�����͵��ǵ���λ����Ϣ��";
            }
            // ������Ϣ
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "�����͵���������Ϣ��";
            }
            // �¼�����
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // �¼�����
                String eventType = requestMap.get("Event");
                // ��ע
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "лл���Ĺ�ע��";
                }
                // ȡ����ע
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO ȡ�����ĺ��û��������յ������˺ŷ��͵���Ϣ����˲���Ҫ�ظ�
                }
                // ɨ���������ά��
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    // TODO ����ɨ���������ά���¼�
                }
                // �ϱ�����λ��
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    // TODO �����ϱ�����λ���¼�
                }
                // �Զ���˵�
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO ����˵�����¼�
                	
                	// �¼�KEYֵ���봴���Զ���˵�ʱָ����KEYֵ��Ӧ  
                    String eventKey = requestMap.get("EventKey");  
                    
                    //�Զ���˵�����ϵ��
                    if (eventKey.equals("33")){
                    	StringBuilder sb = new StringBuilder();
                    	sb.append("��ϵQQ��735308529").append("\n");
                    	sb.append("��ϵ�绰��18217547896");
                    	respContent = sb.toString();
                    }
                    
                    if(eventKey.equals("21")){
                    	//�ı���Ϣ
                    	respContent = "��ϲ��HelloKitty����ϲ��ʲô��";
                    	
                    }else if(eventKey.equals("22")){
                    	//ͼƬ��Ϣ
                    	ImageResponseMessage message = new ImageResponseMessage();
                        message.setCreateTime(new Date().getTime());
                        message.setFromUserName(toUserName);
                        message.setToUserName(fromUserName);
                        message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
                        Image image = new Image();
                        image.setMediaId("yxfgmExnWqf9PxKMeK5Hah9bfp6czM8Y5kxUGDJfemE");
                        message.setImage(image);
                        respXml = MessageUtil.messageToXml(message);
                    	
                    }else if(eventKey.equals("23")){
                    	//������Ϣ
                    	VoiceResponseMessage message = new VoiceResponseMessage();
                    	message.setCreateTime(new Date().getTime());
                        message.setFromUserName(toUserName);
                        message.setToUserName(fromUserName);
                        message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_VOICE);
                        Voice voice = new Voice();
                        voice.setMediaId("");
                        message.setVoice(voice);
                        respXml = MessageUtil.messageToXml(message);
                    	
                    }else if(eventKey.equals("24")){
                    	//��Ƶ��Ϣ
                    	
                    }else if(eventKey.equals("25")){
                    	
                    }else if(eventKey.equals("31")){
                    	
                    }
                }
            }
            // �����ı���Ϣ������
            textMessage.setContent(respContent);
            // ���ı���Ϣ����ת����xml
            respXml = MessageUtil.messageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }
}
