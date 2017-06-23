/**   
* @Title: WeixinJSApiUtil.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: ���  
* @date: 2017��6��23�� ����11:16:00
*
*/
package com.smile.webchat.util;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
* @ClassName: WeixinJSApiUtil
* @Description:���ںŵ��ø��ӿڹ�����
* @author: ���
* @date: 2017��6��23�� ����11:16:00
* 
*/
public class WeixinJSApiUtil {
	
	private static Logger log = LoggerFactory.getLogger(WeixinJSApiUtil.class);

	
	/**
	 * 
	* @Title: getJSApiTicket
	* @author: ���
	* @Description: ��ȡ���ں����ڵ���΢��JS�ӿڵ���ʱƱ�ݡ�
	* 				��������£�jsapi_ticket����Ч��Ϊ7200�룬ͨ��access_token����ȡ��
	* 				���ڻ�ȡjsapi_ticket��api���ô����ǳ����ޣ�Ƶ��ˢ��jsapi_ticket�ᵼ��api�������ޣ�
	* 				Ӱ������ҵ�񣬿����߱������Լ��ķ���ȫ�ֻ���jsapi_ticket��
	* @param access_token
	* @return    
	* @return String    
	* @throws
	 */
	public static String getJSApiTicket(String access_token){
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token + "&type=jsapi";
		JSONObject json = WeixinCommonUtil.httpsRequest(url, "GET", null);
		String jsapi_ticket = "";
		if (json != null) {
            jsapi_ticket = json.getString("ticket");
            log.info("Ʊ�ݣ�" + jsapi_ticket);
        }
		return jsapi_ticket;
	}
	
	
	
	/**
	 * 
	* @Title: getWxConfig
	* @author: ���
	* @Description: ��ȡ΢�ŵ�������Ϣ
	* @param request
	* @return    
	* @return Map<String,Object>    
	* @throws
	 */
	public static Map<String, Object> getWxConfig(String redirect_url,String jsapi_ticket,String appId) {
        Map<String, Object> ret = new HashMap<String, Object>();
      
        String timestamp = Long.toString(System.currentTimeMillis() / 1000); // �������ǩ����ʱ���
        String nonceStr = UUID.randomUUID().toString(); // �������ǩ���������
        
        log.info("nonceStr:" + nonceStr + ",timestamp:" +timestamp);
        

        String signature = "";
        // ע���������������ȫ��Сд���ұ�������
        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr+ "&timestamp=" + timestamp + "&url=" + redirect_url;
        log.info("sign�ַ�����" + sign);
        
        try {
    		//��ƴ�Ӻõ��ַ������м���
    		byte[] digest = SignUtil.digestSHA1(sign);
			signature = SignUtil.byteToStr(digest);
			log.info("ǩ����" + signature);
        	
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        ret.put("appId", appId);
        ret.put("timestamp", timestamp);
        ret.put("nonceStr", nonceStr);
        ret.put("signature", signature);
        return ret;
    }
}
