package kr.co.mySpring.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import common.JsonMessage;
import common.util;
import kr.co.mySpring.dao.BoardDAOImpl;
import kr.co.mySpring.vo.BoardVO;


@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAOImpl dao;

	/************************************************************************************************
	* <pre>게시판 조회 서비스</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	@Override
	public Map<String, Object> listService(BoardVO vo) throws Exception 
	{
		
		String errorMsg = "일시적인 네트워크 장애 및 시스템에 문제가 발생하였습니다. 관리자에게 문의하세요.";
		
		try {
			long start = System.currentTimeMillis(); 
			
			String keyword 		= vo.getKeyword();			// 검색어
			String currentCount = vo.getCurrentCount();		// 현재 페이지
			String pageCount	= vo.getPageCount();		// 페이지 당 출력 갯수 
			String mem_userid	= vo.getMem_userid();		// 회원 id(내가 쓴 글 조회를 위한 param)
			
			Map<String, Object> contents = new HashMap<>();
			Map<String, Object> resultMap= new HashMap<>();
			
			//1. 파라미터체크			
			//1.1. 키워드
			if(keyword==null) {
				keyword="";
				vo.setKeyword("");
			}
			
			//1.2. 현재 페이지
			if(currentCount==null) {
				currentCount = "1";
				vo.setCurrentCount("1");
			}
			
			//1.3. 페이지당 갯수
			if(pageCount==null) {
				pageCount = "10";
				vo.setPageCount(pageCount);
			}
			
			//2. 결과 갯 수 구하기
			int totalCount = 0;
			
			// 전체 조회
			if(mem_userid == null) {
				totalCount = dao.listCount(keyword);
			
			// 내가쓴글 조회
			}else {
				totalCount = dao.userListCount(vo);
			}
			
			//3. 페이징 수 구하기
			int totalPage = 0;
			
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
				resultMap = JsonMessage.getResponseMessage("Y", "검색결과가 존재하지 않습니다.",null);
				resultMap.put("contents", contents);
				return resultMap; 
			}
			
			List<BoardVO> list = new ArrayList<>();

			//5. 정보 처리
			//5.1. 결과 시작점
			int fromRowNum = ((Integer.parseInt(currentCount) -1) * Integer.parseInt(pageCount));
			
			//5.2. DAO 필요 파라미터 세팅
			Map<String, Object> map = new HashMap<>();
			map.put("keyword", keyword);
			map.put("fromRowNum", fromRowNum);
			map.put("pageCount", Integer.parseInt(pageCount));
			
			//6. 검색 결과에 따른 리스트 검색
			// 전체 조회
			if(mem_userid == null) 
			{
				list = dao.list(map);
			
			// 내가쓴글 조회
			}else {	
				map.put("mem_userid", mem_userid);
				list = dao.userlist(map);
			}
			
			if(list == null) {
				return JsonMessage.getResponseMessage("N", "errorMsg","[SQL_ERROR]list");
			}
			
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
	
	/************************************************************************************************
	* <pre>게시판 상세보기 서비스</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> detailBoardService(BoardVO vo) throws Exception 
	{
		
		String errorMsg = "일시적인 네트워크 장애 및 시스템에 문제가 발생하였습니다. 관리자에게 문의하세요.";
		
		try {
			long start = System.currentTimeMillis(); 
			
			String boardKey 	= vo.getBoard_key();
			
			//1. 파라미터체크			
			//1.1. pk키
			if(boardKey==null) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			Map<String, Object> contents = new HashMap<>();
			Map<String, Object> resultMap= new HashMap<>();
			
			//6. 검색 결과에 따른 리스트 검색
			List<BoardVO> list = dao.detailList(boardKey);
			
			if(list == null || list.size()==0) {
				JsonMessage.getResponseMessage("N", "errorMsg","[SQL_ERROR]settingList");
			}

			//7. 결과 반환
			contents.put("DataList", list);
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
	
	/************************************************************************************************
	* <pre>이미지 업로드 서비스</pre>
	* 
	* @param  1. HttpServletRequest req, HttpServletResponse resp, MultipartFile upload
	* @return 2. JSONObject
	* @throws X
	************************************************************************************************/
	public JSONObject imgUpload(HttpServletRequest req, HttpServletResponse resp, MultipartFile upload) throws Exception 
	{
		JSONObject json = new JSONObject();
		OutputStream out = null;
		
		try {
			String fileName = upload.getOriginalFilename();

			// 바이트 배열로 변환
			byte[] bytes = upload.getBytes();

			// 이미지를 업로드할 디렉토리(배포 경로로 설정)
			String uploadPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\ROOT\\img\\";

			File uploadFile = new File(uploadPath);

			if (!uploadFile.exists()) {
				uploadFile.mkdir();
			}

			if (util.checkFile(fileName) == false) {
				return null;
			}

			String[] fileNameArr = fileName.split("\\.");
			String ext = fileNameArr[fileNameArr.length - 1].toUpperCase();

			fileName = String.valueOf(System.currentTimeMillis()) + "." + ext;

			uploadPath = uploadPath + "/" + fileName;

			out = new FileOutputStream(new File(uploadPath));
			out.write(bytes);

			String fileUrl = "http://121.161.228.140:8090/img/" + fileName;

			json.put("uploaded", 1);
			json.put("fileName", fileName);
			json.put("url", fileUrl);
			
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject();
		} finally {
			if (out != null) {
				out.close();
			}
			
		}
	}
	
	/************************************************************************************************
	* <pre>게시글 저장 서비스</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> insertBoard(BoardVO vo) throws Exception {
		String errorMsg = "게시 글 저장 중 시스템 오류가 발생했습니다.";
		
		try {
			
			String title 	= vo.getTitle();
			String content 	= vo.getContent();
			String user_id	= vo.getMem_userid();
			
			//1. 파라미터 체크
			//1.1. 이메일
			if(title == null || title.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [email]");
			}
			
			//1.2. 회원아이디			
			if(content == null || content.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [userId]");
			}
			
			//1.3. 비밀번호
			if(user_id == null || user_id.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [userPw]");
			}
		
			//2. Sql Injection CHECK
			if(user_id.contains("--") || user_id.contains(" or") || user_id.contains(" or ") || user_id.contains("or ") || user_id.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			//3. 게시글 저장
			int cnt = dao.insertBoard(vo);
			
			//4. 결과처리 및 반환
			if(cnt > 0) {
				return JsonMessage.getResponseMessage("Y", "글이 등록되었습니다.", null);
			}else {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
		//5. 예외처리
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]insertBoard");
		}
	}
	
	/************************************************************************************************
	* <pre>게시글 삭제 서비스</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> deleteBoard(BoardVO vo) throws Exception {
		
		String errorMsg = "게시 글 삭제 중 시스템 오류가 발생했습니다.";
		
		try {
		
			String board_key 	= vo.getBoard_key();
			
			//1. 파라미터 체크
			if(board_key == null || board_key.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [board_key]");
			}
			
			//3. 게시글 저장
			int cnt = dao.deleteBoard(vo);
			
			//4. 결과처리 및 반환
			if(cnt < 0) {
				return JsonMessage.getResponseMessage("N", errorMsg, "[SQL_ERROR] RESULT : 0");
			}
			
			return JsonMessage.getResponseMessage("Y", "게시 글이 삭제 되었습니다.", null);
			
		//5. 예외처리
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, null);
		}	
	}
	
	/************************************************************************************************
	* <pre>게시글 수정 서비스</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> updateBoard(BoardVO vo) throws Exception {
		
		String errorMsg = "게시 글 수정 중 시스템 오류가 발생했습니다.";
		
		try {
			
			String board_key 	= vo.getBoard_key();
			String content 		= vo.getContent();
			String title		= vo.getTitle();
			
			//1. 파라미터 체크
			//1.1. 이메일
			if(title == null || title.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [email]");
			}
			
			//1.2. 회원아이디			
			if(content == null || content.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [userId]");
			}

			//1.3. 게시글 pk키
			if(board_key == null || board_key.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [board_key]");
			}
			
		
			//2. Sql Injection CHECK
			if(board_key.contains("--") || board_key.contains(" or") || board_key.contains(" or ") || board_key.contains("or ") || board_key.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			//3. 게시글 수정
			int cnt = dao.updateBoard(vo);
			
			//4. 결과처리 및 반환
			if(cnt < 0) {
				return JsonMessage.getResponseMessage("N", errorMsg, "[SQL_ERROR] RESULT : 0");
			}
			
			return JsonMessage.getResponseMessage("Y", "게시 글이 수정 되었습니다.", null);
			
		//5. 예외처리
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, null);
		}	
	}
}

