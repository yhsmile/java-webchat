/**   
* @Title: QRCodeEvent.java
* @Package: com.smile.webchat.message.event
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����5:44:39
*
*/
package com.smile.webchat.message.event;

/**
* @ClassName: QRCodeEvent
* @Description: ɨ���������ά���¼�
* @author: ���
* @date: 2017��6��19�� ����5:44:39
* 
*/
public class QRCodeEvent extends BaseEvent {

	/**
	 *  �¼�KEYֵ
	 */
    private String EventKey;
    /**
     *  ���ڻ�ȡ��ά��ͼƬ
     */
    private String Ticket;
    
    
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}

    
    
}
