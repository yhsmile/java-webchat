/**   
* @Title: LocationEvent.java
* @Package: com.smile.webchat.message.event
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午5:45:39
*
*/
package com.smile.webchat.message.event;

/**
* @ClassName: LocationEvent
* @Description: 上报地理位置事件
* @author: 杨辉
* @date: 2017年6月19日 下午5:45:39
* 
*/
public class LocationEvent extends BaseEvent {

	 /**
	  *  地理位置纬度
	  */
    private String Latitude;
    
    /**
     *  地理位置经度
     */
    private String Longitude;
    
    /**
     *  地理位置精度
     */
    private String Precision;
    
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getPrecision() {
		return Precision;
	}
	public void setPrecision(String precision) {
		Precision = precision;
	}
    
    
    
}
