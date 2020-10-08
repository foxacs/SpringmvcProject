package kr.co.mySpring.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.mySpring.vo.ReplyVO;


//자동 빈 등록 어노테이션 : @Component, @Controller, @Service, @Repository
@Repository
public class ReplyDAOImpl implements ReplyDAO{
	
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace = "kr.co.mapper.replyMapper";

	public Integer listCount(String keyword) throws Exception {
		
		return sqlSession.selectOne(namespace+".replyCount", keyword);
	}
	
	public List<ReplyVO> list(Map<String, Object> map) throws Exception {
		
		return sqlSession.selectList(namespace+".replyList", map);
	}
	
}
