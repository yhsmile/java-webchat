/**   
* @Title: ComplexButton.java
* @Package: com.smile.webchat.menu
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����11:51:08
*
*/
package com.smile.webchat.menu;

/**
* @ClassName: ComplexButton
* @Description: ���˵��� :�����ж����˵����һ���˵���
* 				����˵�������ж������ԣ�name��sub_button����sub_button����һ���Ӳ˵�������
* @author: ���
* @date: 2017��6��20�� ����11:51:08
* 
*/
public class ComplexButton extends Button {
	
	private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
