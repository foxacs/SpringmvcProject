package kr.co.mySpring.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.mySpring.vo.BoardVO;


//자동 빈 등록 어노테이션 : @Component, @Controller, @Service, @Repository
@Repository
public class BoardDAOImpl implements BoardDAO{
	
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace = "kr.co.mapper.boardMapper";

	public Integer listCount(String keyword) throws Exception {
		
		return sqlSession.selectOne(namespace+".boardCount", keyword);
	}
	
	public Integer userListCount(BoardVO vo) throws Exception {
		
		return sqlSession.selectOne(namespace+".userBoardCount", vo);
	}
	
	public List<BoardVO> list(Map<String, Object> map) throws Exception {
		
		return sqlSession.selectList(namespace+".boardList", map);
	}
	
	public List<BoardVO> userlist(Map<String, Object> map) throws Exception {
		
		return sqlSession.selectList(namespace+".userBoardList", map);
	}
	
	public List<BoardVO> detailList(String board_key) throws Exception {
		
		return sqlSession.selectList(namespace+".detailBoard", board_key);
	}
	
	public Integer insertBoard(BoardVO vo) throws Exception {
		
		return sqlSession.insert(namespace+".insertBoard", vo);
	}
	
	public Integer deleteBoard(BoardVO vo) throws Exception {
		
		return sqlSession.delete(namespace+".deleteBoard", vo);
	}
	
	public Integer updateBoard(BoardVO vo) throws Exception {
		
		return sqlSession.update(namespace+".updateBoard", vo);
	}
	
	
}
