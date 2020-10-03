package kr.co.mySpring.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.mySpring.vo.MemberVO;
import kr.co.mySpring.vo.UserVO;


//자동 빈 등록 어노테이션 : @Component, @Controller, @Service, @Repository

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace = "kr.co.mapper.userMapper";

	// 로그인
	@Override
	public List<MemberVO> login(MemberVO vo) throws Exception {

		return sqlSession.selectList(namespace+".login",vo);
	}
	
	// 아이디 중복 체크	
	public Integer chkId(MemberVO vo) throws Exception {

		return sqlSession.selectOne(namespace+".chkId",vo);
	}

	// 이메일 중복 체크
	public Integer chkEmail(MemberVO vo) throws Exception {

		return sqlSession.selectOne(namespace+".chkEmail",vo);
	}

	// 인증키 저장
	public Integer insertAuthMsg(Map<String, Object> map) throws Exception {

		return sqlSession.insert(namespace+".insertAuthMsg",map);
	}
	
	// 인증키 저장
	public Map<String, Object> getAuthNumber(MemberVO vo) throws Exception {

		return sqlSession.selectOne(namespace+".getAuthNumber",vo);
	}

	// 인증키 저장
	public Integer insertUser(MemberVO vo) throws Exception {

		return sqlSession.insert(namespace+".insertUser",vo);
	}
		
}
