/**   
* @Title: MenuManager.java
* @Package: com.smile.webchat.menu
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 上午11:59:36
*
*/
package com.smile.webchat.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smile.webchat.model.Token;
import com.smile.webchat.util.WeixinCommonUtil;
import com.smile.webchat.util.WeixinMenuUtil;

/**
* @ClassName: MenuManager
* @Description: 菜单管理器类
* @author: 杨辉
* @date: 2017年6月20日 上午11:59:36
* 
*/
public class MenuManager {
	

	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

    public static void main(String[] args) {
        // 第三方用户唯一凭证
    	String appId = "wx638d3d4293105e85";
        // 第三方用户唯一凭证密钥
        String appSecret = "d4624c36b6795d1d99dcf0547af5443d";

        // 调用接口获取access_token
        Token token = WeixinCommonUtil.getToken(appId, appSecret);

        if (null != token) {
            // 调用接口创建菜单
//            int result = CommonUtil.createMenu(getMenu(), token.getAccessToken());
           
            //创建个性菜单
            int result = WeixinMenuUtil.createConditionalMenu(getMenu2(), token.getAccessToken());

            // 判断菜单创建结果
            if (0 == result)
                log.info("菜单创建成功！");
            else
                log.info("菜单创建失败，错误码：" + result);
        }
    }
    
    
    private static Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("天气预报");
        btn11.setType("click");
        btn11.setKey("11");

        CommonButton btn12 = new CommonButton();
        btn12.setName("公交查询");
        btn12.setType("click");
        btn12.setKey("12");

        CommonButton btn13 = new CommonButton();
        btn13.setName("周边搜索");
        btn13.setType("click");
        btn13.setKey("13");

        CommonButton btn14 = new CommonButton();
        btn14.setName("历史上的今天");
        btn14.setType("click");
        btn14.setKey("14");

        CommonButton btn21 = new CommonButton();
        btn21.setName("文本消息");
        btn21.setType("click");
        btn21.setKey("21");

        CommonButton btn22 = new CommonButton();
        btn22.setName("图片消息");
        btn22.setType("click");
        btn22.setKey("22");

        CommonButton btn23 = new CommonButton();
        btn23.setName("语音消息");
        btn23.setType("click");
        btn23.setKey("23");

        CommonButton btn24 = new CommonButton();
        btn24.setName("视频消息");
        btn24.setType("click");
        btn24.setKey("24");

        CommonButton btn25 = new CommonButton();
        btn25.setName("音乐消息");
        btn25.setType("click");
        btn25.setKey("25");

        CommonButton btn31 = new CommonButton();
        btn31.setName("图文消息");
        btn31.setType("click");
        btn31.setKey("31");

        CommonButton btn32 = new CommonButton();
        btn32.setName("电影排行榜");
        btn32.setType("click");
        btn32.setKey("32");

        CommonButton btn33 = new CommonButton();
        btn33.setName("联系我");
        btn33.setType("click");
        btn33.setKey("33");
        
        ViewButton btn34 = new ViewButton();
        btn34.setName("百度");
        btn34.setType("view");
        btn34.setUrl("http://www.baidu.com");

        /**
         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
         */
        
        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("生活助手");
        //一级下有4个子菜单
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14 });

        
        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("发送消息");
        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24, btn25 });

        
        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("更多体验");
        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33,btn34 });
        
        
        
        Matchrule matchrule = new Matchrule();
        matchrule.setTag_id("");
        matchrule.setSex("");
        matchrule.setCountry("中国");
        matchrule.setProvince("河南");
        matchrule.setCity("");
        matchrule.setClient_platform_type("");
        matchrule.setLanguage("");
        

        
        /**
         * 封装整个菜单
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
        menu.setMatchrule(matchrule);

        return menu;
    }
    
    private static Menu getMenu2() {
    	CommonButton btn21 = new CommonButton();
    	btn21.setName("歌曲点播");
    	btn21.setType("click");
    	btn21.setKey("21");
    	
    	CommonButton btn22 = new CommonButton();
    	btn22.setName("经典游戏");
    	btn22.setType("click");
    	btn22.setKey("22");
    	
    	CommonButton btn23 = new CommonButton();
    	btn23.setName("美女电台");
    	btn23.setType("click");
    	btn23.setKey("23");
    	
    	CommonButton btn24 = new CommonButton();
    	btn24.setName("人脸识别");
    	btn24.setType("click");
    	btn24.setKey("24");
    	
    	CommonButton btn25 = new CommonButton();
    	btn25.setName("聊天唠嗑");
    	btn25.setType("click");
    	btn25.setKey("25");
    	
    	CommonButton btn31 = new CommonButton();
    	btn31.setName("Q友圈");
    	btn31.setType("click");
    	btn31.setKey("31");
    	
    	CommonButton btn32 = new CommonButton();
    	btn32.setName("电影排行榜");
    	btn32.setType("click");
    	btn32.setKey("32");
    	
    	CommonButton btn33 = new CommonButton();
    	btn33.setName("联系我");
    	btn33.setType("click");
    	btn33.setKey("33");
    	
    	ViewButton btn34 = new ViewButton();
    	btn34.setName("百度");
    	btn34.setType("view");
    	btn34.setUrl("http://www.baidu.com");
    	
    	/**
    	 * 微信：  mainBtn2,mainBtn3底部的三个一级菜单。
    	 */
    	
    	ComplexButton mainBtn2 = new ComplexButton();
    	mainBtn2.setName("休闲驿站");
    	mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24, btn25 });
    	
    	
    	ComplexButton mainBtn3 = new ComplexButton();
    	mainBtn3.setName("更多体验");
    	mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33,btn34 });
    	
    	
    	
    	Matchrule matchrule = new Matchrule();
    	matchrule.setTag_id("");
    	matchrule.setSex("");
    	matchrule.setCountry("中国");
    	matchrule.setProvince("上海");
    	matchrule.setCity("");
    	matchrule.setClient_platform_type("");
    	matchrule.setLanguage("");
    	
    	
    	
    	/**
    	 * 封装整个菜单
    	 */
    	Menu menu = new Menu();
    	menu.setButton(new Button[] { mainBtn2, mainBtn3 });
    	menu.setMatchrule(matchrule);
    	
    	return menu;
    }
}
