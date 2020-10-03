package kr.co.mySpring.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.mySpring.vo.MemberVO;
import kr.co.mySpring.vo.UserVO;


//�ڵ� �� ��� ������̼� : @Component, @Controller, @Service, @Repository

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace = "kr.co.mapper.userMapper";

	// �α���
	@Override
	public List<MemberVO> login(MemberVO vo) throws Exception {

		return sqlSession.selectList(namespace+".login",vo);
	}
	
	// ���̵� �ߺ� üũ	
	public Integer chkId(MemberVO vo) throws Exception {

		return sqlSession.selectOne(namespace+".chkId",vo);
	}

	// �̸��� �ߺ� üũ
	public Integer chkEmail(MemberVO vo) throws Exception {

		return sqlSession.selectOne(namespace+".chkEmail",vo);
	}

	// ����Ű ����
	public Integer insertAuthMsg(Map<String, Object> map) throws Exception {

		return sqlSession.insert(namespace+".insertAuthMsg",map);
	}
	
	// ����Ű ����
	public Map<String, Object> getAuthNumber(MemberVO vo) throws Exception {

		return sqlSession.selectOne(namespace+".getAuthNumber",vo);
	}

	// ����Ű ����
	public Integer insertUser(MemberVO vo) throws Exception {

		return sqlSession.insert(namespace+".insertUser",vo);
	}
		
}
