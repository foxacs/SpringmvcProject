package kr.co.mySpring.service;

import java.util.Map;

import kr.co.mySpring.vo.BoardVO;

public interface BoardService {
	// ����Ʈ(�˻�) ����
	public Map<String, Object> listService(BoardVO vo) throws Exception;
}