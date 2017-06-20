/**   
* @Title: CommonUtil.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 上午11:01:44
*
*/
package com.smile.webchat.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.smile.webchat.menu.Menu;
import com.smile.webchat.model.Token;

/**
* @ClassName: CommonUtil
* @Description: 通用工具类	
* @author: 杨辉
* @date: 2017年6月20日 上午11:01:44
* 
*/
public class CommonUtil {
	
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	
	// 获取access_token的接口地址（GET） 限200（次/天）
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
    
    // 菜单创建（POST） 限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    
	/**
	 * 
	* @Title: httpsRequest
	* @author: 杨辉
	* @Description: 发送https请求
	* @param requestUrl
	* @param requestMethod
	* @param outputStr
	* @return    
	* @return JSONObject    
	* @throws
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return jsonObject;
	}
	
	/**
	 * 
	* @Title: getToken
	* @author: 杨辉
	* @Description: 获取接口访问凭证
	* @param appid
	* @param appsecret
	* @return    
	* @return Token    
	* @throws
	 */
	public static Token getToken(String appid, String appsecret) {
        Token token = null;
        String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getIntValue("expires_in"));
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }
	
	/**
	 * 
	* @Title: createMenu
	* @author: 杨辉
	* @Description: 创建菜单
	* @param menu
	* @param accessToken
	* @return    
	* @return int 0表示成功，其他值表示失败
	* @throws
	 */
	public static int createMenu(Menu menu, String accessToken) {
        int result = 0;
        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.toJSONString(menu);
        // 调用接口创建菜单
        JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);
        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                result = jsonObject.getIntValue("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }
	
	
	public static void main(String[] args) {
		Token token = getToken("wx638d3d4293105e85", "d4624c36b6795d1d99dcf0547af5443d");
		System.out.println(token.toString());
	}

}
