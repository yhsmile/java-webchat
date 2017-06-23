/**   
* @Title: WeixinMenuUtil.java
* @Package: com.smile.webchat.util
* @Description: 
* @author: 杨辉  
* @date: 2017年6月23日 上午11:30:39
*
*/
package com.smile.webchat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.smile.webchat.menu.Menu;

/**
* @ClassName: WeixinMenuUtil
* @Description: 微信公众菜单工具类
* @author: 杨辉
* @date: 2017年6月23日 上午11:30:39
* 
*/
public class WeixinMenuUtil {
	
	private static Logger log = LoggerFactory.getLogger(WeixinMenuUtil.class);
	
	
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	// 个性菜单创建（POST） 限100（次/天）
	public static String cond_menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN";
	
	// 获取菜单（POST）
	public static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	// 删除菜单（POST）
	public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
   

	/**
	 * 
	* @Title: createMenu
	* @author: 杨辉
	* @Description: 创建菜单
	* @param menu
	* @param accessToken
	* @return    
	* @return int 0表示成功，其他值表示失败
	* @throws
	 */
	public static int createMenu(Menu menu, String accessToken) {
        int result = 0;
        
        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.toJSONString(menu);
        
        // 调用接口创建菜单
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(url, "POST", jsonMenu);
        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                result = jsonObject.getIntValue("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }
	
	
	/**
	 * 
	* @Title: createConditionalMenu
	* @author: 杨辉
	* @Description: 创建个性化菜单
	* @param menu
	* @param accessToken
	* @return    
	* @return int    
	* @throws
	 */
	public static int createConditionalMenu(Menu menu, String accessToken){
		int result = 0;
		String url = cond_menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.toJSONString(menu);
        // 调用接口创建菜单
        JSONObject jsonObject = WeixinCommonUtil.httpsRequest(url, "POST", jsonMenu);
        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                result = jsonObject.getIntValue("errcode");
                log.error("创建个性化菜单失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }
		return result;
	}
	
	
	/**
	 * 
	* @Title: getMenu
	* @author: 杨辉
	* @Description: 获取菜单信息
	* @param accessToken
	* @return    
	* @return JSONObject    
	* @throws
	 */
	public static JSONObject getMenu(String accessToken){
		String url = menu_get_url.replace("ACCESS_TOKEN", accessToken);
		 JSONObject jsonObject = WeixinCommonUtil.httpsRequest(url, "POST", null);
		 log.info("查询菜单返回的结果：" + jsonObject.toJSONString());
		 return jsonObject;
	}
	
	/**
	 * 
	* @Title: deleteMenu
	* @author: 杨辉
	* @Description: 删除所有的菜单
	* @param accessToken
	* @return    
	* @return JSONObject    
	* @throws
	 */
	public static int deleteMenu(String accessToken){
		int result = 0;
		String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = WeixinCommonUtil.httpsRequest(url, "POST", null);
		log.info("查询菜单返回的结果：" + jsonObject.toJSONString());
		if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                result = jsonObject.getIntValue("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }
		return result;
	}
}
