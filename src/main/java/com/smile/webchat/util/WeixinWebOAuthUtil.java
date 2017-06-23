/**   
* @Title: AdvancedUtil.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����4:07:57
*
*/
package com.smile.webchat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.smile.webchat.model.SNSUserInfo;
import com.smile.webchat.model.Token;
import com.smile.webchat.model.WeixinOauth2Token;

/**
* @ClassName: WeixinWebOAuthUtil
* @Description: ΢����ҳ������Ȩ������
* @author: ���
* @date: 2017��6��20�� ����4:07:57
* 
*/
public class WeixinWebOAuthUtil {

	private static Logger log = LoggerFactory.getLogger(WeixinWebOAuthUtil.class);
	
	/**
	 * 
	* @Title: getOauth2AccessToken
	* @author: ���
	* @Description: ��ȡ��ҳ��Ȩaccess_token
	* @param appId
	* @param appSecret
	* @param code
	* @return WeixinOauth2Token    
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;
        // ƴ�������ַ
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // ��ȡ��ҳ��Ȩƾ֤
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(requestUrl, "GET", null);
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
	* @Title: refreshToken
	* @author: ���
	* @Description: ˢ����ҳ��Ȩ access_token
	* @param appid
	* @param refreshToken
	* @return    
	* @return WeixinOauth2Token    
	 */
	public static WeixinOauth2Token refreshToken(String appid, String refreshToken) {
		String refresh_token_url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		WeixinOauth2Token wat = null;
        String requestUrl = refresh_token_url.replace("APPID", appid).replace("REFRESH_TOKEN", refreshToken);
        // ����GET�����ȡƾ֤
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
            	wat = new WeixinOauth2Token();
            	wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (JSONException e) {
            	wat = null;
                int errorCode = jsonObject.getIntValue("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("ˢ����ҳ��Ȩƾ֤ʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
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
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // ͨ����ҳ��Ȩ��ȡ�û���Ϣ
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(requestUrl, "GET", null);

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
	
	
	
}
