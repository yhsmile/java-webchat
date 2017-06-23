/**   
* @Title: OAuthServlet.java
* @Package: com.smile.webchat.servlet
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����4:52:31
*
*/
package com.smile.webchat.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smile.webchat.model.SNSUserInfo;
import com.smile.webchat.model.WeixinOauth2Token;
import com.smile.webchat.util.WeixinWebOAuthUtil;

/**
* @ClassName: OAuthServlet
* @Description: ��Ȩ��Ļص�������
* @author: ���
* @date: 2017��6��20�� ����4:52:31
* 
*/
public class OAuthServlet extends HttpServlet {

	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -2167615165569861666L;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // �û�ͬ����Ȩ���ܻ�ȡ��code
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        
        // �û�ͬ����Ȩ
        if (!"authdeny".equals(code)) {
            // ��ȡ��ҳ��Ȩaccess_token
            WeixinOauth2Token weixinOauth2Token = WeixinWebOAuthUtil.getOauth2AccessToken("wx638d3d4293105e85", "d4624c36b6795d1d99dcf0547af5443d", code);
            // ��ҳ��Ȩ�ӿڷ���ƾ֤
            String accessToken = weixinOauth2Token.getAccessToken();
            // �û���ʶ
            String openId = weixinOauth2Token.getOpenId();
            // ��ȡ�û���Ϣ
            SNSUserInfo snsUserInfo = WeixinWebOAuthUtil.getSNSUserInfo(accessToken, openId);

            // ����Ҫ���ݵĲ���
            request.setAttribute("snsUserInfo", snsUserInfo);
            request.setAttribute("state", state);
        }
        // ��ת��index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
