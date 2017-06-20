/**   
* @Title: SNSUserInfo.java
* @Package: com.smile.webchat.model
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 下午4:05:57
*
*/
package com.smile.webchat.model;

import java.util.List;

/**
* @ClassName: SNSUserInfo
* @Description: 通过网页授权获取的用户信息
* @author: 杨辉
* @date: 2017年6月20日 下午4:05:57
* 
*/
public class SNSUserInfo extends WeixinUserInfo {
	
	/**
	 *  用户特权信息
	 */
    private List<String> privilegeList;

	public List<String> getPrivilegeList() {
		return privilegeList;
	}

	public void setPrivilegeList(List<String> privilegeList) {
		this.privilegeList = privilegeList;
	}

	@Override
	public String toString() {
		return "SNSUserInfo [privilegeList=" + privilegeList + "]";
	}
	
    
    

}
