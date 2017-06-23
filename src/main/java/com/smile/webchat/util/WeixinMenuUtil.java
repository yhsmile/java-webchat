/**   
* @Title: WeixinMenuUtil.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: ���  
* @date: 2017��6��23�� ����11:30:39
*
*/
package com.smile.webchat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.smile.webchat.menu.Menu;

/**
* @ClassName: WeixinMenuUtil
* @Description: ΢�Ź��ڲ˵�������
* @author: ���
* @date: 2017��6��23�� ����11:30:39
* 
*/
public class WeixinMenuUtil {
	
	private static Logger log = LoggerFactory.getLogger(WeixinMenuUtil.class);
	
	
	// �˵�������POST�� ��100����/�죩
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	// ���Բ˵�������POST�� ��100����/�죩
	public static String cond_menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN";
	
	// ��ȡ�˵���POST��
	public static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	// ɾ���˵���POST��
	public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
   

	/**
	 * 
	* @Title: createMenu
	* @author: ���
	* @Description: �����˵�
	* @param menu
	* @param accessToken
	* @return    
	* @return int 0��ʾ�ɹ�������ֵ��ʾʧ��
	* @throws
	 */
	public static int createMenu(Menu menu, String accessToken) {
        int result = 0;
        
        // ƴװ�����˵���url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        
        // ���˵�����ת����json�ַ���
        String jsonMenu = JSONObject.toJSONString(menu);
        
        // ���ýӿڴ����˵�
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(url, "POST", jsonMenu);
        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                result = jsonObject.getIntValue("errcode");
                log.error("�����˵�ʧ�� errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }
	
	
	/**
	 * 
	* @Title: createConditionalMenu
	* @author: ���
	* @Description: �������Ի��˵�
	* @param menu
	* @param accessToken
	* @return    
	* @return int    
	* @throws
	 */
	public static int createConditionalMenu(Menu menu, String accessToken){
		int result = 0;
		String url = cond_menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// ���˵�����ת����json�ַ���
        String jsonMenu = JSONObject.toJSONString(menu);
        // ���ýӿڴ����˵�
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(url, "POST", jsonMenu);
        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                result = jsonObject.getIntValue("errcode");
                log.error("�������Ի��˵�ʧ�� errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }
		return result;
	}
	
	
	/**
	 * 
	* @Title: getMenu
	* @author: ���
	* @Description: ��ȡ�˵���Ϣ
	* @param accessToken
	* @return    
	* @return JSONObject    
	* @throws
	 */
	public static JSONObject getMenu(String accessToken){
		String url = menu_get_url.replace("ACCESS_TOKEN", accessToken);
		 JSONObject jsonObject = WeixinCommonUtil.httpsRequest(url, "POST", null);
		 log.info("��ѯ�˵����صĽ����" + jsonObject.toJSONString());
		 return jsonObject;
	}
	
	/**
	 * 
	* @Title: deleteMenu
	* @author: ���
	* @Description: ɾ�����еĲ˵�
	* @param accessToken
	* @return    
	* @return JSONObject    
	* @throws
	 */
	public static int deleteMenu(String accessToken){
		int result = 0;
		String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = WeixinCommonUtil.httpsRequest(url, "POST", null);
		log.info("��ѯ�˵����صĽ����" + jsonObject.toJSONString());
		if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                result = jsonObject.getIntValue("errcode");
                log.error("�����˵�ʧ�� errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }
		return result;
	}
}
