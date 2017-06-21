package com.smile.webchat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smile.webchat.model.SNSUserInfo;
import com.smile.webchat.model.WeixinOauth2Token;
import com.smile.webchat.util.AdvancedUtil;

@Controller
@RequestMapping(value = "/webchat")
public class WeixinController {
	
	@RequestMapping(value = "/getUserInfo")
	public String getUserInfo(HttpServletRequest request){
		
		// 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        
        // 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("wx638d3d4293105e85", "d4624c36b6795d1d99dcf0547af5443d", code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

            // 设置要传递的参数
            request.setAttribute("snsUserInfo", snsUserInfo);
            request.setAttribute("state", state);
        }
		
		
		return "userinfo";
	}
	
	@RequestMapping(value = "/share")
	public String share(){
		return "share";
	}

}
