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
	* <pre>��� ��ȸ</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> listService(ReplyVO vo) throws Exception {
		
		String errorMsg = "��� ��ȸ �� ������ �߻��߽��ϴ�.";
		
		try {
			long start = System.currentTimeMillis(); 
			
			String board_key    = vo.getBoard_key();
			String currentCount = vo.getCurrentCount();
			String pageCount	= vo.getPageCount();
			
			Map<String, Object> contents = new HashMap<>();
			Map<String, Object> resultMap= new HashMap<>();
			
			//1. �Ķ����üũ			
			//1.1. 
			if(board_key==null || board_key.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg,"Input Parameter Not Found [board_key]");
			}
			
			//1.2. ���� ������
			if(currentCount==null || currentCount.equals("undefined")) {
				currentCount = "1";
				vo.setCurrentCount("1");
			}
			
			//1.3. �������� ����
			if(pageCount==null || pageCount.equals("undefined")) {
				pageCount = "5";
				vo.setPageCount(pageCount);
			}
			
			//2. ��� �� �� ���ϱ�
			int totalCount = 0;
			
			//3. ����¡ �� ���ϱ�
			int totalPage = 0;
			
			totalCount = dao.listCount(board_key);
			
			if(totalCount % Integer.valueOf(pageCount) == 0)                                                                      
			{
				totalPage = (totalCount / Integer.valueOf(pageCount));                                                           
			}
			else                                                                                                                       
				totalPage = ((totalCount - (totalCount % Integer.valueOf(pageCount))) / Integer.valueOf(pageCount) + 1);
			
			//4. �˻� ����� ���� �� �����ȯ
			if(totalCount ==0) {
				
				contents.put("DataList", new ArrayList<>());
				contents.put("TotalCount" , totalCount);
				contents.put("TotalPage" , totalPage);
				resultMap = JsonMessage.getResponseMessage("Y", "",null);
				resultMap.put("contents", contents);
				return resultMap; 
			}
			
			List<ReplyVO> list = new ArrayList<>();

			//5. ���� ó��
			//5.1. ��� ������
			int fromRowNum = ((Integer.parseInt(currentCount) -1) * Integer.parseInt(pageCount));
			
			//5.2. DAO �ʿ� �Ķ���� ����
			Map<String, Object> map = new HashMap<>();

			map.put("fromRowNum", fromRowNum);
			map.put("pageCount", Integer.parseInt(pageCount));
			map.put("board_key", board_key);
			
			
			//6. �˻� ����� ���� ����Ʈ �˻�
			list = dao.list(map);
			
			if(list == null) {
				return JsonMessage.getResponseMessage("N", "errorMsg","[SQL_ERROR]list");
			}


			System.out.println(list.toString());
			contents.put("DataList"		, list);
			contents.put("TotalCount" 	, totalCount);
			contents.put("TotalPage" 	, totalPage);
			
			//7. ��� ��ȯ
			resultMap = JsonMessage.getResponseMessage("Y", "OK", null);
			resultMap.put("contents", contents);
			
			long end = System.currentTimeMillis(); //���α׷��� ������ ���� ���
			
			String runTime = "���� �ð� : " + ( end - start )/1000.0 +"��"; //���� �ð� ��� �� ���
			
			resultMap.put("runTime", runTime);
			
			return resultMap;
			
		}catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, e.getMessage());
		}
	}
}

