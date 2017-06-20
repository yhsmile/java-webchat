/**   
* @Title: Button.java
* @Package: com.smile.webchat.menu
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 上午11:45:35
*
*/
package com.smile.webchat.menu;

/**
* @ClassName: Button
* @Description: 菜单项的基类
* @author: 杨辉
* @date: 2017年6月20日 上午11:45:35
* 
*/
public class Button {

	/**
	 * 所有一级菜单、二级菜单都共有一个相同的属性，那就是name
	 */
	private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
