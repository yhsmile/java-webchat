/**   
* @Title: Menu.java
* @Package: com.smile.webchat.menu
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����11:52:12
*
*/
package com.smile.webchat.menu;

/**
* @ClassName: Menu
* @Description: �����˵�����ķ�װ
* @author: ���
* @date: 2017��6��20�� ����11:52:12
* 
*/
public class Menu {

	/**
	 * �˵���ť
	 */
	private Button[] button;
	
	/**
	 * ���Ի��˵�ƥ�����
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
