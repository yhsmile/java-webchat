/**   
* @Title: Matchrule.java
* @Package: com.smile.webchat.menu
* @Description: 
* @author: ���  
* @date: 2017��6��22�� ����10:46:04
*
*/
package com.smile.webchat.menu;

/**
* @ClassName: Matchrule
* @Description: ���Ի��˵� - �˵�ƥ�����
* @author: ���
* @date: 2017��6��22�� ����10:46:04
* 
*/
public class Matchrule {

	/**
	 * �û���ǩ��id����ͨ���û���ǩ����ӿڻ�ȡ
	 */
	private String tag_id;
	
	/**
	 * �Ա��У�1��Ů��2������������ƥ��
	 */
	private String sex;
	/**
	 * ������Ϣ
	 */
	private String country;
	/**
	 * ʡ����Ϣ
	 */
	private String province;
	/**
	 * ������Ϣ
	 */
	private String city;
	/**
	 * �ͻ��˰汾����ǰֻ���嵽ϵͳ�ͺţ�IOS(1), Android(2),Others(3)����������ƥ��
	 */
	private String client_platform_type;
	/**
	 * ������Ϣ
	 */
	private String language;
	
	
	public String getTag_id() {
		return tag_id;
	}
	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getClient_platform_type() {
		return client_platform_type;
	}
	public void setClient_platform_type(String client_platform_type) {
		this.client_platform_type = client_platform_type;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	
	
	
}
