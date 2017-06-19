/**   
* @Title: LocationMessage.java
* @Package: com.smile.webchat.message
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午3:25:45
*
*/
package com.smile.webchat.message;

/**
* @ClassName: LocationMessage
* @Description: 请求消息之地理位置消息
* @author: 杨辉
* @date: 2017年6月19日 下午3:25:45
* 
*/
public class LocationMessage extends BaseMessage {

	/**
	 *  地理位置维度
	 */
    private String Location_X;
    /**
     *  地理位置经度
     */
    private String Location_Y;
    /**
     *  地图缩放大小
     */
    private String Scale;
    /**
     *  地理位置信息
     */
    private String Label;
    
	public String getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
    
    
    
}
