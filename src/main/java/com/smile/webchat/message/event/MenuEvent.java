/**   
* @Title: MenuEvent.java
* @Package: com.smile.webchat.message.event
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午5:46:35
*
*/
package com.smile.webchat.message.event;

/**
* @ClassName: MenuEvent
* @Description: 自定义菜单事件
* @author: 杨辉
* @date: 2017年6月19日 下午5:46:35
* 
*/
public class MenuEvent extends BaseEvent {

	/**
	 *  事件KEY值，与自定义菜单接口中KEY值对应
	 */
    private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
    
    
    
}
