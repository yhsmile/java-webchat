package com.smile.webchat.util;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.smile.webchat.model.Token;
import com.smile.webchat.model.WeixinUserInfo;

/**
* @ClassName: WeixinCommonUtil
* @Description: ΢�Ź���ͨ�ù�����	
* @author: ���
* @date: 2017��6��20�� ����11:01:44
* 
*/
public class WeixinCommonUtil {
	
	private static Logger log = LoggerFactory.getLogger(WeixinCommonUtil.class);
	
	
	// ��ȡaccess_token�Ľӿڵ�ַ��GET�� ��200����/�죩
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
    
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
	* @Description: ��ȡ�ӿڷ���ƾ֤(��ͨaccess_token����)
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
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(requestUrl, "GET", null);

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
	
	
	
	
	public static void uploadPermanentMedia2(String accessToken,File file,String title,String introduction) {  
		// ƴװ�����ַ  
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";  
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);  
        try {  
        	
        	 // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // ������SSLContext�����еõ�SSLSocketFactory����
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setSSLSocketFactory(ssf);
            
            //�����������������ϴ���������video�����͵�  
            JSONObject j=new JSONObject();  
            j.put("title", title);  
            j.put("introduction", introduction);  
              
  
            String result = null;  
            long filelength = file.length();  
            String fileName=file.getName();  
            String suffix=fileName.substring(fileName.lastIndexOf("."),fileName.length());  
            
//          String type="video/mp4"; //������д��  
            String type="image"; //������д��  
            /** 
             *  ������Ҫ����������ļ���׺suffix��type��ֵ���óɶ�Ӧ��mime���͵�ֵ 
             */  
            con.setRequestMethod("POST"); // ��Post��ʽ�ύ����Ĭ��get��ʽ  
            con.setDoInput(true);  
            con.setDoOutput(true);  
            con.setUseCaches(false); // post��ʽ����ʹ�û���  
            // ��������ͷ��Ϣ  
            con.setRequestProperty("Connection", "Keep-Alive");  
            con.setRequestProperty("Charset", "UTF-8");  
              
            // ���ñ߽�,�����boundary��httpЭ������ķָ���������Ŀ�ϧ�ٶ�(http Э�� boundary)������boundary �����������ֵ(111,2222)����  
            String BOUNDARY = "----------" + System.currentTimeMillis();  
            con.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);  
            // ����������Ϣ  
            // ��һ���֣�  
              
            StringBuilder sb = new StringBuilder();  
              
              
              
            //�����post�ύtype��ֵҲ�����ļ���Ӧ��mime����ֵ  
            sb.append("--"); // ����������� ����˵���£������������httpЭ��Ҫ��ģ������ָ��ύ�Ĳ����õģ������Ŀ��Կ���http Э��ͷ  
            sb.append(BOUNDARY);  
            sb.append("\r\n");  
            sb.append("Content-Disposition: form-data;name=\"type\" \r\n\r\n"); //�����ǲ���������������ֵ֮��Ҫ������  
            sb.append(type+"\r\n"); //������ֵ  
              
            //������ϴ�video�Ǳ���Ĳ��������ǿ�������������ļ�������if/else �ж�  
//            sb.append("--"); // �����������  
//            sb.append(BOUNDARY);  
//            sb.append("\r\n");  
//            sb.append("Content-Disposition: form-data;name=\"description\" \r\n\r\n");  
//            sb.append(j.toString()+"\r\n");  
              
            /** 
             * �����ص�˵���£���������������ȫ����ж��url��ַ���� ��������ƽʱurl��ַ����һ���� 
             * http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##&type=""&description={} ���������д������������� 
             * �����������Ĵ���Ͳ���д�ˣ�����media�����ܷ������ύ��û���ԣ�����Ȥ�Ŀ������� 
             */  
              
            sb.append("--"); // �����������  
            sb.append(BOUNDARY);  
            sb.append("\r\n");  
            //������media������ص���Ϣ�������Ƿ��ֿܷ�����û���ԣ�����Ȥ�Ŀ�������  
            sb.append("Content-Disposition: form-data;name=\"media\";filename=\""  
                    + fileName + "\";filelength=\"" + filelength + "\" \r\n");  
            sb.append("Content-Type:application/octet-stream\r\n\r\n");  
            System.out.println(sb.toString());  
            byte[] head = sb.toString().getBytes("utf-8");  
            // ��������  
            OutputStream out = new DataOutputStream(con.getOutputStream());  
            // �����ͷ  
            out.write(head);  
            // �ļ����Ĳ���  
            // ���ļ������ļ��ķ�ʽ ���뵽url��  
            DataInputStream in = new DataInputStream(new FileInputStream(file));  
            int bytes = 0;  
            byte[] bufferOut = new byte[1024];  
            while ((bytes = in.read(bufferOut)) != -1) {  
                out.write(bufferOut, 0, bytes);  
            }  
            in.close();  
            // ��β���֣������β��ʾ����Ĳ����Ľ�β����βҪ��"--"��Ϊ��������Щ����httpЭ��Ĺ涨  
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// ����������ݷָ���  
            out.write(foot);  
            out.flush();  
            out.close();  
            StringBuffer buffer = new StringBuffer();  
            BufferedReader reader = null;  
            // ����BufferedReader����������ȡURL����Ӧ  
            reader = new BufferedReader(new InputStreamReader(  
                    con.getInputStream()));  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                buffer.append(line);  
            }  
            if (result == null) {  
                result = buffer.toString();  
            }  
            // ʹ��JSON-lib�������ؽ��  
            JSONObject jsonObject = JSONObject.parseObject(result);  
            if (jsonObject.containsKey("media_id")) {  
                System.out.println("media_id:"+jsonObject.getString("media_id"));  
            } else {  
                System.out.println(jsonObject.toString());  
            }  
            System.out.println("json:"+jsonObject.toString());  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {  
  
        }  
    }  

	
	
	public static void main(String[] args) {
		Token token = getToken("wx638d3d4293105e85", "d4624c36b6795d1d99dcf0547af5443d");
//		System.out.println(token.toString());
//		
//		
//		WeixinUserInfo userInfo = getUserInfo(token.getAccessToken(),"o6hXFvgAXg5AfrybI_MXQKIfCqv0");
//		log.info(userInfo.toString());
		
	//	getMenu(token.getAccessToken());
		
		File file = new File("E:\\3.jpg");
		uploadPermanentMedia2(token.getAccessToken(),file,"","");
		
//		deleteMenu(token.getAccessToken());
	}

}
