package com.dcl.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/** 
 * �ж��û�Ȩ�޵�Spring MVC��������
 */
public class AuthorizedInterceptor  implements HandlerInterceptor {
	
	/**����������*/
	private static final String[] IGNORE_URI = {"login","share_public_file","/"};

	 /** 
     * �÷�����ҪpreHandle�����ķ���ֵΪtrueʱ�Ż�ִ�С�
     * �÷������������������֮��ִ�У���Ҫ����������������Դ��
     */  
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
		
	}

	 /** 
     * ���������preHandle��������ֵΪtrue��ʱ��Ż�ִ�С�
     * ִ��ʱ�����ڴ��������д���֮ ��Ҳ������Controller�ķ�������֮��ִ�С�
     */  
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView mv) throws Exception {
		
	}

	 /** 
     * preHandle�����ǽ��д����������õģ��÷�������Controller����֮ǰ���е��ã�
     * ��preHandle�ķ���ֵΪfalse��ʱ����������ͽ����ˡ� 
     * ���preHandle�ķ���ֵΪtrue��������ִ��postHandle��afterCompletion��
     */  
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		//�������
		response.setHeader("Access-Control-Allow-Origin", "*");
		/** Ĭ���û�û�е�¼ */
		boolean flag = false; 
		
		String servletPath = request.getServletPath();
		if(request.getQueryString() != null) {
			servletPath += "?" + request.getQueryString();
		}
		/**ѭ������ �ж������Ƿ��ڲ�����������*/
        for (String s : IGNORE_URI) {
            if (servletPath.contains(s)) {
                flag = true;
                break;
            }
        }
        /**��Ҫ���ص�������ж��δ��� */
		if(!flag) {
			/** 1.��ȡsession�е��û�  */
	    	String account = (String) request.getSession().getAttribute("account");
	    	/** 2.�ж��û��Ƿ��Ѿ���¼ */
	    	if(account == null || account.equals("")){
	    		 /** ����û�û�е�¼����ת����¼ҳ�� */
	    		request.getRequestDispatcher("/login.html").forward(request, response);
	    	}else{
	    		 flag = true;
	    	}
		}
        return flag;
	}

}
