package kr.co.mySpring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import common.JsonMessage;
import common.util;
import kr.co.mySpring.dao.UserDAOImpl;
import kr.co.mySpring.vo.MemberVO;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAOImpl dao;

	/************************************************************************************************
	* <pre>�ڵ� �α��� üũ ����</pre>
	* 
	* @param  1. void
	* @return 2. String
	* @throws X
	************************************************************************************************/
	public String loginCheck() throws Exception {
		
		try {
			String user_id = "";
			String password = "";
			
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
			
			if(request.getCookies() ==null) {
				return "login/login";
			}
			
			Cookie[] cookies = request.getCookies(); 
			
			for(int i =0; i< cookies.length; i++) 
			{
				if(cookies[i].getName().equals("side_user_id")) 
				{
					user_id = cookies[i].getValue();
				}
				else if(cookies[i].getName().equals("side_password")) {
					password = cookies[i].getValue();
				}
			}
			
			if(user_id.equals("") || password.equals("")) 
			{
				return "login/login";
			}
			
			//2. Sql Injection CHECK
			if(user_id.contains("--") || user_id.contains(" or") || user_id.contains(" or ") || user_id.contains("or ") || user_id.contains("' or ")) {
				return "login/login";
			}
			if(password.contains(" or") || password.contains(" or ") || password.contains("or ") || password.contains("' or ")) {		
				return "login/login";
			}
			
			MemberVO vo = new MemberVO();
			
			vo.setMem_userid(user_id);
			vo.setMem_password(password);
			
			//3. �α��� Ȯ��
			List<MemberVO> list = dao.login(vo);
			
			if(list.size()<1) 
			{
				return "login/login";
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("user_id", user_id);
			
			return "board/board";
					
		//5. ����ó��
		} catch (Exception e) {
			e.printStackTrace();
			return "login/login";
		}
	}
	
	
	/************************************************************************************************
	* <pre>�α��� ����</pre>
	* 
	* @param  1. MemberVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> loginService(MemberVO vo) throws Exception {
		String errorMsg = "�Ͻ����� ��Ʈ��ũ ��� �� �ý��ۿ� ������ �߻��Ͽ����ϴ�. �����ڿ��� �����ϼ���.";
		
		try {
			String user_id 	= vo.getMem_userid();
			String password = vo.getMem_password();
			String keepLogin= vo.getKeepLogin();
			
			//1. �Ķ���� üũ
			//1.1. ���̵�
			if(user_id == null) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [user_id]");
			}
			
			//1.2.��й�ȣ
			if(password == null) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [password]");
			}
			
			//2. Sql Injection CHECK
			if(user_id.contains("--") || user_id.contains(" or") || user_id.contains(" or ") || user_id.contains("or ") || user_id.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", "���������� �ùٸ��� �ʽ��ϴ�. �ٽ� �õ��� �ּ���.", null);
			}
			if(password.contains(" or") || password.contains(" or ") || password.contains("or ") || password.contains("' or ")) {		
				return JsonMessage.getResponseMessage("N", "���������� �ùٸ��� �ʽ��ϴ�. �ٽ� �õ��� �ּ���.", null);
			}
			
			//3. �α��� Ȯ��
			List<MemberVO> list = dao.login(vo);
			
			//4. ���ó��
			if(list.size()>=1) {
				
				//4.1. ���� ó��
				HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
				HttpSession session = request.getSession();
				session.setAttribute("user_id", user_id);
				
				HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
				
				if(keepLogin.equals("Y")) 
				{
					Cookie cookieId = new Cookie("side_user_id", user_id);
					Cookie cookiePw = new Cookie("side_password", password);
					
					cookieId.setPath("/");
					cookiePw.setPath("/");
					
					cookieId.setMaxAge(60 * 60 * 24 * 30);
					cookiePw.setMaxAge(60 * 60 * 24 * 30);
					
					response.addCookie(cookieId);
					response.addCookie(cookiePw);
					
				}
				else 
				{					
					Cookie[] cookies = request.getCookies(); 
					
					for(int i =0; i< cookies.length; i++) {
						if(cookies[i].getName().equals("side_user_id") || cookies[i].getName().equals("side_password")) {
					
							Cookie cookie = new Cookie(cookies[i].getName(),"");
							cookie.setPath("/");
							cookie.setMaxAge(0);
							
							response.addCookie(cookie);
						}
					}
				}
				//4.2. ��� ��ȯ
				return JsonMessage.getResponseMessage("Y", "�α��� �Ǿ����ϴ�.", null);
			}else {
				//4.2. ��� ��ȯ
				return JsonMessage.getResponseMessage("N", "���������� �ùٸ��� �ʽ��ϴ�.", null);
			}
			
			//5. ����ó��
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]loginService");
		}
	}
	
	/************************************************************************************************
	* <pre>���̵�üũ ����</pre>
	* 
	* @param  1. MemberVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> chkUserId(MemberVO vo) throws Exception {
		String errorMsg = "�Ͻ����� ��Ʈ��ũ ��� �� �ý��ۿ� ������ �߻��Ͽ����ϴ�. �����ڿ��� �����ϼ���.";
		
		try {
			
			String user_id 	= vo.getMem_userid();
			
			//1. �Ķ���� üũ
			//1.1. ���̵�
			if(user_id == null) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [user_id]");
			}
		
			//2. Sql Injection CHECK
			if(user_id.contains("--") || user_id.contains(" or") || user_id.contains(" or ") || user_id.contains("or ") || user_id.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			//3. �α��� Ȯ��
			int cnt = dao.chkId(vo);
			
			//4. ���ó��
			if(cnt >= 1) {
				//4.2. ��� ��ȯ
				return JsonMessage.getResponseMessage("N", "��� ���� ���̵��Դϴ�.", null);
			}else {
				//4.2. ��� ��ȯ
				return JsonMessage.getResponseMessage("Y", "��� �� �� �ִ� ���̵��Դϴ�.", null);
			}
			
		//5. ����ó��
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]chkUserId");
		}
	}
	
	/************************************************************************************************
	* <pre>�̸��� üũ �� ���� ����</pre>
	* 
	* @param  1. MemberVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> chkEmail(MemberVO vo) throws Exception {
		String errorMsg = "������ȣ ���� �� ������ �߻��߽��ϴ�. ������ ���ӵǴ� ��� �����ڿ��� �������ּ���.";
		
		try {
			
			String email 	= vo.getMem_email();
			
			//1. �Ķ���� üũ
			//1.1. �̸���
			if(email == null) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [email]");
			}
		
			//2. Sql Injection CHECK
			if(email.contains("--") || email.contains(" or") || email.contains(" or ") || email.contains("or ") || email.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			//3. ��ϵ� �̸��� Ȯ��
			int cnt = dao.chkEmail(vo);
			
			//4. ���ó��
			if(cnt >= 1) {
				//4.2. ��� ��ȯ
				return JsonMessage.getResponseMessage("N", "��ϵǾ��ִ� ȸ���� �̸����Դϴ�.", null);
			}else {
				
				Map<String, Object> insertMap = new HashMap<>();
				
				String authMsg = util.makeAuth(12);
				
				insertMap.put("email", email);
				insertMap.put("authMsg", authMsg);
				
				int insertAuthMsg = dao.insertAuthMsg(insertMap);
				
				if(insertAuthMsg<1) {
					return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]insertAuthMsg");
				}
				
				int sendchk = util.sendMail(email, authMsg);
				
				if(sendchk==-1) {
					return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]sendMail");
				}
				
				return JsonMessage.getResponseMessage("Y", "�ش� �̸������� ������ȣ�� �����߽��ϴ�.", null);
			}
			
		//5. ����ó��
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]chkEmail");
		}
	}
	
	/************************************************************************************************
	* <pre>������ȣ ��ȸ ����</pre>
	* 
	* @param  1. MemberVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> getAuthNumber(MemberVO vo) throws Exception {
		String errorMsg = "������ȣ �߼��� �ٽ� �������ּ���.";
		
		try {
			
			String email 	= vo.getMem_email();
			
			//1. �Ķ���� üũ
			//1.1. �̸���
			if(email == null) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [email]");
			}
		
			//2. Sql Injection CHECK
			if(email.contains("--") || email.contains(" or") || email.contains(" or ") || email.contains("or ") || email.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			Map<String, Object> contents = new HashMap<>();
			Map<String, Object> resultMap = new HashMap<>();
			
			//3. �������� Ȯ��
			contents = dao.getAuthNumber(vo);
			
			//4. ���ó��
			if(contents == null) {
				//4.2. ��� ��ȯ
				return JsonMessage.getResponseMessage("N", errorMsg, null);
				
			}else {
				resultMap = JsonMessage.getResponseMessage("Y", "OK", null);
				
				resultMap.put("contents", contents);

				//��� ��ȯ
				return resultMap;
			}
			
		//5. ����ó��
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]getAuthNumber");
		}
	}
	
	/************************************************************************************************
	* <pre>ȸ������ ����</pre>
	* 
	* @param  1. MemberVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> insertUser(MemberVO vo) throws Exception {
		String errorMsg = "ȸ������ �� ������ �߻��߽��ϴ�. �ٽ� �õ����ּ���.";
		
		try {
			
			String email 	= vo.getMem_email();
			String userId 	= vo.getMem_userid();
			String userPw	= vo.getMem_password();
			
			//1. �Ķ���� üũ
			//1.1. �̸���
			if(email == null || email.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [email]");
			}
			
			//1.2. ȸ�����̵�			
			if(userId == null || userId.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [userId]");
			}
			
			//1.3. ��й�ȣ
			if(userPw == null || userPw.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [userPw]");
			}
		
			//2. Sql Injection CHECK
			if(email.contains("--") || email.contains(" or") || email.contains(" or ") || email.contains("or ") || email.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			if(userId.contains("--") || userId.contains(" or") || userId.contains(" or ") || userId.contains("or ") || userId.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			if(userPw.contains("--") || userPw.contains(" or") || userPw.contains(" or ") || userPw.contains("or ") || userPw.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			//3. �������� Ȯ��
			int cnt = dao.insertUser(vo);
			
			//4. ���ó�� �� ��ȯ
			if(cnt > 0) {
				return JsonMessage.getResponseMessage("Y", "���������� ȸ�������� �Ǿ����ϴ�. ����Ͻ� ���̵�� �α������ּ���", null);
			}else {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
		//5. ����ó��
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]getAuthNumber");
		}
	}
	
	
	/************************************************************************************************
	* <pre>�α׾ƿ� ����</pre>
	* 
	* @param  1. x
	* @return 2. void
	* @throws X
	************************************************************************************************/
	public void logout() throws Exception {

		try {
				
				//1. ���� ����
				HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
				HttpSession session = request.getSession();
				session.removeAttribute("user_id");
				
				HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
				
				
				//2. ��Ű ����
				Cookie[] cookies = request.getCookies(); 
					
				for(int i =0; i< cookies.length; i++) {
					if(cookies[i].getName().equals("side_user_id") || cookies[i].getName().equals("side_password")) {
				
						Cookie cookie = new Cookie(cookies[i].getName(),"");
						cookie.setPath("/");
						cookie.setMaxAge(0);
							
						response.addCookie(cookie);
					}
				}
			
			//5. ����ó��
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}

