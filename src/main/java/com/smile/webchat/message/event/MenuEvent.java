/**   
* @Title: MenuEvent.java
* @Package: com.smile.webchat.message.event
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����5:46:35
*
*/
package com.smile.webchat.message.event;

/**
* @ClassName: MenuEvent
* @Description: �Զ���˵��¼�
* @author: ���
* @date: 2017��6��19�� ����5:46:35
* 
*/
public class MenuEvent extends BaseEvent {

	/**
	 *  �¼�KEYֵ�����Զ���˵��ӿ���KEYֵ��Ӧ
	 */
    private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
    
    
    
}
