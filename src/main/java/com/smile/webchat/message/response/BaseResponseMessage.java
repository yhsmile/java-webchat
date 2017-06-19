/**   
* @Title: BaseResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����5:48:31
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: BaseResponseMessage
* @Description: ��Ӧ��Ϣ���ࣨ�����ʺ� -> ��ͨ�û���
* @author: ���
* @date: 2017��6��19�� ����5:48:31
* 
*/
public class BaseResponseMessage {

	/**
	 *  ���շ��ʺţ��յ���OpenID��
	 */
    private String ToUserName;
    
    /** 
     * ������΢�ź�
     */
    private String FromUserName;
    
    /**
     *  ��Ϣ����ʱ�� �����ͣ�
     */
    private long CreateTime;
    
    /**
     *  ��Ϣ����
     */
    private String MsgType;
    
    
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
    
    
    
}
