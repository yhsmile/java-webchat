/**   
* @Title: WeixinUserInfo.java
* @Package: com.smile.webchat.model
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����3:17:20
*
*/
package com.smile.webchat.model;

/**
* @ClassName: WeixinUserInfo
* @Description: ΢���û��Ļ�����Ϣ
* @author: ���
* @date: 2017��6��20�� ����3:17:20
* 
*/
public class WeixinUserInfo {
	
	/**
	 *  �û��ı�ʶ
	 */
    private String openId;
    /**
     *  ��ע״̬��1�ǹ�ע��0��δ��ע����δ��עʱ��ȡ����������Ϣ
     */
    private int subscribe;
    /**
     *  �û���עʱ�䣬Ϊʱ���������û�����ι�ע����ȡ����עʱ��
     */
    private String subscribeTime;
    /**
     *  �ǳ�
     */
    private String nickname;
    /**
     *  �û����Ա�1�����ԣ�2��Ů�ԣ�0��δ֪��
     */
    private int sex;
    /**
     *  �û����ڹ���
     */
    private String country;
    /**
     *  �û�����ʡ��
     */
    private String province;
    /**
     *  �û����ڳ���
     */
    private String city;
    /**
     *  �û������ԣ���������Ϊzh_CN
     */
    private String language;
    /**
     *  �û�ͷ��
     */
    private String headImgUrl;
    
    
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
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
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
	
	@Override
	public String toString() {
		return "WeixinUserInfo [openId=" + openId + ", subscribe=" + subscribe + ", subscribeTime=" + subscribeTime
				+ ", nickname=" + nickname + ", sex=" + sex + ", country=" + country + ", province=" + province
				+ ", city=" + city + ", language=" + language + ", headImgUrl=" + headImgUrl + "]";
	}
    
    
    

}