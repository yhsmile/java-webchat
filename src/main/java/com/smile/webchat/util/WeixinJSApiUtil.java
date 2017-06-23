/**   
* @Title: WeixinJSApiUtil.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: 杨辉  
* @date: 2017年6月23日 上午11:16:00
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
* @Description:公众号调用各接口工具类
* @author: 杨辉
* @date: 2017年6月23日 上午11:16:00
* 
*/
public class WeixinJSApiUtil {
	
	private static Logger log = LoggerFactory.getLogger(WeixinJSApiUtil.class);

	
	/**
	 * 
	* @Title: getJSApiTicket
	* @author: 杨辉
	* @Description: 获取公众号用于调用微信JS接口的临时票据。
	* 				正常情况下，jsapi_ticket的有效期为7200秒，通过access_token来获取。
	* 				由于获取jsapi_ticket的api调用次数非常有限，频繁刷新jsapi_ticket会导致api调用受限，
	* 				影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket。
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
            log.info("票据：" + jsapi_ticket);
        }
		return jsapi_ticket;
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
	public static Map<String, Object> getWxConfig(String redirect_url,String jsapi_ticket,String appId) {
        Map<String, Object> ret = new HashMap<String, Object>();
      
        String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
        String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
        
        log.info("nonceStr:" + nonceStr + ",timestamp:" +timestamp);
        

        String signature = "";
        // 注意这里参数名必须全部小写，且必须有序
        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr+ "&timestamp=" + timestamp + "&url=" + redirect_url;
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
}
