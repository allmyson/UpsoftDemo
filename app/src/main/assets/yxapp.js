//基础方法
var yxapp = function() {};
yxapp.callback = {
    callback:function(data){}
};

yxapp.show = function(obj){
    AndroidJsInterface.toast(obj);
};
///////////////////////////////基础数据获取///////////////////////////
yxapp.basedata = function() {};
//数据库操作
yxapp.basedata.getBaseData = function(str) {
	return AndroidJsInterface.getBaseData(str);
};

/////////////////////////////////储存相关///////////////////////////
yxapp.storage = function() {};

//sql操作
yxapp.storage.sql = function(sql) {
	return AndroidJsInterface.excuteSql(sql)
};

//keyvalue 取
yxapp.storage.getKeyValue = function(key) {
	return AndroidJsInterface.searchKeyValue(key);
};
//keyValue存储
yxapp.storage.saveKeyValue = function(key, value) {
	return AndroidJsInterface.saveKeyValue(key, value);
};
//keyvalue 刪
yxapp.storage.delKeyValue = function(key) {
	return AndroidJsInterface.delKeyValue(key);
};
/////////////////////////////////日志打印///////////////////////////
yxapp.log = function() {};
//日志打印
yxapp.log.writeLog = function(key, value) {
	return AndroidJsInterface.writeLog(key, value);
};

///////////////////////////////////界面组件/////////////////////
yxapp.ui = function() {};




//消息提示窗口
yxapp.ui.dialogBack = {
	title: "",
	msg: "",
	bta: "",
	btb: "",
	onClickBack: function(data) {}
};
yxapp.ui.dialog = function(obj) {
		yxapp.ui.dialogBack = obj;
	   AndroidJsInterface.showDialog(obj.title,obj.msg,obj.bta,obj.btb);
};




//列表选择
yxapp.ui.dialogListBack = {
	data: "",
	onClickBack: function(data) {}
};

yxapp.ui.listDialog = function(obj) {
		yxapp.ui.dialogListBack = obj;
	   AndroidJsInterface.showListDialog(obj.data);
};





//////////////////////////////////////////设备方法///////////////////////
//设备方法命名
yxapp.device = function() {};
 

yxapp.device.getDeviceInfo = function(){
    return AndroidJsInterface.getDeviceInfo();
}
 
 //选择图片或拍照
yxapp.device.pictureBack = {
 	max: 9,//最大选择张数
 	mode:2,//选择模式 1多选，2单
 	showCamera:true,//显示拍照
 	enablePreview:false,//预览
 	enableCrop:false,//剪切
	onClickBack: function(data) {}
};

yxapp.device.getPicture = function(obj) {
	   yxapp.device.pictureBack = obj;
	   AndroidJsInterface.choosePicture(obj.max,obj.mode,obj.showCamera,obj.enablePreview,obj.enableCrop);
};

yxapp.device.openCamera = function(obj){
    yxapp.device.pictureBack = obj
    AndroidJsInterface.openCamera();
}


 //获取录音
yxapp.device.audioBack = {
	onClickBack: function(data) {}
};

yxapp.device.getAudio = function(obj) {
		 yxapp.device.audioBack = obj;
	   AndroidJsInterface.startRecord();
};


//播放录音
yxapp.device.playAudio = function(url) {
	   AndroidJsInterface.playRecord(url);
};


//获取录像
yxapp.device.videoBack = {
	onClickBack: function(data) {}
};
yxapp.device.getVideo = function(obj) {
		 yxapp.device.videoBack = obj;
	   AndroidJsInterface.startVideo();
};


//播放录像
yxapp.device.playVideo = function(url) {
	   AndroidJsInterface.playVideo(url);
};



 //打开原生界面
yxapp.device.activity = {
 	name:"",
 	code:"",
 	data:"",
};
yxapp.device.openActivity = function(obj) {
		 yxapp.device.activity = obj;
	    AndroidJsInterface.openActivity(obj.name,obj.code,obj.data);
};

  
 
 //查看大图
 yxapp.device.picDetail = {
 	path:"",
 	positions:"",
};
  
  yxapp.device.intentToPicDetail = function(obj) {
		 yxapp.device.picDetail = obj;
	    AndroidJsInterface.intentToPicDetail(obj.path,obj.positions);
};
    
   

//扫描二维码
yxapp.device.scanCodeBack = {
	onClickBack: function(data) {}
};
yxapp.device.scanCode = function(obj) {
		 yxapp.device.scanCodeBack = obj;
	    AndroidJsInterface.scanCode();
};



//扫描NFC
yxapp.device.scanNfcBack = {
	onClickBack: function(data) {}
};
yxapp.device.scanNfc = function(obj) {
		 yxapp.device.scanNfcBack = obj;
	    AndroidJsInterface.scanNfc();
};


//返回上一页
yxapp.device.goBack = function(obj) {
	    AndroidJsInterface.goBack();
};


//关闭当前页面
yxapp.device.close = function(obj) {
	    AndroidJsInterface.close();
};

////////////////////////////////////////网络请求////////////////////////

//ajax命名
yxapp.ajax = function() {};
yxapp.basePath = AndroidJsInterface.getBaseData("ip");

//post请求
yxapp.ajax.post = function(params) {
	var defaultParams = {
		url: "",
		data: "",
		// 告诉jQuery不要去处理发送的数据
		processData: true,
		// 告诉jQuery不要去设置Content-Type请求头yxapp.storage.sql
		contentType: true,
		async: false,
		success: function(result) {

		},
		error: function(result) {

		}
	};
	var paramsObj = $.extend(defaultParams, params);
	$.ajax({
		url: yxapp.basePath + paramsObj.url,
		dataType: "json",
		async: paramsObj.async,
		contentType: paramsObj.contentType,
		processData: paramsObj.processData,
		contentType: "application/json; charset=utf-8",
		type: "post",
		data: JSON.stringify(paramsObj.data),
		cache: false,
		success: function(result) {
			if(0 == result.status) {
				var errorCode = result.errorCode;
				if("session_expired" == errorCode || "session_invalid" == errorCode) {
					//session过期或失效则跳转登录
					yxapp.base.toNativeLogin();
					return;
				}
			}
			paramsObj.success(result);
		},
		error: function(result) {
			paramsObj.error(result);
		}
	});
};

//get请求
yxapp.ajax.get = function(params) {

	var defaultParams = {
		url: "",
		data: "",
		processData: true,
		contentType: true,
		async: false,
		success: function(result) {

		},
		error: function(result) {

		}
	};

	var paramsObj = $.extend(defaultParams, params);
	$.ajax({
		url: yxapp.basePath + paramsObj.url,
		async: paramsObj.async,
		contentType: paramsObj.contentType,
		processData: paramsObj.processData,
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		type: "get",
		data: JSON.stringify(paramsObj.data),
		cache: false,
		success: function(result) {
			if(0 == result.status) {
				var errorCode = result.errorCode;
				if("session_expired" == errorCode || "session_invalid" == errorCode) {
					//session过期或失效则跳转登录
					yxapp.base.toNativeLogin();
					return;
				}
			}
			paramsObj.success(result);
		},
		error: function(result) {
			paramsObj.error(result);
		}
	});
};

yxapp.ajax.postFile = function(params) {
	var defaultParams = {
		url: "",
		data: "",
		// 告诉jQuery不要去处理发送的数据
		processData: true,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType: true,
		async: false,
		success: function(result) {

		},
		error: function(result) {

		}
	};
	var paramsObj = $.extend(defaultParams, params);
	$.ajax({
		url: yxapp.basePath + paramsObj.url,
		dataType: "json",
		async: paramsObj.async,
		contentType: paramsObj.contentType,
		processData: paramsObj.processData,
		type: "post",
		data: paramsObj.data,
		cache: false,
		success: function(result) {
			if(0 == result.status) {
				var errorCode = result.errorCode;
				if("session_expired" == errorCode || "session_invalid" == errorCode) {
					//session过期或失效则跳转登录
					yxapp.base.toNativeLogin();
					return;
				}
			}
			paramsObj.success(result);
		},
		error: function(result) {
			paramsObj.error(result);
		}
	});
};

