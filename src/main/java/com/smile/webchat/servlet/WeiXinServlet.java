/**   
* @Title: WeiXinServlet.java
* @Package: com.smile.webchat.servlet
* @Description: 
* @author: ���  
* @date: 2017��6��19�� ����2:20:10
*
*/
package com.smile.webchat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smile.webchat.util.SignUtil;

/**
* @ClassName: WeiXinServlet
* @Description: 
* @author: ���
* @date: 2017��6��19�� ����2:20:10
* 
*/
public class WeiXinServlet extends HttpServlet{

	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -3021103284817658251L;
	
	/**
	 * ȷ����������΢�ŷ�����
	 */
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		
		// ΢�ż���ǩ��
        String signature = request.getParameter("signature");
        // ʱ���
        String timestamp = request.getParameter("timestamp");
        // �����
        String nonce = request.getParameter("nonce");
        // ����ַ���
        String echostr = request.getParameter("echostr");
        
        PrintWriter out = response.getWriter();
        
        // ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        
        out.close();
        out = null;
	}

	/**
	 * ����΢�ŷ�������������Ϣ
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO ��Ϣ�Ľ��ա�������Ӧ
    }
}
