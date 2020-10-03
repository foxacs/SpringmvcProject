package kr.co.mySpring.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.mySpring.service.BoardServiceImpl;
import kr.co.mySpring.vo.BoardVO;
import kr.co.mySpring.vo.MemberVO;


@Controller
public class BoardController {
	
	@Autowired
	public BoardServiceImpl service;

	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public String board() throws Exception {
	
		return "board/board";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> userList(BoardVO vo) throws Exception {
		
		return service.listService(vo);
	}
	
	@RequestMapping(value = "/detailBoard", method = RequestMethod.GET)
	public ModelAndView detailBoard(BoardVO vo) throws Exception {
		
		Map<String, Object> map = service.detailBoardService(vo);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(map);
		
		JSONObject json = new JSONObject(jsonStr);
		
		JSONArray jsonArray =  json.getJSONObject("contents").getJSONArray("DataList");
		
		JSONObject tempJson = new JSONObject(); 
		
		if(jsonArray.length()>0) {
			tempJson = jsonArray.getJSONObject(0);
		}
		
		String title 		= tempJson.has("title"		) == false ? "" : tempJson.getString("title");
		String content 		= tempJson.has("content"	) == false ? "" : tempJson.getString("content");
		String user_id 		= tempJson.has("mem_userid"	) == false ? "" : tempJson.getString("mem_userid");
		String update_date 	= tempJson.has("update_date") == false ? "" : tempJson.getString("update_date");
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("content"	 , content		);
		model.addObject("title"		 , title		);
		model.addObject("user_id"	 , user_id		);
		model.addObject("update_date", update_date	);
		
		model.setViewName("board/detailBoard");
		
		return model;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert() throws Exception {
	
		return "board/insertBoard";
	}
	
	@RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
	public @ResponseBody String imageUpload(HttpServletRequest req, HttpServletResponse resp, @RequestParam MultipartFile upload) throws Exception {
		PrintWriter printWriter = null;
		
		JSONObject json = service.imgUpload(req, resp, upload);
		
		try {
			resp.setCharacterEncoding("utf-8");
	        resp.setContentType("text/html;charset=utf-8");
			
	        printWriter = resp.getWriter();
	        
	        if(json.length()==0) {
				json.put("uploaded", 0);
				json.put("error", "{'message': 업로드 중 문제가 발생했습니다.'}");
	        }
	        printWriter.println(json.toString());
	        
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			if(printWriter !=null) {printWriter.close();}
		}
	}
	
	//게시글 저장
	@RequestMapping(value = "/insertBoard", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> insertUser(BoardVO vo) throws Exception {
	
		return service.insertBoard(vo);
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detailboard() throws Exception {
		
		return "board/detailBoard";
	}
	
	@RequestMapping(value = "/mylist", method = RequestMethod.GET)
	public String userMyList(BoardVO vo) throws Exception {
		
		return "board/myBoard";
	}
	
	@RequestMapping(value = "/deleteBoard", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteBoard(BoardVO vo) throws Exception {
		
		return service.deleteBoard(vo);
	}
	
	@RequestMapping(value = "/moveUpdateBoard", method = RequestMethod.GET)
	public ModelAndView moveUpdateBoard(BoardVO vo) throws Exception {
		
		Map<String, Object> map = service.detailBoardService(vo);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(map);
		
		JSONObject json = new JSONObject(jsonStr);
		
		JSONArray jsonArray =  json.getJSONObject("contents").getJSONArray("DataList");
		
		JSONObject tempJson = new JSONObject(); 
		
		if(jsonArray.length()>0) {
			tempJson = jsonArray.getJSONObject(0);
		}
		
		System.out.println(tempJson.toString());
		
		String board_key 	= tempJson.has("board_key"	) == false ? "" : String.valueOf(tempJson.getInt("board_key"));
		String title 		= tempJson.has("title"		) == false ? "" : tempJson.getString("title");
		String content 		= tempJson.has("content"	) == false ? "" : tempJson.getString("content");
		String user_id 		= tempJson.has("mem_userid"	) == false ? "" : tempJson.getString("mem_userid");
		String update_date 	= tempJson.has("update_date") == false ? "" : tempJson.getString("update_date");
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("board_key"	 , board_key	);
		model.addObject("content"	 , content		);
		model.addObject("title"		 , title		);
		model.addObject("user_id"	 , user_id		);
		model.addObject("update_date", update_date	);
		
		model.setViewName("board/updateBoard");
		
		return model;
	}
	
	//게시글 저장
	@RequestMapping(value = "/updateBoard", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateBoard(BoardVO vo) throws Exception {
	
		return service.updateBoard(vo);
	}
	
	
}
