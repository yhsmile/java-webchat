/**   
* @Title: AdvancedUtil.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 下午4:07:57
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
* @author: 杨辉
* @date: 2017年6月20日 下午4:07:57
* 
*/
public class AdvancedUtil {

	private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);
	
	/**
	 * 
	* @Title: getOauth2AccessToken
	* @author: 杨辉
	* @Description: 获取网页授权
	* @param appId
	* @param appSecret
	* @param code
	* @return    
	* @return WeixinOauth2Token    
	* @throws
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
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
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
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
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

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
        //        snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
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
	
	/**
	 * 
	* @Title: getWxConfig
	* @author: 杨辉
	* @Description: 获取微信的配置信息
	* @param request
	* @return    
	* @return Map<String,Object>    
	* @throws
	 */
	public static Map<String, Object> getWxConfig(HttpServletRequest request) {
        Map<String, Object> ret = new HashMap<String, Object>();
      
        String appId = "wx638d3d4293105e85"; // 必填，公众号的唯一标识
        String secret = "d4624c36b6795d1d99dcf0547af5443d";

        //注意：此处的URL地址，必须为你访问页面的地址
        String requestUrl = "http://1m74216j69.51mypc.cn/webchat/image.html";
        String access_token = "";
        String jsapi_ticket = "";
        String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
        String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
        
        log.info("nonceStr:" + nonceStr + ",timestamp:" +timestamp);
        
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId + "&secret=" + secret;
        
        JSONObject json = CommonUtil.httpsRequest(url, "GET", null);
        
        if (json != null) {
            //要注意，access_token需要缓存
            access_token = json.getString("access_token");
            
            
            url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token + "&type=jsapi";
            json = CommonUtil.httpsRequest(url, "GET", null);
            if (json != null) {
                jsapi_ticket = json.getString("ticket");
                log.info("票据：" + jsapi_ticket);
            }
        }
        String signature = "";
        // 注意这里参数名必须全部小写，且必须有序
        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr+ "&timestamp=" + timestamp + "&url=" + requestUrl;
        log.info("sign字符串：" + sign);
        
        try {
    		//将拼接好的字符串进行加密
    		byte[] digest = SignUtil.digestSHA1(sign);
			signature = SignUtil.byteToStr(digest);
			log.info("签名：" + signature);
        	
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
