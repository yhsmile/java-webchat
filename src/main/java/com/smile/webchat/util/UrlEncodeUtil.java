/**   
* @Title: UrlEncodeUtil.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 下午4:57:50
*
*/
package com.smile.webchat.util;

import java.io.UnsupportedEncodingException;

/**
* @ClassName: UrlEncodeUtil
* @Description: 
* @author: 杨辉
* @date: 2017年6月20日 下午4:57:50
* 
*/
public class UrlEncodeUtil {
	
	/**
	 * 
	* @Title: urlEncodeUTF8
	* @author: 杨辉
	* @Description: URL 进行 UTF-8 编码
	* @param source
	* @return    
	* @return String    
	* @throws
	 */
	public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
	
	
	public static void main(String[] args) {
		String url = urlEncodeUTF8("http://1m74216j69.51mypc.cn/oauthServlet");
		System.out.println(url);
	}

}
