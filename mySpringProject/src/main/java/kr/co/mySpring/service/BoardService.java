package kr.co.mySpring.service;

import java.util.Map;

import kr.co.mySpring.vo.BoardVO;

public interface BoardService {
	// 리스트(검색) 서비스
	public Map<String, Object> listService(BoardVO vo) throws Exception;
}