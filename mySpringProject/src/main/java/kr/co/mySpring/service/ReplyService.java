package kr.co.mySpring.service;

import java.util.Map;

import kr.co.mySpring.vo.BoardVO;
import kr.co.mySpring.vo.ReplyVO;

public interface ReplyService {
	// ����Ʈ(�˻�) ����
	public Map<String, Object> listService(ReplyVO vo) throws Exception;
}