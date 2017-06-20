/**   
* @Title: CommonButton.java
* @Package: com.smile.webchat.menu
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 上午11:48:39
*
*/
package com.smile.webchat.menu;

/**
* @ClassName: CommonButton
* @Description: 子菜单项 :没有子菜单的菜单项，有可能是二级菜单项，也有可能是不含二级菜单的一级菜单。
* 				这类子菜单项一定会包含三个属性：type、name和key。
* @author: 杨辉
* @date: 2017年6月20日 上午11:48:39
* 
*/
public class CommonButton extends Button {

	
	private String type;
	
    private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

    
    
}
