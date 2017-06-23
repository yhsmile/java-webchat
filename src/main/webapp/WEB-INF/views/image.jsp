<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		request.getSession().setAttribute("path",  path);
		request.getSession().setAttribute("basePath",  basePath);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信上传图片</title>

<script type="text/javascript" src="${basePath}/static/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<!-- 如需使用摇一摇周边功能，请引入 jweixin-1.1.0.js -->
<script type="text/javascript">

//$(function(){
	//微信信息以及调用的配置
	wx.config({
	    debug: false, 
	    appId: '${appId}', 
	    timestamp: '${timestamp}', 
	    nonceStr: '${nonceStr}', 
	    signature: '${signature}',
	    jsApiList: ['chooseImage','uploadImage','downloadImage'] 
	});
	
	
		wx.ready(function(){
			 var images = {
			    localId: [],
			    serverId: []
			  };
			
			
			$("#chooseImage").click(function(){
			    //拍照或从手机相册中选图接口
			    wx.chooseImage({
			        count: 2, // 默认9
			        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
			        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
			        success: function (res) {
			        	images.localId = res.localIds;
			            alert('已选择 ' + res.localIds.length + ' 张图片');
			            
			     //       var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
			     //       uploadImg(localIds); //上传图片
			        }
			    });
			});
			
			//上传图片
			$("#uploadImage").click(function(){
				if (images.localId.length == 0) {
			      alert('请先使用 chooseImage 接口选择图片');
			      return;
			    }
				
				var i = 0, length = images.localId.length;
			    images.serverId = [];
			    function upload() {
			        wx.uploadImage({
			          localId: images.localId[i],
			          success: function (res) {
			            i++;
			            alert('已上传：' + i + '/' + length + ",serverId:" + res.serverId);
			            $("#serverId").html(res.serverId);
			            images.serverId.push(res.serverId);
			            if (i < length) {
			              upload();
			            }
			          },
			          fail: function (res) {
			            alert(JSON.stringify(res));
			          }
			        });
			      }
			      upload();
			});
			
			
			//下载图片
			$("#downloadImage").click(function(){
				if (images.serverId.length === 0) {
			      alert('请先使用 uploadImage 上传图片');
			      return;
				}
			    var i = 0, length = images.serverId.length;
			    images.localId = [];
			    function download() {
			      wx.downloadImage({
			        serverId: images.serverId[i],
			        success: function (res) {
			          i++;
			          alert('已下载：' + i + '/' + length + ',地址：' + res.localId);
			          images.localId.push(res.localId);
			          if (i < length) {
			            download();
			          }
			        },
			        fail: function (res) {
			        	alert(JSON.stringify(res));
			        }
			      });
			    }
				download();
			});
			
			
			//查看本地图片
			$("#localImage").click(function(){
				$.get('${basePath}/webchat/local.html',{'mediaId':images.serverId[0]},function(re){
					
				});
			});
	});
	
	
//});

var url=window.location.href.split('#')[0];
url = url.replace(/&/g, '%26');
console.log(url);



wx.error(function(res){
	alert('wx.error: '+JSON.stringify(res));
});

</script>

</head>
<body>
	<div>
		<h3>欢迎来到微信图片页面</h3>
	</div>
	<div>
		<button id="chooseImage">选择图片</button>
		<button id="uploadImage">上传图片</button>
		<button id="downloadImage">下载图片</button>
		<button id="localImage">本地图片</button>
		
	</div>
	<div>
		<span id="serverId"></span>
	</div>
	<div id="imageList"></div>
</body>
</html>