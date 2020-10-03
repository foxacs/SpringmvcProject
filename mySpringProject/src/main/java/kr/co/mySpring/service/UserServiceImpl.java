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
	* <pre>자동 로그인 체크 서비스</pre>
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
			
			//3. 로그인 확인
			List<MemberVO> list = dao.login(vo);
			
			if(list.size()<1) 
			{
				return "login/login";
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("user_id", user_id);
			
			return "board/board";
					
		//5. 예외처리
		} catch (Exception e) {
			e.printStackTrace();
			return "login/login";
		}
	}
	
	
	/************************************************************************************************
	* <pre>로그인 서비스</pre>
	* 
	* @param  1. MemberVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> loginService(MemberVO vo) throws Exception {
		String errorMsg = "일시적인 네트워크 장애 및 시스템에 문제가 발생하였습니다. 관리자에게 문의하세요.";
		
		try {
			String user_id 	= vo.getMem_userid();
			String password = vo.getMem_password();
			String keepLogin= vo.getKeepLogin();
			
			//1. 파라미터 체크
			//1.1. 아이디
			if(user_id == null) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [user_id]");
			}
			
			//1.2.비밀번호
			if(password == null) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [password]");
			}
			
			//2. Sql Injection CHECK
			if(user_id.contains("--") || user_id.contains(" or") || user_id.contains(" or ") || user_id.contains("or ") || user_id.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", "인증정보가 올바르지 않습니다. 다시 시도해 주세요.", null);
			}
			if(password.contains(" or") || password.contains(" or ") || password.contains("or ") || password.contains("' or ")) {		
				return JsonMessage.getResponseMessage("N", "인증정보가 올바르지 않습니다. 다시 시도해 주세요.", null);
			}
			
			//3. 로그인 확인
			List<MemberVO> list = dao.login(vo);
			
			//4. 결과처리
			if(list.size()>=1) {
				
				//4.1. 세션 처리
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
				//4.2. 결과 반환
				return JsonMessage.getResponseMessage("Y", "로그인 되었습니다.", null);
			}else {
				//4.2. 결과 반환
				return JsonMessage.getResponseMessage("N", "인증정보가 올바르지 않습니다.", null);
			}
			
			//5. 예외처리
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]loginService");
		}
	}
	
	/************************************************************************************************
	* <pre>아이디체크 서비스</pre>
	* 
	* @param  1. MemberVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> chkUserId(MemberVO vo) throws Exception {
		String errorMsg = "일시적인 네트워크 장애 및 시스템에 문제가 발생하였습니다. 관리자에게 문의하세요.";
		
		try {
			
			String user_id 	= vo.getMem_userid();
			
			//1. 파라미터 체크
			//1.1. 아이디
			if(user_id == null) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [user_id]");
			}
		
			//2. Sql Injection CHECK
			if(user_id.contains("--") || user_id.contains(" or") || user_id.contains(" or ") || user_id.contains("or ") || user_id.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			//3. 로그인 확인
			int cnt = dao.chkId(vo);
			
			//4. 결과처리
			if(cnt >= 1) {
				//4.2. 결과 반환
				return JsonMessage.getResponseMessage("N", "사용 중인 아이디입니다.", null);
			}else {
				//4.2. 결과 반환
				return JsonMessage.getResponseMessage("Y", "사용 할 수 있는 아이디입니다.", null);
			}
			
		//5. 예외처리
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]chkUserId");
		}
	}
	
	/************************************************************************************************
	* <pre>이메일 체크 및 전송 서비스</pre>
	* 
	* @param  1. MemberVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> chkEmail(MemberVO vo) throws Exception {
		String errorMsg = "인증번호 전송 중 에러가 발생했습니다. 현상이 지속되는 경우 관리자에게 문의해주세요.";
		
		try {
			
			String email 	= vo.getMem_email();
			
			//1. 파라미터 체크
			//1.1. 이메일
			if(email == null) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [email]");
			}
		
			//2. Sql Injection CHECK
			if(email.contains("--") || email.contains(" or") || email.contains(" or ") || email.contains("or ") || email.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			//3. 등록된 이메일 확인
			int cnt = dao.chkEmail(vo);
			
			//4. 결과처리
			if(cnt >= 1) {
				//4.2. 결과 반환
				return JsonMessage.getResponseMessage("N", "등록되어있는 회원의 이메일입니다.", null);
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
				
				return JsonMessage.getResponseMessage("Y", "해당 이메일으로 인증번호를 전송했습니다.", null);
			}
			
		//5. 예외처리
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]chkEmail");
		}
	}
	
	/************************************************************************************************
	* <pre>인증번호 조회 서비스</pre>
	* 
	* @param  1. MemberVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> getAuthNumber(MemberVO vo) throws Exception {
		String errorMsg = "인증번호 발송을 다시 진행해주세요.";
		
		try {
			
			String email 	= vo.getMem_email();
			
			//1. 파라미터 체크
			//1.1. 이메일
			if(email == null) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [email]");
			}
		
			//2. Sql Injection CHECK
			if(email.contains("--") || email.contains(" or") || email.contains(" or ") || email.contains("or ") || email.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			Map<String, Object> contents = new HashMap<>();
			Map<String, Object> resultMap = new HashMap<>();
			
			//3. 인증정보 확인
			contents = dao.getAuthNumber(vo);
			
			//4. 결과처리
			if(contents == null) {
				//4.2. 결과 반환
				return JsonMessage.getResponseMessage("N", errorMsg, null);
				
			}else {
				resultMap = JsonMessage.getResponseMessage("Y", "OK", null);
				
				resultMap.put("contents", contents);

				//결과 반환
				return resultMap;
			}
			
		//5. 예외처리
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]getAuthNumber");
		}
	}
	
	/************************************************************************************************
	* <pre>회원가입 서비스</pre>
	* 
	* @param  1. MemberVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> insertUser(MemberVO vo) throws Exception {
		String errorMsg = "회원가입 중 오류가 발생했습니다. 다시 시도해주세요.";
		
		try {
			
			String email 	= vo.getMem_email();
			String userId 	= vo.getMem_userid();
			String userPw	= vo.getMem_password();
			
			//1. 파라미터 체크
			//1.1. 이메일
			if(email == null || email.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [email]");
			}
			
			//1.2. 회원아이디			
			if(userId == null || userId.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [userId]");
			}
			
			//1.3. 비밀번호
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
			
			//3. 인증정보 확인
			int cnt = dao.insertUser(vo);
			
			//4. 결과처리 및 반환
			if(cnt > 0) {
				return JsonMessage.getResponseMessage("Y", "성공적으로 회원가입이 되었습니다. 등록하신 아이디로 로그인해주세요", null);
			}else {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
		//5. 예외처리
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]getAuthNumber");
		}
	}
	
	
	/************************************************************************************************
	* <pre>로그아웃 서비스</pre>
	* 
	* @param  1. x
	* @return 2. void
	* @throws X
	************************************************************************************************/
	public void logout() throws Exception {

		try {
				
				//1. 세션 제거
				HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
				HttpSession session = request.getSession();
				session.removeAttribute("user_id");
				
				HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
				
				
				//2. 쿠키 제거
				Cookie[] cookies = request.getCookies(); 
					
				for(int i =0; i< cookies.length; i++) {
					if(cookies[i].getName().equals("side_user_id") || cookies[i].getName().equals("side_password")) {
				
						Cookie cookie = new Cookie(cookies[i].getName(),"");
						cookie.setPath("/");
						cookie.setMaxAge(0);
							
						response.addCookie(cookie);
					}
				}
			
			//5. 예외처리
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}

