package com.dcl.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.dcl.domain.Account;
import com.dcl.domain.Msg;
import com.dcl.service.AccountService;
import com.dcl.service.MsgService;
import com.dcl.utils.BasicUtils;
import com.dcl.utils.Beans;

import net.sf.json.JSONObject;


@ServerEndpoint("/websocket/{userId}")
public class WebSocketController {

	 /**
     * 用户在线数量
     */
    private static int onlineCount = 0;
    /**
     * 在线用户
     */
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, WebSocketController> userWebSocket = new ConcurrentHashMap<String, WebSocketController>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session WebSocketsession;
    /**
     * 当前登录用户
     */
    private String userId = "";
    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userId") String userId, Session WebSocketsession, EndpointConfig config) {
        this.userId = userId;//接收到发送消息的人员编号
        this.WebSocketsession = WebSocketsession;
        if(!userWebSocket.containsKey(userId)) {
        	userWebSocket.put(userId, this);//加入map中
        }
        addOnlineCount();           //在线数加1
    }
 
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (!userId.equals("")) {
            userWebSocket.remove(userId);  //从set中删除
            subOnlineCount();           //在线数减1
        }
    }
    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
    /**
     * 发送消息调用
     * @param message
     * @param toAccount
     */
    @OnMessage
    public void sendToUser(String message) {
    	JSONObject res = JSONObject.fromObject(message);
    	JSONObject json = new JSONObject();
    	String toAccount = res.getString("toAccount");
    	String msg = res.getString("msg");
        String now = BasicUtils.getDatetime();
        try {
            if (userWebSocket.get(toAccount) != null) {//用户在线直接发送
            	//封装消息数据
            	json.put("msg", msg);//消息内容
            	json.put("date", now);//消息时间
            	json.put("fromAData", getAccountData(userId));//发送消息用户资料
            	json.put("toData",  getAccountData(toAccount));//接收消息用户资料
                userWebSocket.get(toAccount).sendMessage(json.toString());
            } else {
                MsgService msgService = (MsgService) Beans.getBean("msgService");
                Msg m = new Msg(0,msg,userId,toAccount,BasicUtils.getDatetime(),"未读");
        		msgService.addMsg(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 给所有人发消息
     * @param message
     */
   /* private void sendAll(String message) {
        String now = BasicUtils.getDatetime();
        String sendMessage = message.split("[|]")[0];
        //遍历HashMap
        for (String key : userWebSocket.keySet()) {
            try {
                //判断接收用户是否是当前发消息的用户
                if (!userId.equals(key)) {
                    userWebSocket.get(key).sendMessage(now + "用户" + userId + "发来消息：" + " <br/> " + sendMessage);
                    System.out.println("key = " + key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
    /**
     * 消息发送
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.WebSocketsession.getBasicRemote().sendText(message);
    }
    /**
     * 获取在线用户数量
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    /**
     * 用户在线数量加1
     */
    public static synchronized void addOnlineCount() {
    	WebSocketController.onlineCount++;
    }
    /**
     * 用户在线数量减1
     */
    public static synchronized void subOnlineCount() {
    	WebSocketController.onlineCount--;
    }
    /**
     * 获取账号资料
     * @param account
     * @return
     */
    @SuppressWarnings("unused")
	private JSONObject getAccountData(String account) {
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("account", account);
    	AccountService accountService = (AccountService) Beans.getBean("accountService");
    	List<Account> ais = accountService.query(params);
    	if(ais.size() > 0) {
    		JSONObject json = JSONObject.fromObject(ais.get(0));
    		return json;
    	}else {
    		return null;
    	}
    }
}
