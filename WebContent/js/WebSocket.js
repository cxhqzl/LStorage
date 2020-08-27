var websocket = null;
/**
 * 创建WebSocket连接
 * @param account 用户账号
 * @param func 将消息显示到页面的方法
 * @returns
 */
function getWebSocket(account){
		var url = sessionStorage.getItem("URL");
		if(url.substring(0,5) == "https"){
			url = url.replace("http","w");
		}else{
			url = url.replace("http","ws");
		}
    	if ('WebSocket' in window){
    		websocket = new WebSocket(url + "websocket/"+account);
    	}else{
    		alert('当前浏览器不支持WebSocket通信！');
	   	}
    	//连接发生错误的回调方法
        websocket.onerror = function () {
    		alert("连接失败，请重试！")
        };
        //连接成功建立的回调方法
        websocket.onopen = function () {
            console.log("连接成功");
        }
        //接收到消息的回调方法
        websocket.onmessage = function (event) {
        	showFriendMessage(event.data);
        }
        //连接关闭的回调方法
        websocket.onclose = function () {
        	 console.log("WebSocket连接关闭");
        }
        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
       /* window.onbeforeunload = function () {
        	//websocket.close();
        }*/
        return websocket;
}
//显示来自好友消息
function showFriendMessage(msg){
	msg = JSON.parse(msg);
	saveMsgToLocal(msg.msg,msg.date,1,msg.fromAData.account);
	var count = sessionStorage.getItem("count");
	var msgFlag = sessionStorage.getItem("msgFlag");
	if( msgFlag == 0){
		sessionStorage.setItem("msgFlag",1);
		var chartId = sessionStorage.getItem("chartId");
		var str = `
		<div class="chat-message chat-message-left">
	        <div class="chat-message-text">
	            <span>${msg.msg}</span>
	        </div>
	        <div class="chat-message-meta">
	            <span>${msg.date}</span>
	        </div>
	    </div>
		`;
		document.getElementById(chartId).innerHTML = str;
	}else{
		var chartId = sessionStorage.getItem("chartId");
		var div = document.getElementById(chartId);
		var div1 = document.createElement("div");
		div1.setAttribute("class","chat-message chat-message-left");
		var id = chartId + "_" + count;
		count += 1;
		sessionStorage.setItem("count",count);
		div1.setAttribute("id",id);
		div.appendChild(div1);
		var str = `
	         <div class="chat-message-text">
	            <span>${msg.msg}</span>
	         </div>
	         <div class="chat-message-meta">
	             <span>${msg.date}</span>
	         </div>
		`;
		document.getElementById(id).innerHTML = str;
	}
	
}
//显示自己的消息
function showMyMsg(msg){
	var chartId = sessionStorage.getItem("chartId");
	var msgFlag = sessionStorage.getItem("msgFlag");
	var count = sessionStorage.getItem("count");
	if(msgFlag == 0){
		sessionStorage.setItem("msgFlag",1);
		var str = `
			<div class="chat-message chat-message-right">
		        <div class="chat-message-text">
		            <span>${msg}</span>
		        </div>
		        <div class="chat-message-meta">
		            <span>${getDateTime()}</span>
		        </div>
		    </div>
			`;
		document.getElementById(chartId).innerHTML = str;
	}else{
		var chartId = sessionStorage.getItem("chartId");
		var div = document.getElementById(chartId);
		var div1 = document.createElement("div");
		div1.setAttribute("class","chat-message chat-message-right");
		var id = chartId + "_" + count;
		count += 1;
		sessionStorage.setItem("count",count);
		div1.setAttribute("id",id);
		div.appendChild(div1);
		var str = `
	         <div class="chat-message-text">
	            <span>${msg}</span>
	         </div>
	         <div class="chat-message-meta">
	             <span>${getDateTime()}</span>
	         </div>
		`;
		document.getElementById(id).innerHTML = str;
	}
	
}
//发送消息
function send() {
    var message = $("#msg").val();//要发送的消息内容
    $("#msg").val("");
    showMyMsg(message);
    var ToSendUserno = sessionStorage.getItem("friendAccount"); //接收人编号：4567
    saveMsgToLocal(message,getDateTime(),0,ToSendUserno);
    var json = {"msg":message,"toAccount":ToSendUserno};
    message=message+"|"+ToSendUserno;//将要发送的信息和内容拼起来，以便于服务端知道消息要发给谁
    websocket.send(JSON.stringify(json));
}
function getDateTime() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

//本地消息存储器
function saveMsgToLocal(msg,date,type,account){
	var msgs = localStorage.getItem("local_msg_" + account);
	if(msgs == null || msgs == ""){
		msgs = sessionStorage.getItem("account") + "[|]" + msg + "#@$@#" + date + "#@$@#" + type;
	}else{
		msgs = msgs + "[|||]" + msg + "#@$@#" + date + "#@$@#" + type;
	}
	localStorage.setItem("local_msg_" + account,msgs);
}
//解析岑地缓存的聊天记录
function localMsgAnalyse(account){
	var msgData = new Array();
	var msgs = localStorage.getItem("local_msg_" + account);
	if(msgs == null || msgs == ""){
		return msgData;
	}
	var account1 = msgs.split("[|]")[0];
	if(account1 != sessionStorage.getItem("account")){
		return msgData;
	}
	var str = msgs.split("[|]")[1];
	var str1 = str.split("[|||]");
	for(var i=0;i<str1.length;i++){
		var json = {"msg": str1[i].split("#@$@#")[0],"date":str1[i].split("#@$@#")[1],"type":str1[i].split("#@$@#")[2]};
		msgData.push(json);
	}
	return msgData;
}