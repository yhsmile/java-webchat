/**
 * 
 */
package com.smile.webchat.util;

import java.security.MessageDigest;
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
		
		MessageDigest md = null;
		String tmpStr = null;
		try {
			
			md = MessageDigest.getInstance("SHA-1");
			//��ƴ�Ӻõ��ַ������м���
			byte[] digest = md.digest(content.toString().getBytes());
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
	private static String byteToStr(byte[] byteArray) {
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
}
