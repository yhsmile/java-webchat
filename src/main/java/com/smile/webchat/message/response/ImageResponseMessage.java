/**   
* @Title: ImageResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����5:52:27
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: ImageResponseMessage
* @Description: ͼƬ��Ϣ
* @author: ���
* @date: 2017��6��19�� ����5:52:27
* 
*/
public class ImageResponseMessage extends BaseResponseMessage {

	private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
