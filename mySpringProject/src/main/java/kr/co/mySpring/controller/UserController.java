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

	//��Ű�� ȸ�������� ����Ǿ� ���� �� ���������� �ƴ� ��� �α��� ������ ( �ڵ� �α��� ) 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login() throws Exception {
		
		return service.loginCheck();
	}

	//ȸ������ ������
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() throws Exception {
		
		return "login/join";
	}
	
	//�α���
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(MemberVO vo) throws Exception {
		
		return service.loginService(vo);
	}
	
	//�α׾ƿ�
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() throws Exception {
		
		service.logout();
		
		return "login/login";
	}
	
	//���̵� üũ
	@RequestMapping(value = "/chkId", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> chkUserId(MemberVO vo) throws Exception {
		
		return service.chkUserId(vo);
	}
	
	//�̸��� üũ �� �߼�
	@RequestMapping(value = "/chkEmail", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> chkEmail(MemberVO vo) throws Exception {
	
		return service.chkEmail(vo);
	}
	
	//������ȣ ��ȸ
	@RequestMapping(value = "/getAuthNumber", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getAuthNumber(MemberVO vo) throws Exception {
	
		return service.getAuthNumber(vo);
	}
	
	//ȸ������
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> insertUser(MemberVO vo) throws Exception {
	
		return service.insertUser(vo);
	}
	
}
