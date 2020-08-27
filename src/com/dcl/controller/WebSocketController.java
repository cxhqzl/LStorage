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
     * �û���������
     */
    private static int onlineCount = 0;
    /**
     * �����û�
     */
    //concurrent�����̰߳�ȫSet���������ÿ���ͻ��˶�Ӧ��MyWebSocket������Ҫʵ�ַ�����뵥һ�ͻ���ͨ�ŵĻ�������ʹ��Map����ţ�����Key����Ϊ�û���ʶ
    private static ConcurrentHashMap<String, WebSocketController> userWebSocket = new ConcurrentHashMap<String, WebSocketController>();
    /**
     * ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
     */
    private Session WebSocketsession;
    /**
     * ��ǰ��¼�û�
     */
    private String userId = "";
    /**
     * ���ӽ����ɹ����õķ���
     *
     * @param session ��ѡ�Ĳ�����sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userId") String userId, Session WebSocketsession, EndpointConfig config) {
        this.userId = userId;//���յ�������Ϣ����Ա���
        this.WebSocketsession = WebSocketsession;
        if(!userWebSocket.containsKey(userId)) {
        	userWebSocket.put(userId, this);//����map��
        }
        addOnlineCount();           //��������1
    }
 
    /**
     * ���ӹرյ��õķ���
     */
    @OnClose
    public void onClose() {
        if (!userId.equals("")) {
            userWebSocket.remove(userId);  //��set��ɾ��
            subOnlineCount();           //��������1
        }
    }
    /**
     * ��������ʱ����
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("��������");
        error.printStackTrace();
    }
    /**
     * ������Ϣ����
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
            if (userWebSocket.get(toAccount) != null) {//�û�����ֱ�ӷ���
            	//��װ��Ϣ����
            	json.put("msg", msg);//��Ϣ����
            	json.put("date", now);//��Ϣʱ��
            	json.put("fromAData", getAccountData(userId));//������Ϣ�û�����
            	json.put("toData",  getAccountData(toAccount));//������Ϣ�û�����
                userWebSocket.get(toAccount).sendMessage(json.toString());
            } else {
                MsgService msgService = (MsgService) Beans.getBean("msgService");
                Msg m = new Msg(0,msg,userId,toAccount,BasicUtils.getDatetime(),"δ��");
        		msgService.addMsg(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * �������˷���Ϣ
     * @param message
     */
   /* private void sendAll(String message) {
        String now = BasicUtils.getDatetime();
        String sendMessage = message.split("[|]")[0];
        //����HashMap
        for (String key : userWebSocket.keySet()) {
            try {
                //�жϽ����û��Ƿ��ǵ�ǰ����Ϣ���û�
                if (!userId.equals(key)) {
                    userWebSocket.get(key).sendMessage(now + "�û�" + userId + "������Ϣ��" + " <br/> " + sendMessage);
                    System.out.println("key = " + key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
    /**
     * ��Ϣ����
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.WebSocketsession.getBasicRemote().sendText(message);
    }
    /**
     * ��ȡ�����û�����
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    /**
     * �û�����������1
     */
    public static synchronized void addOnlineCount() {
    	WebSocketController.onlineCount++;
    }
    /**
     * �û�����������1
     */
    public static synchronized void subOnlineCount() {
    	WebSocketController.onlineCount--;
    }
    /**
     * ��ȡ�˺�����
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
