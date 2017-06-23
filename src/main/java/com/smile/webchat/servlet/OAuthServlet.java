/**   
* @Title: OAuthServlet.java
* @Package: com.smile.webchat.servlet
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 下午4:52:31
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
* @Description: 授权后的回调请求处理
* @author: 杨辉
* @date: 2017年6月20日 下午4:52:31
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

        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        
        // 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = WeixinWebOAuthUtil.getOauth2AccessToken("wx638d3d4293105e85", "d4624c36b6795d1d99dcf0547af5443d", code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            SNSUserInfo snsUserInfo = WeixinWebOAuthUtil.getSNSUserInfo(accessToken, openId);

            // 设置要传递的参数
            request.setAttribute("snsUserInfo", snsUserInfo);
            request.setAttribute("state", state);
        }
        // 跳转到index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
