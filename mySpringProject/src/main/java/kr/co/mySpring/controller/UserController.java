package kr.co.mySpring.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.mySpring.service.UserServiceImpl;
import kr.co.mySpring.vo.MemberVO;

@Controller
public class UserController {
	
	@Autowired
	public UserServiceImpl service;

	//쿠키에 회원정보가 저장되어 있을 시 메인페이지 아닐 경우 로그인 페이지 ( 자동 로그인 ) 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login() throws Exception {
		
		return service.loginCheck();
	}

	//회원가입 페이지
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() throws Exception {
		
		return "login/join";
	}
	
	//로그인
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(MemberVO vo) throws Exception {
		
		return service.loginService(vo);
	}
	
	//로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() throws Exception {
		
		service.logout();
		
		return "login/login";
	}
	
	//아이디 체크
	@RequestMapping(value = "/chkId", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> chkUserId(MemberVO vo) throws Exception {
		
		return service.chkUserId(vo);
	}
	
	//이메일 체크 및 발송
	@RequestMapping(value = "/chkEmail", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> chkEmail(MemberVO vo) throws Exception {
	
		return service.chkEmail(vo);
	}
	
	//인증번호 조회
	@RequestMapping(value = "/getAuthNumber", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getAuthNumber(MemberVO vo) throws Exception {
	
		return service.getAuthNumber(vo);
	}
	
	//회원가입
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> insertUser(MemberVO vo) throws Exception {
	
		return service.insertUser(vo);
	}
	
}
