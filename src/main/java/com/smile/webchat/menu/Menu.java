/**   
* @Title: Menu.java
* @Package: com.smile.webchat.menu
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 上午11:52:12
*
*/
package com.smile.webchat.menu;

/**
* @ClassName: Menu
* @Description: 整个菜单对象的封装
* @author: 杨辉
* @date: 2017年6月20日 上午11:52:12
* 
*/
public class Menu {

	/**
	 * 菜单按钮
	 */
	private Button[] button;
	
	/**
	 * 个性化菜单匹配规则
	 */
	private Matchrule matchrule;

    public Button[] getButton() {
        return button;
    }

    public void setButton(Button[] button) {
        this.button = button;
    }

	public Matchrule getMatchrule() {
		return matchrule;
	}

	public void setMatchrule(Matchrule matchrule) {
		this.matchrule = matchrule;
	}
    
    
}
