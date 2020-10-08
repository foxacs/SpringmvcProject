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
	* <pre>�Խ��� ��ȸ ����</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	@Override
	public Map<String, Object> listService(BoardVO vo) throws Exception 
	{
		
		String errorMsg = "�Ͻ����� ��Ʈ��ũ ��� �� �ý��ۿ� ������ �߻��Ͽ����ϴ�. �����ڿ��� �����ϼ���.";
		
		try {
			long start = System.currentTimeMillis(); 
			
			String keyword 		= vo.getKeyword();			// �˻���
			String currentCount = vo.getCurrentCount();		// ���� ������
			String pageCount	= vo.getPageCount();		// ������ �� ��� ���� 
			String mem_userid	= vo.getMem_userid();		// ȸ�� id(���� �� �� ��ȸ�� ���� param)
			
			Map<String, Object> contents = new HashMap<>();
			Map<String, Object> resultMap= new HashMap<>();
			
			//1. �Ķ����üũ			
			//1.1. Ű����
			if(keyword==null) {
				keyword="";
				vo.setKeyword("");
			}
			
			//1.2. ���� ������
			if(currentCount==null) {
				currentCount = "1";
				vo.setCurrentCount("1");
			}
			
			//1.3. �������� ����
			if(pageCount==null) {
				pageCount = "10";
				vo.setPageCount(pageCount);
			}
			
			//2. ��� �� �� ���ϱ�
			int totalCount = 0;
			
			// ��ü ��ȸ
			if(mem_userid == null) {
				totalCount = dao.listCount(keyword);
			
			// �������� ��ȸ
			}else {
				totalCount = dao.userListCount(vo);
			}
			
			//3. ����¡ �� ���ϱ�
			int totalPage = 0;
			
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
				resultMap = JsonMessage.getResponseMessage("Y", "�˻������ �������� �ʽ��ϴ�.",null);
				resultMap.put("contents", contents);
				return resultMap; 
			}
			
			List<BoardVO> list = new ArrayList<>();

			//5. ���� ó��
			//5.1. ��� ������
			int fromRowNum = ((Integer.parseInt(currentCount) -1) * Integer.parseInt(pageCount));
			
			//5.2. DAO �ʿ� �Ķ���� ����
			Map<String, Object> map = new HashMap<>();
			map.put("keyword", keyword);
			map.put("fromRowNum", fromRowNum);
			map.put("pageCount", Integer.parseInt(pageCount));
			
			//6. �˻� ����� ���� ����Ʈ �˻�
			// ��ü ��ȸ
			if(mem_userid == null) 
			{
				list = dao.list(map);
			
			// �������� ��ȸ
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
	
	/************************************************************************************************
	* <pre>�Խ��� �󼼺��� ����</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> detailBoardService(BoardVO vo) throws Exception 
	{
		
		String errorMsg = "�Ͻ����� ��Ʈ��ũ ��� �� �ý��ۿ� ������ �߻��Ͽ����ϴ�. �����ڿ��� �����ϼ���.";
		
		try {
			long start = System.currentTimeMillis(); 
			
			String boardKey 	= vo.getBoard_key();
			
			//1. �Ķ����üũ			
			//1.1. pkŰ
			if(boardKey==null) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			Map<String, Object> contents = new HashMap<>();
			Map<String, Object> resultMap= new HashMap<>();
			
			//6. �˻� ����� ���� ����Ʈ �˻�
			List<BoardVO> list = dao.detailList(boardKey);
			
			if(list == null || list.size()==0) {
				JsonMessage.getResponseMessage("N", "errorMsg","[SQL_ERROR]settingList");
			}

			//7. ��� ��ȯ
			contents.put("DataList", list);
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
	
	/************************************************************************************************
	* <pre>�̹��� ���ε� ����</pre>
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

			// ����Ʈ �迭�� ��ȯ
			byte[] bytes = upload.getBytes();

			// �̹����� ���ε��� ���丮(���� ��η� ����)
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
	* <pre>�Խñ� ���� ����</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> insertBoard(BoardVO vo) throws Exception {
		String errorMsg = "�Խ� �� ���� �� �ý��� ������ �߻��߽��ϴ�.";
		
		try {
			
			String title 	= vo.getTitle();
			String content 	= vo.getContent();
			String user_id	= vo.getMem_userid();
			
			//1. �Ķ���� üũ
			//1.1. �̸���
			if(title == null || title.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [email]");
			}
			
			//1.2. ȸ�����̵�			
			if(content == null || content.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [userId]");
			}
			
			//1.3. ��й�ȣ
			if(user_id == null || user_id.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [userPw]");
			}
		
			//2. Sql Injection CHECK
			if(user_id.contains("--") || user_id.contains(" or") || user_id.contains(" or ") || user_id.contains("or ") || user_id.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			//3. �Խñ� ����
			int cnt = dao.insertBoard(vo);
			
			//4. ���ó�� �� ��ȯ
			if(cnt > 0) {
				return JsonMessage.getResponseMessage("Y", "���� ��ϵǾ����ϴ�.", null);
			}else {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
		//5. ����ó��
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, "[SERVICE_ERROR]insertBoard");
		}
	}
	
	/************************************************************************************************
	* <pre>�Խñ� ���� ����</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> deleteBoard(BoardVO vo) throws Exception {
		
		String errorMsg = "�Խ� �� ���� �� �ý��� ������ �߻��߽��ϴ�.";
		
		try {
		
			String board_key 	= vo.getBoard_key();
			
			//1. �Ķ���� üũ
			if(board_key == null || board_key.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [board_key]");
			}
			
			//3. �Խñ� ����
			int cnt = dao.deleteBoard(vo);
			
			//4. ���ó�� �� ��ȯ
			if(cnt < 0) {
				return JsonMessage.getResponseMessage("N", errorMsg, "[SQL_ERROR] RESULT : 0");
			}
			
			return JsonMessage.getResponseMessage("Y", "�Խ� ���� ���� �Ǿ����ϴ�.", null);
			
		//5. ����ó��
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, null);
		}	
	}
	
	/************************************************************************************************
	* <pre>�Խñ� ���� ����</pre>
	* 
	* @param  1. BoardVO vo
	* @return 2. Map<String, Object>
	* @throws X
	************************************************************************************************/
	public Map<String, Object> updateBoard(BoardVO vo) throws Exception {
		
		String errorMsg = "�Խ� �� ���� �� �ý��� ������ �߻��߽��ϴ�.";
		
		try {
			
			String board_key 	= vo.getBoard_key();
			String content 		= vo.getContent();
			String title		= vo.getTitle();
			
			//1. �Ķ���� üũ
			//1.1. �̸���
			if(title == null || title.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [email]");
			}
			
			//1.2. ȸ�����̵�			
			if(content == null || content.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [userId]");
			}

			//1.3. �Խñ� pkŰ
			if(board_key == null || board_key.equals("undefined")) {
				return JsonMessage.getResponseMessage("N", errorMsg, "Input Parameter Not Found [board_key]");
			}
			
		
			//2. Sql Injection CHECK
			if(board_key.contains("--") || board_key.contains(" or") || board_key.contains(" or ") || board_key.contains("or ") || board_key.contains("' or ")) {
				return JsonMessage.getResponseMessage("N", errorMsg, null);
			}
			
			//3. �Խñ� ����
			int cnt = dao.updateBoard(vo);
			
			//4. ���ó�� �� ��ȯ
			if(cnt < 0) {
				return JsonMessage.getResponseMessage("N", errorMsg, "[SQL_ERROR] RESULT : 0");
			}
			
			return JsonMessage.getResponseMessage("Y", "�Խ� ���� ���� �Ǿ����ϴ�.", null);
			
		//5. ����ó��
		} catch (Exception e) {
			e.printStackTrace();
			return JsonMessage.getResponseMessage("N", errorMsg, null);
		}	
	}
}

