/**   
* @Title: ImageResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: 杨辉  
* @date: 2017年6月19日 下午5:52:27
*
*/
package com.smile.webchat.message.response;

/**
* @ClassName: ImageResponseMessage
* @Description: 图片消息
* @author: 杨辉
* @date: 2017年6月19日 下午5:52:27
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
