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
		
		// �û�ͬ����Ȩ���ܻ�ȡ��code
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        
        // �û�ͬ����Ȩ
        if (!"authdeny".equals(code)) {
            // ��ȡ��ҳ��Ȩaccess_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("wx638d3d4293105e85", "d4624c36b6795d1d99dcf0547af5443d", code);
            // ��ҳ��Ȩ�ӿڷ���ƾ֤
            String accessToken = weixinOauth2Token.getAccessToken();
            // �û���ʶ
            String openId = weixinOauth2Token.getOpenId();
            // ��ȡ�û���Ϣ
            SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

            // ����Ҫ���ݵĲ���
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
