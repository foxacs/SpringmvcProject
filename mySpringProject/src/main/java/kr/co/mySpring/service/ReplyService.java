package kr.co.mySpring.service;

import java.util.Map;

import kr.co.mySpring.vo.BoardVO;
import kr.co.mySpring.vo.ReplyVO;

public interface ReplyService {
	// 리스트(검색) 서비스
	public Map<String, Object> listService(ReplyVO vo) throws Exception;
}