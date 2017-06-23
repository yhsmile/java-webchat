/**   
* @Title: AdvancedUtil.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 下午4:07:57
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
* @Description: 微信网页访问授权工具类
* @author: 杨辉
* @date: 2017年6月20日 下午4:07:57
* 
*/
public class WeixinWebOAuthUtil {

	private static Logger log = LoggerFactory.getLogger(WeixinWebOAuthUtil.class);
	
	/**
	 * 
	* @Title: getOauth2AccessToken
	* @author: 杨辉
	* @Description: 获取网页授权access_token
	* @param appId
	* @param appSecret
	* @param code
	* @return WeixinOauth2Token    
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
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
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }
	
	/**
	 * 
	* @Title: refreshToken
	* @author: 杨辉
	* @Description: 刷新网页授权 access_token
	* @param appid
	* @param refreshToken
	* @return    
	* @return WeixinOauth2Token    
	 */
	public static WeixinOauth2Token refreshToken(String appid, String refreshToken) {
		String refresh_token_url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		WeixinOauth2Token wat = null;
        String requestUrl = refresh_token_url.replace("APPID", appid).replace("REFRESH_TOKEN", refreshToken);
        // 发起GET请求获取凭证
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
                log.error("刷新网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }
	
	
	/**
	 * 
	* @Title: getSNSUserInfo
	* @author: 杨辉
	* @Description: 网页授权获取用户信息
	* @param accessToken
	* @param openId
	* @return    
	* @return SNSUserInfo    
	* @throws
	 */
	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getIntValue("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                snsUserInfo.setPrivilegeList(JSONArray.parseArray(jsonObject.get("privilege").toString(), String.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getIntValue("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
    }
	
	
	
}
