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
* @Description: ����signature ������
* @author: ���
* @date: 2017��6��19�� ����2:06:25
*
 */
public class SignUtil {
	
	//��ӿ�������Ϣ�е�TokenҪһ��
	private static final String token = "smilewebchat";

	
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		
		//1.��token��timestamp��nonce�������������ֵ�������
		String[] arr = new String[]{token,timestamp,nonce};
		Arrays.sort(arr);
		
		//2.�����������ַ���ƴ�ӳ�һ���ַ������� sha1 ����
		StringBuilder content = new StringBuilder();
		for(int i=0;i<arr.length;i++){
			content.append(arr[i]);
		}
		
		String tmpStr = null;
		try {
			
			//��ƴ�Ӻõ��ַ������м���
			byte[] digest = digestSHA1(content.toString());
			tmpStr = byteToStr(digest);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		content = null;
		
		//3.��sha1���ܺ���ַ�����signature�Աȣ���ʶ��������Դ��΢��
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()):false;
	}
	
	/**
	 * 
	* @Title: byteToStr
	* @author: ���
	* @Description: ���ֽ�����ת��Ϊʮ�������ַ���
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
	* @author: ���
	* @Description: ���ֽ�ת��Ϊʮ�������ַ���
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
	* @author: ���
	* @Description: �ַ���SHA1����
	* @param sign
	* @return
	* @throws NoSuchAlgorithmException    
	* @return byte[]    
	* @throws
	 */
	public static byte[] digestSHA1(String sign) throws NoSuchAlgorithmException{
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA-1");
		//��ƴ�Ӻõ��ַ������м���
		byte[] digest = md.digest(sign.getBytes());
		return digest;
	}
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
	//	String s ="jsapi_ticket=kgt8ON7yVITDhtdwci0qeT1XS5lQunjk4gYPvE-M_bxEyiCD5IeQjD9Z9Y35tNeU-4xe6lDJeXoARPCjkwMkeg&noncestr=509e9cf0-10d1-4ec9-ad2b-c9ee40a6b7ed&timestamp=1498021875&url=http://1m74216j69.51mypc.cn/webchat/share.html";
		String s ="1498022853384414999smilewebchat";
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA-1");
		//��ƴ�Ӻõ��ַ������м���
		byte[] digest = md.digest(s.getBytes());
		
		System.out.println(byteToStr(digest));
	}

}
