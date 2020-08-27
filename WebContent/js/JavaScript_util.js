//保存URL前缀，首页加载时调用
function setURL(url){
	if(url == ""){
		var href = window.location.href;
		var str = href.substring(0,href.lastIndexOf("/"));
		href = href.substring(0,href.lastIndexOf("/")+1);
		sessionStorage.setItem("URL",href);
		sessionStorage.setItem("imgURL",str.substring(0,str.lastIndexOf("/")+1)+"image/DStorageFileStorage/");
	}else{
		sessionStorage.setItem("URL",url);
		url = url.substring(0,url.length-1);
		sessionStorage.setItem("imgURL",url.substring(0,url.lastIndexOf("/")+1)+"image/DStorageFileStorage/");
	}
}
//获取URL前缀
function getURL(){
	return sessionStorage.getItem("URL");
}
//获取图片服务器URL前缀
function getImgURL(){
	return sessionStorage.getItem("imgURL");
}
//跳转页面
function skipPage(pageName){
	parent.window.location.href=getURL()+pageName;
}
//显示头像和用户名
function showMyInfo(){
	var userName = sessionStorage.getItem("userName");
	var image = sessionStorage.getItem("image");
	document.getElementById('showImage').src = getImgURL() + "userImage/" + image;
	document.getElementById('showName').innerHTML = userName + "<span class='caret'>";
}
//退出登录
function logout(){
	$.confirm({
        title: '提示信息',
        content: '确认退出系统？',
        type: 'red',
        typeAnimated: true,
        buttons: {
            tryAgain: {
                text: '确认',
                btnClass: 'btn-red',
                action: function(){
                	$.ajax({
        				type : "POST",//方法类型
        				dataType : "json",//预期服务器返回的数据类型
        				url :  getURL() + "account/logout",
        				success : function(result) {
        					skipPage("login.html");
        				},
        				error : function(e){
        					
        				}
        			});
                }
            },
            close: {
                text: '关闭'
            }
        }
    });
}
//打开加密页面
function openPrivate(){
	$.confirm({
        title: '请输入访问密码',
        content: '' +
        '<form action="" class="formName">' +
        '<div class="form-group">' +
        '<input type="text" placeholder="" class="name form-control" required />' +
        '</div>' +
        '</form>',
        buttons: {
            formSubmit: {
                text: '提交',
                btnClass: 'btn-blue',
                action: function () {
                    var fileName = this.$content.find('.name').val();
                    if(!fileName){
                        $.alert('请您输入名称');
                        return false;
                    }
                    skipPage("share.html");
                }
            },
            cancel: {
                text: '取消'
            },
        },
        onContentReady: function () {
            var jc = this;
            this.$content.find('form').on('submit', function (e) {
                e.preventDefault();
                jc.$$formSubmit.trigger('click');
            });
        }
    });
}
//判断分享的文件是否加密
function getFileType(type){
	if(type == "lock"){
		return true;
	}else{
		return false;
	}
}
//修改当前定位目录
function changePath(name){
	var nowPath = sessionStorage.getItem("nowPath");
	if(name == -1){
		if(nowPath.indexOf(",") != -1){
			nowPath = nowPath.substring(0,nowPath.lastIndexOf(","));
		}
	}else{
		nowPath = nowPath + "," + name;
	}
	sessionStorage.setItem("nowPath",nowPath);
}
//获取当前目录
function getPath(){
	return sessionStorage.getItem("nowPath");
}

//获取ICON
function getFileIcon(fileName,size){
	if(size == -1){
		return "folder.png";
	}else{
		var fileType = "unknow.png";
		var type = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length);
		switch(type){
		 case "doc":
			 fileType = "doc.png";
			 break;
		 case "docx":
			 fileType = "docx.png";
			 break;
		 case "html":
			 fileType = "html.png";
			 break;
		 case "rar":
			 fileType = "rar.png";
			 break;
		 case "txt":
			 fileType = "txt.png";
			 break;
		 case "xls":
			 fileType = "xls.png";
			 break;
		 case "xlsx":
			 fileType = "xlsx.png";
			 break;
		 case "pdf":
			 fileType = "pdf.png";
			 break;
		 case "jpg":
			 fileType = "jpg.png";
			 break;
		 case "jpeg":
			 fileType = "jpeg.png";
			 break;
		 case "png":
			 fileType = "PNG.png";
			 break;
		}
		return fileType;
	}
}

//单位换算
function changeUnit(size){
	if(size < 1024){
		return (size + "B");
	}else if(size < 1024 * 1024){
		var size = size / 1024;
		var res = parseFloat(size.toFixed(2));
		return (res + "KB");
	}else if(size < 1024*1024*1024){
		var size = size / 1024 / 1024;
		var res = parseFloat(size.toFixed(2));
		return (res + "MB");
	}else if(size < 1024*1024*1024*1024){
		var size = size / 1024 / 1024 / 1024;
		var res = parseFloat(size.toFixed(24));
		return (res + "GB");
	}
}
//是否删除
function isDelete(){
	var res = confirm("确认删除?");
	if(!res){
		return false;
	}else{
		return true;
	}
}