/**   
* @Title: SNSUserInfo.java
* @Package: com.smile.webchat.model
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����4:05:57
*
*/
package com.smile.webchat.model;

import java.util.List;

/**
* @ClassName: SNSUserInfo
* @Description: ͨ����ҳ��Ȩ��ȡ���û���Ϣ
* @author: ���
* @date: 2017��6��20�� ����4:05:57
* 
*/
public class SNSUserInfo extends WeixinUserInfo {
	
	/**
	 *  �û���Ȩ��Ϣ
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
