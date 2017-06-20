/**   
* @Title: MyX509TrustManager.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 上午10:56:52
*
*/
package com.smile.webchat.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
* @ClassName: MyX509TrustManager
* @Description: 信任管理器  用于https请求
* @author: 杨辉
* @date: 2017年6月20日 上午10:56:52
* 
*/
public class MyX509TrustManager implements X509TrustManager {

	/* (non-Javadoc)
	* Title: checkClientTrusted
	* Description: 
	* @param arg0
	* @param arg1
	* @throws CertificateException
	* @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
	*/
	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	* Title: checkServerTrusted
	* Description: 
	* @param arg0
	* @param arg1
	* @throws CertificateException
	* @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
	*/
	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	* Title: getAcceptedIssuers
	* Description: 
	* @return
	* @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
	*/
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

}
