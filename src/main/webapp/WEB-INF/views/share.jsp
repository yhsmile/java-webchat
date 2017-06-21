<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.smile.webchat.util.AdvancedUtil"%>
<%
        Map<String,Object>  ret = new HashMap<String,Object> ();
        ret=AdvancedUtil.getWxConfig(request);
        request.setAttribute("appId", ret.get("appId"));
        request.setAttribute("timestamp", ret.get("timestamp"));
        request.setAttribute("nonceStr", ret.get("nonceStr"));
        request.setAttribute("signature", ret.get("signature"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信分享功能</title>

<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<!-- 如需使用摇一摇周边功能，请引入 jweixin-1.1.0.js -->
<script type="text/javascript">

var url=window.location.href.split('#')[0];
url = url.replace(/&/g, '%26');
console.log(url);

//微信信息以及调用的配置
wx.config({
    debug: false, 
    appId: '${appId}', 
    timestamp: '${timestamp}', 
    nonceStr: '${nonceStr}', 
    signature: '${signature}',
    jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone'] 
});

wx.ready(function(){
	
    // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
    wx.onMenuShareTimeline({
        title: '第八篇 ：微信公众平台开发实战Java版之如何网页授权获取用户基本信息', // 分享标题
        link:"http://1m74216j69.51mypc.cn/webchat/share.html",
        imgUrl: "http://1m74216j69.51mypc.cn/static/images/4.jpg", // 分享图标
        success:function(){
        	 // 用户确认分享后执行的回调函数
        	 alert("分享成功");
        	 console.log("分享成功");
        },
        cancel: function () { 
            // 用户取消分享后执行的回调函数
        	 alert("取消分享"); 
        }
    });
    
    // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
    wx.onMenuShareAppMessage({
        title: '第七篇 ：微信公众平台开发实战Java版之如何获取微信用户基本信息', // 分享标题
        desc: "第七篇 ：微信公众平台开发实战Java版之如何获取微信用户基本信息", // 分享描述
        link:"http://1m74216j69.51mypc.cn/webchat/share.html",
        imgUrl: "http://1m74216j69.51mypc.cn/static/images/5.jpg", // 分享图标
        type: 'link', // 分享类型,music、video或link，不填默认为link
        success: function () { 
            // 用户确认分享后执行的回调函数
        },
        cancel: function () { 
            // 用户取消分享后执行的回调函数
        }
    });
    
    //获取“分享到QQ”按钮点击状态及自定义分享内容接口
    wx.onMenuShareQQ({
        title: '第六篇 ：微信公众平台开发实战Java版之如何自定义微信公众号菜单', // 分享标题
        desc: '第六篇 ：微信公众平台开发实战Java版之如何自定义微信公众号菜单', // 分享描述
        link: 'http://1m74216j69.51mypc.cn/webchat/share.html', // 分享链接
        imgUrl: 'http://1m74216j69.51mypc.cn/static/images/1.png', // 分享图标
        success: function () { 
           // 用户确认分享后执行的回调函数
        },
        cancel: function () { 
           // 用户取消分享后执行的回调函数
        }
    });
    
    //获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口
    wx.onMenuShareWeibo({
        title: '分享到腾讯微博标题', // 分享标题
        desc: '分享到腾讯微博描述', // 分享描述
        link: 'http://1m74216j69.51mypc.cn/webchat/share.html', // 分享链接
        imgUrl: 'http://1m74216j69.51mypc.cn/static/images/2.png', // 分享图标
        success: function () { 
           // 用户确认分享后执行的回调函数
        },
        cancel: function () { 
            // 用户取消分享后执行的回调函数
        }
    });
    
    //获取“分享到QQ空间”按钮点击状态及自定义分享内容接口
    wx.onMenuShareQZone({
        title: '分享到QQ空间标题', // 分享标题
        desc: '分享到QQ空间描述', // 分享描述
        link: 'http://1m74216j69.51mypc.cn/webchat/share.html', // 分享链接
        imgUrl: 'http://1m74216j69.51mypc.cn/static/images/3.jpg', // 分享图标
        success: function () { 
           // 用户确认分享后执行的回调函数
        },
        cancel: function () { 
            // 用户取消分享后执行的回调函数
        }
    });
});

wx.error(function(res){
	alert('wx.error: '+JSON.stringify(res));
});
</script>

</head>
<body>
	<div>
		<h3>欢迎来到微信分享页面</h3>
		<img alt="" src="http://1m74216j69.51mypc.cn/static/images/3.jpg">
	</div>
</body>
</html>