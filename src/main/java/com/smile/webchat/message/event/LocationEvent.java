/**   
* @Title: LocationEvent.java
* @Package: com.smile.webchat.message.event
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����5:45:39
*
*/
package com.smile.webchat.message.event;

/**
* @ClassName: LocationEvent
* @Description: �ϱ�����λ���¼�
* @author: ���
* @date: 2017��6��19�� ����5:45:39
* 
*/
public class LocationEvent extends BaseEvent {

	 /**
	  *  ����λ��γ��
	  */
    private String Latitude;
    
    /**
     *  ����λ�þ���
     */
    private String Longitude;
    
    /**
     *  ����λ�þ���
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
