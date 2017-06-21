/**   
* @Title: CommonUtil.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����11:01:44
*
*/
package com.smile.webchat.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;

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
import com.smile.webchat.model.WeixinUserInfo;

/**
* @ClassName: CommonUtil
* @Description: ͨ�ù�����	
* @author: ���
* @date: 2017��6��20�� ����11:01:44
* 
*/
public class CommonUtil {
	
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	
	// ��ȡaccess_token�Ľӿڵ�ַ��GET�� ��200����/�죩
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
    
    // �˵�������POST�� ��100����/�죩
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    
	/**
	 * 
	* @Title: httpsRequest
	* @author: ���
	* @Description: ����https����
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
            // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // ������SSLContext�����еõ�SSLSocketFactory����
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // ��������ʽ��GET/POST��
            conn.setRequestMethod(requestMethod);

            // ��outputStr��Ϊnullʱ�������д����
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // ע������ʽ
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // ����������ȡ��������
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // �ͷ���Դ
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("���ӳ�ʱ��{}", ce);
        } catch (Exception e) {
            log.error("https�����쳣��{}", e);
        }
        return jsonObject;
	}
	
	/**
	 * 
	* @Title: getToken
	* @author: ���
	* @Description: ��ȡ�ӿڷ���ƾ֤
	* @param appid
	* @param appsecret
	* @return    
	* @return Token    
	* @throws
	 */
	public static Token getToken(String appid, String appsecret) {
        Token token = null;
        String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        // ����GET�����ȡƾ֤
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getIntValue("expires_in"));
            } catch (JSONException e) {
                token = null;
                // ��ȡtokenʧ��
                log.error("��ȡtokenʧ�� errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }
	
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
        JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);
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
	* @Title: getUserInfo
	* @author: ���
	* @Description: ��ȡ�û���Ϣ
	* @param accessToken
	* @param openId
	* @return    
	* @return WeixinUserInfo    
	* @throws
	 */
	public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
        WeixinUserInfo weixinUserInfo = null;
        // ƴ�������ַ
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // ��ȡ�û���Ϣ
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                weixinUserInfo = new WeixinUserInfo();
                // �û��ı�ʶ
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // ��ע״̬��1�ǹ�ע��0��δ��ע����δ��עʱ��ȡ����������Ϣ
                weixinUserInfo.setSubscribe(jsonObject.getIntValue("subscribe"));
                // �û���עʱ��
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // �ǳ�
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // �û����Ա�1�����ԣ�2��Ů�ԣ�0��δ֪��
                weixinUserInfo.setSex(jsonObject.getIntValue("sex"));
                // �û����ڹ���
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // �û�����ʡ��
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // �û����ڳ���
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // �û������ԣ���������Ϊzh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // �û�ͷ��
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    log.error("�û�{}��ȡ����ע", weixinUserInfo.getOpenId());
                } else {
                    int errorCode = jsonObject.getIntValue("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    log.error("��ȡ�û���Ϣʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }
        }
        return weixinUserInfo;
    }
	
	
	
	public static void getWeiXinImage(String mediaId){
		Token token = getToken("wx638d3d4293105e85", "d4624c36b6795d1d99dcf0547af5443d");
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + token.getAccessToken() + "&media_id=" + mediaId;
		
		try {
            // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // ������SSLContext�����еõ�SSLSocketFactory����
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // ��������ʽ��GET/POST��
            conn.setRequestMethod("GET");

            // ����������ȡ��������
            InputStream inputStream = conn.getInputStream();
            
            FileOutputStream fileOutputStream = null;
            
            String path = "E:\\weixin\\";
            Date date = new Date();
            String fileName = date.getTime()+".jpg";
            File dir = new File(path);
            if(!dir.exists()){
            	dir.mkdirs();
            }
            path = path + fileName;
            fileOutputStream = new FileOutputStream(path);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            fileOutputStream.flush();
            // �ͷ���Դ
            inputStream.close();
            inputStream = null;
            fileOutputStream.close();
            fileOutputStream = null;
            conn.disconnect();
        } catch (ConnectException ce) {
            log.error("���ӳ�ʱ��{}", ce);
        } catch (Exception e) {
            log.error("https�����쳣��{}", e);
        }
		
		
		
		
		log.info("��ȡͼƬ");
		
		
	}
	
	
	public static void main(String[] args) {
//		Token token = getToken("wx638d3d4293105e85", "d4624c36b6795d1d99dcf0547af5443d");
//		System.out.println(token.toString());
//		
//		
//		WeixinUserInfo userInfo = getUserInfo(token.getAccessToken(),"o6hXFvgAXg5AfrybI_MXQKIfCqv0");
//		log.info(userInfo.toString());
		
		
		getWeiXinImage("");
	}

}
