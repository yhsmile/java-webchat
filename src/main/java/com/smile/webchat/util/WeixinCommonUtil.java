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
* @Description: 微信公众通用工具类	
* @author: 杨辉
* @date: 2017年6月20日 上午11:01:44
* 
*/
public class WeixinCommonUtil {
	
	private static Logger log = LoggerFactory.getLogger(WeixinCommonUtil.class);
	
	
	// 获取access_token的接口地址（GET） 限200（次/天）
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
    
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
	* @Description: 获取接口访问凭证(普通access_token调用)
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
	* @Title: getUserInfo
	* @author: 杨辉
	* @Description: 获取用户信息
	* @param accessToken
	* @param openId
	* @return    
	* @return WeixinUserInfo    
	* @throws
	 */
	public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
        WeixinUserInfo weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                weixinUserInfo = new WeixinUserInfo();
                // 用户的标识
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weixinUserInfo.setSubscribe(jsonObject.getIntValue("subscribe"));
                // 用户关注时间
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getIntValue("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    log.error("用户{}已取消关注", weixinUserInfo.getOpenId());
                } else {
                    int errorCode = jsonObject.getIntValue("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }
        }
        return weixinUserInfo;
    }
	
	
	
	public static void getWeiXinImage(String mediaId){
		Token token = getToken("wx638d3d4293105e85", "d4624c36b6795d1d99dcf0547af5443d");
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + token.getAccessToken() + "&media_id=" + mediaId;
		
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
            conn.setRequestMethod("GET");

            // 从输入流读取返回内容
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
            // 释放资源
            inputStream.close();
            inputStream = null;
            fileOutputStream.close();
            fileOutputStream = null;
            conn.disconnect();
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
		
		
		
		
		log.info("获取图片");
		
	}
	
	
	
	
	public static void uploadPermanentMedia2(String accessToken,File file,String title,String introduction) {  
		// 拼装请求地址  
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";  
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);  
        try {  
        	
        	 // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setSSLSocketFactory(ssf);
            
            //这块是用来处理如果上传的类型是video的类型的  
            JSONObject j=new JSONObject();  
            j.put("title", title);  
            j.put("introduction", introduction);  
              
  
            String result = null;  
            long filelength = file.length();  
            String fileName=file.getName();  
            String suffix=fileName.substring(fileName.lastIndexOf("."),fileName.length());  
            
//          String type="video/mp4"; //我这里写死  
            String type="image"; //我这里写死  
            /** 
             *  你们需要在这里根据文件后缀suffix将type的值设置成对应的mime类型的值 
             */  
            con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式  
            con.setDoInput(true);  
            con.setDoOutput(true);  
            con.setUseCaches(false); // post方式不能使用缓存  
            // 设置请求头信息  
            con.setRequestProperty("Connection", "Keep-Alive");  
            con.setRequestProperty("Charset", "UTF-8");  
              
            // 设置边界,这里的boundary是http协议里面的分割符，不懂的可惜百度(http 协议 boundary)，这里boundary 可以是任意的值(111,2222)都行  
            String BOUNDARY = "----------" + System.currentTimeMillis();  
            con.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);  
            // 请求正文信息  
            // 第一部分：  
              
            StringBuilder sb = new StringBuilder();  
              
              
              
            //这块是post提交type的值也就是文件对应的mime类型值  
            sb.append("--"); // 必须多两道线 这里说明下，这两个横杠是http协议要求的，用来分隔提交的参数用的，不懂的可以看看http 协议头  
            sb.append(BOUNDARY);  
            sb.append("\r\n");  
            sb.append("Content-Disposition: form-data;name=\"type\" \r\n\r\n"); //这里是参数名，参数名和值之间要用两次  
            sb.append(type+"\r\n"); //参数的值  
              
            //这块是上传video是必须的参数，你们可以在这里根据文件类型做if/else 判断  
//            sb.append("--"); // 必须多两道线  
//            sb.append(BOUNDARY);  
//            sb.append("\r\n");  
//            sb.append("Content-Disposition: form-data;name=\"description\" \r\n\r\n");  
//            sb.append(j.toString()+"\r\n");  
              
            /** 
             * 这里重点说明下，上面两个参数完全可以卸载url地址后面 就想我们平时url地址传参一样， 
             * http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##&type=""&description={} 这样，如果写成这样，上面的 
             * 那两个参数的代码就不用写了，不过media参数能否这样提交我没有试，感兴趣的可以试试 
             */  
              
            sb.append("--"); // 必须多两道线  
            sb.append(BOUNDARY);  
            sb.append("\r\n");  
            //这里是media参数相关的信息，这里是否能分开下我没有试，感兴趣的可以试试  
            sb.append("Content-Disposition: form-data;name=\"media\";filename=\""  
                    + fileName + "\";filelength=\"" + filelength + "\" \r\n");  
            sb.append("Content-Type:application/octet-stream\r\n\r\n");  
            System.out.println(sb.toString());  
            byte[] head = sb.toString().getBytes("utf-8");  
            // 获得输出流  
            OutputStream out = new DataOutputStream(con.getOutputStream());  
            // 输出表头  
            out.write(head);  
            // 文件正文部分  
            // 把文件已流文件的方式 推入到url中  
            DataInputStream in = new DataInputStream(new FileInputStream(file));  
            int bytes = 0;  
            byte[] bufferOut = new byte[1024];  
            while ((bytes = in.read(bufferOut)) != -1) {  
                out.write(bufferOut, 0, bytes);  
            }  
            in.close();  
            // 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定  
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线  
            out.write(foot);  
            out.flush();  
            out.close();  
            StringBuffer buffer = new StringBuffer();  
            BufferedReader reader = null;  
            // 定义BufferedReader输入流来读取URL的响应  
            reader = new BufferedReader(new InputStreamReader(  
                    con.getInputStream()));  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                buffer.append(line);  
            }  
            if (result == null) {  
                result = buffer.toString();  
            }  
            // 使用JSON-lib解析返回结果  
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
