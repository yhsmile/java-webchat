/**   
* @Title: AdvancedUtil.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����4:07:57
*
*/
package com.smile.webchat.util;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smile.webchat.model.SNSUserInfo;
import com.smile.webchat.model.Token;
import com.smile.webchat.model.WeixinOauth2Token;

/**
* @ClassName: AdvancedUtil
* @Description: 
* @author: ���
* @date: 2017��6��20�� ����4:07:57
* 
*/
public class AdvancedUtil {

	private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);
	
	/**
	 * 
	* @Title: getOauth2AccessToken
	* @author: ���
	* @Description: ��ȡ��ҳ��Ȩ
	* @param appId
	* @param appSecret
	* @param code
	* @return    
	* @return WeixinOauth2Token    
	* @throws
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;
        // ƴ�������ַ
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // ��ȡ��ҳ��Ȩƾ֤
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getIntValue("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("��ȡ��ҳ��Ȩƾ֤ʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }
	
	
	/**
	 * 
	* @Title: getSNSUserInfo
	* @author: ���
	* @Description: ��ҳ��Ȩ��ȡ�û���Ϣ
	* @param accessToken
	* @param openId
	* @return    
	* @return SNSUserInfo    
	* @throws
	 */
	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;
        // ƴ�������ַ
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // ͨ����ҳ��Ȩ��ȡ�û���Ϣ
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // �û��ı�ʶ
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // �ǳ�
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // �Ա�1�����ԣ�2��Ů�ԣ�0��δ֪��
                snsUserInfo.setSex(jsonObject.getIntValue("sex"));
                // �û����ڹ���
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // �û�����ʡ��
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // �û����ڳ���
                snsUserInfo.setCity(jsonObject.getString("city"));
                // �û�ͷ��
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // �û���Ȩ��Ϣ
        //        snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
                snsUserInfo.setPrivilegeList(JSONArray.parseArray(jsonObject.get("privilege").toString(), String.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getIntValue("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("��ȡ�û���Ϣʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
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
	public static Map<String, Object> getWxConfig(HttpServletRequest request) {
        Map<String, Object> ret = new HashMap<String, Object>();
      
        String appId = "wx638d3d4293105e85"; // ������ںŵ�Ψһ��ʶ
        String secret = "d4624c36b6795d1d99dcf0547af5443d";

        //ע�⣺�˴���URL��ַ������Ϊ�����ҳ��ĵ�ַ
        String requestUrl = "http://1m74216j69.51mypc.cn/webchat/image.html";
        String access_token = "";
        String jsapi_ticket = "";
        String timestamp = Long.toString(System.currentTimeMillis() / 1000); // �������ǩ����ʱ���
        String nonceStr = UUID.randomUUID().toString(); // �������ǩ���������
        
        log.info("nonceStr:" + nonceStr + ",timestamp:" +timestamp);
        
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId + "&secret=" + secret;
        
        JSONObject json = CommonUtil.httpsRequest(url, "GET", null);
        
        if (json != null) {
            //Ҫע�⣬access_token��Ҫ����
            access_token = json.getString("access_token");
            
            
            url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token + "&type=jsapi";
            json = CommonUtil.httpsRequest(url, "GET", null);
            if (json != null) {
                jsapi_ticket = json.getString("ticket");
                log.info("Ʊ�ݣ�" + jsapi_ticket);
            }
        }
        String signature = "";
        // ע���������������ȫ��Сд���ұ�������
        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr+ "&timestamp=" + timestamp + "&url=" + requestUrl;
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
	
	public static void main(String[] args) {
		Token token = CommonUtil.getToken("", "");
	//	SNSUserInfo userInfo = 
	}
}
