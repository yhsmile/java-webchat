/**
 * 
 */
package com.smile.webchat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 
* @ClassName: SignUtil
* @Description: 检验signature 工具类
* @author: 杨辉
* @date: 2017年6月19日 下午2:06:25
*
 */
public class SignUtil {
	
	//与接口配置信息中的Token要一致
	private static final String token = "smilewebchat";

	
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		
		//1.将token、timestamp、nonce三个参数进行字典序排序
		String[] arr = new String[]{token,timestamp,nonce};
		Arrays.sort(arr);
		
		//2.将三个参数字符串拼接成一个字符串进行 sha1 加密
		StringBuilder content = new StringBuilder();
		for(int i=0;i<arr.length;i++){
			content.append(arr[i]);
		}
		
		String tmpStr = null;
		try {
			
			//将拼接好的字符串进行加密
			byte[] digest = digestSHA1(content.toString());
			tmpStr = byteToStr(digest);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		content = null;
		
		//3.将sha1加密后的字符串与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()):false;
	}
	
	/**
	 * 
	* @Title: byteToStr
	* @author: 杨辉
	* @Description: 将字节数组转换为十六进制字符串
	* @param byteArray
	* @return    
	* @return String    
	* @throws
	 */
	public static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }
	
	/**
	 * 
	* @Title: byteToHexStr
	* @author: 杨辉
	* @Description: 将字节转换为十六进制字符串
	* @param mByte
	* @return    
	* @return String    
	* @throws
	 */
	private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
	
	/**
	 * 
	* @Title: digestSHA1
	* @author: 杨辉
	* @Description: 字符串SHA1加密
	* @param sign
	* @return
	* @throws NoSuchAlgorithmException    
	* @return byte[]    
	* @throws
	 */
	public static byte[] digestSHA1(String sign) throws NoSuchAlgorithmException{
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA-1");
		//将拼接好的字符串进行加密
		byte[] digest = md.digest(sign.getBytes());
		return digest;
	}
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
	//	String s ="jsapi_ticket=kgt8ON7yVITDhtdwci0qeT1XS5lQunjk4gYPvE-M_bxEyiCD5IeQjD9Z9Y35tNeU-4xe6lDJeXoARPCjkwMkeg&noncestr=509e9cf0-10d1-4ec9-ad2b-c9ee40a6b7ed&timestamp=1498021875&url=http://1m74216j69.51mypc.cn/webchat/share.html";
		String s ="1498022853384414999smilewebchat";
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA-1");
		//将拼接好的字符串进行加密
		byte[] digest = md.digest(s.getBytes());
		
		System.out.println(byteToStr(digest));
	}

}
