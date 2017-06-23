package com.smile.webchat.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.smile.webchat.bean.WebchatAppBean;
import com.smile.webchat.model.SNSUserInfo;
import com.smile.webchat.model.Token;
import com.smile.webchat.model.WeixinOauth2Token;
import com.smile.webchat.service.RedisService;
import com.smile.webchat.util.WeixinCommonUtil;
import com.smile.webchat.util.WeixinJSApiUtil;
import com.smile.webchat.util.WeixinWebOAuthUtil;

@Controller
@RequestMapping(value = "/webchat")
public class WeixinController {
	
	
	Logger log = LoggerFactory.getLogger(WeixinController.class);
	
	private static String ACCESS_TOKEN = "access_token";
	private static String JSAPI_TICKET = "jsapi_ticket";
	
	
	@Autowired
	private WebchatAppBean webchatAppBean;
	
	@Autowired
	private RedisService redisService;
	
	
	@RequestMapping(value = "/share")
	public String share(){
		return "share";
	}
	
	@RequestMapping(value = "/image")
	public String image(){
		return "image";
	}
	
	
	@RequestMapping(value = "/tz")
	public void tz(HttpServletResponse response) throws IOException{
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx638d3d4293105e85&redirect_uri=http%3A%2F%2F1m74216j69.51mypc.cn%2Fwebchat%2Fhyl.html&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		response.sendRedirect(url);
	}
	
	
	@RequestMapping(value = "/getConfig",method = RequestMethod.POST)
	@ResponseBody
	public String getConfig(String url,HttpServletRequest request){
		JSONObject json = new JSONObject();
		System.err.println(url);
		
		//定义微信调用接口配置信息的MAP
		Map<String, Object> map = new HashMap<String,Object>();
		
		String jsapi_ticket = "";
		String access_token = "";
		//先判断微信接口调用临时凭证是否存在于缓存中
		 jsapi_ticket = redisService.get(JSAPI_TICKET);
		 if(StringUtils.isNotBlank(jsapi_ticket)){
			 
			 //获取微信接口配置信息
			 map = WeixinJSApiUtil.getWxConfig(url, jsapi_ticket, webchatAppBean.getAppID());
			 
		 }else{
			 //先判断缓存中是否有access_token
			 access_token = redisService.get(ACCESS_TOKEN);
			 if(StringUtils.isNotBlank(access_token)){
				 
				jsapi_ticket = WeixinJSApiUtil.getJSApiTicket(access_token);
				map = WeixinJSApiUtil.getWxConfig(url, jsapi_ticket, webchatAppBean.getAppID());
				
			 }else{
				 log.info("缓存中不存在，重新获取Token");
				 
				 //缓存中不存在 access_token，重新获取
				 //获取Access_token
				 Token token = WeixinCommonUtil.getToken(webchatAppBean.getAppID(), webchatAppBean.getAppsecret());
				 if(null != token && StringUtils.isNotBlank(token.getAccessToken())){
					redisService.set(ACCESS_TOKEN, token.getAccessToken());
					redisService.expire(ACCESS_TOKEN, token.getExpiresIn());
					
					jsapi_ticket = WeixinJSApiUtil.getJSApiTicket(token.getAccessToken());
					
					redisService.set(JSAPI_TICKET, jsapi_ticket);
					redisService.expire(JSAPI_TICKET, 7200);
					
					map = WeixinJSApiUtil.getWxConfig(url, jsapi_ticket, webchatAppBean.getAppID());
				 }
			 }
		 }
		json.put("appid", map.get("appId"));
		json.put("timestamp", map.get("timestamp"));
		json.put("nonceStr", map.get("nonceStr"));
		json.put("signature", map.get("signature"));

		return json.toJSONString();
	}
	
	

	@RequestMapping(value = "/hyl")
	public String hyl(HttpServletRequest request,Model model){
		try {
			request.setCharacterEncoding("utf-8");
			// 用户同意授权后，能获取到code
	        String code = request.getParameter("code");
	        System.out.println("code:" + code);
	        if("".equals(code) || null == code){
	        	return "redirect:tz.html";
	        }
			
	        // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = WeixinWebOAuthUtil.getOauth2AccessToken(webchatAppBean.getAppID(), webchatAppBean.getAppsecret(), code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
		 	log.info("WeixinController hyl() 网页授权接口访问凭证：" + accessToken);
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            SNSUserInfo snsUserInfo = WeixinWebOAuthUtil.getSNSUserInfo(accessToken, openId);
            model.addAttribute("userInfo", snsUserInfo);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "hyl";
	}
}
