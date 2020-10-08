package kr.co.mySpring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import common.JsonMessage;
import kr.co.mySpring.dao.ReplyDAOImpl;
import kr.co.mySpring.vo.ReplyVO;


@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Autowired
	private ReplyDAOImpl dao;
	
	/************************************************************************************************
	* <pre>댓글 조회</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> listService(ReplyVO vo) throws Exception {
		
		String errorMsg = "댓글 조회 중 에러가 발생했습니다.";
		
		try {
			long start = System.currentTimeMillis(); 
			
			String board_key    = vo.getBoard_key();
			String currentCount = vo.getCurrentCount();
			String pageCount	= vo.getPageCount();
			
			Map<String, Object> contents = new HashMap<>();
			Map<String, Object> resultMap= new HashMap<>();
			
			//1. 파라미터체크			
			//1.1. 
			if(board_key==null || board_key.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg,"Input Parameter Not Found [board_key]");
			}
			
			//1.2. 현재 페이지
			if(currentCount==null || currentCount.equals("undefined")) {
				currentCount = "1";
				vo.setCurrentCount("1");
			}
			
			//1.3. 페이지당 갯수
			if(pageCount==null || pageCount.equals("undefined")) {
				pageCount = "5";
				vo.setPageCount(pageCount);
			}
			
			//2. 결과 갯 수 구하기
			int totalCount = 0;
			
			//3. 페이징 수 구하기
			int totalPage = 0;
			
			totalCount = dao.listCount(board_key);
			
			if(totalCount % Integer.valueOf(pageCount) == 0)                                                                      
			{
				totalPage = (totalCount / Integer.valueOf(pageCount));                                                           
			}
			else                                                                                                                       
				totalPage = ((totalCount - (totalCount % Integer.valueOf(pageCount))) / Integer.valueOf(pageCount) + 1);
			
			//4. 검색 결과가 없을 때 결과반환
			if(totalCount ==0) {
				
				contents.put("DataList", new ArrayList<>());
				contents.put("TotalCount" , totalCount);
				contents.put("TotalPage" , totalPage);
				resultMap = JsonMessage.getResponseMessage("Y", "",null);
				resultMap.put("contents", contents);
				return resultMap; 
			}
			
			List<ReplyVO> list = new ArrayList<>();

			//5. 정보 처리
			//5.1. 결과 시작점
			int fromRowNum = ((Integer.parseInt(currentCount) -1) * Integer.parseInt(pageCount));
			
			//5.2. DAO 필요 파라미터 세팅
			Map<String, Object> map = new HashMap<>();

			map.put("fromRowNum", fromRowNum);
			map.put("pageCount", Integer.parseInt(pageCount));
			map.put("board_key", board_key);
			
			
			//6. 검색 결과에 따른 리스트 검색
			list = dao.list(map);
			
			if(list == null) {
				return JsonMessage.getResponseMessage("N", "errorMsg","[SQL_ERROR]list");
			}


			System.out.println(list.toString());
			contents.put("DataList"		, list);
			contents.put("TotalCount" 	, totalCount);
			contents.put("TotalPage" 	, totalPage);
			
			//7. 결과 반환
			resultMap = JsonMessage.getResponseMessage("Y", "OK", null);
			resultMap.put("contents", contents);
			
			long end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산
			
			String runTime = "실행 시간 : " + ( end - start )/1000.0 +"초"; //실행 시간 계산 및 출력
			
			resultMap.put("runTime", runTime);
			
			return resultMap;
			
		}catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, e.getMessage());
		}
	}
}

