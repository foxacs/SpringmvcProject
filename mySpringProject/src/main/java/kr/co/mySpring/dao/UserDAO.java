package kr.co.mySpring.dao;

import java.util.List;

import kr.co.mySpring.vo.MemberVO;

public interface UserDAO {
	
	// ·Î±×ÀÎ 
	public List<MemberVO> login(MemberVO vo) throws Exception;

	
}
