/**   
* @Title: QRCodeEvent.java
* @Package: com.smile.webchat.message.event
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午5:44:39
*
*/
package com.smile.webchat.message.event;

/**
* @ClassName: QRCodeEvent
* @Description: 扫描带参数二维码事件
* @author: 杨辉
* @date: 2017年6月19日 下午5:44:39
* 
*/
public class QRCodeEvent extends BaseEvent {

	/**
	 *  事件KEY值
	 */
    private String EventKey;
    /**
     *  用于换取二维码图片
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
