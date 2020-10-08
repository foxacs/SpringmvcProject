package kr.co.mySpring.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import common.util;
import kr.co.mySpring.service.ReplyServiceImpl;
import kr.co.mySpring.vo.ReplyVO;


@Controller
public class ReplyController {

	@Autowired
	public ReplyServiceImpl service;
	
	@RequestMapping(value = "/replyList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> replyList(ReplyVO vo) throws Exception {
		
		Map<String, Object> map = service.listService(vo);
		
		String jsonStr = new ObjectMapper().writeValueAsString(map);
		JSONObject json = new JSONObject(jsonStr);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date tempDate = null;
		
		HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
	    
		try {
			JSONArray jsonArray =  json.getJSONObject("contents").getJSONArray("DataList");
			
			JSONObject tempJson = new JSONObject(); 
			
			if(jsonArray.length()>0) {
				for(int i =0; i<jsonArray.length(); i++) {
					tempJson = jsonArray.getJSONObject(i);
					
					String update_date 	= tempJson.has("update_date") == false ? "" : tempJson.getString("update_date");
					
					tempDate = (Date)formatter.parse(update_date);
					
					String tempStr = util.formatTimeString(tempDate);
					
					jsonArray.getJSONObject(i).put("update_date", tempStr);
				}
			}
			
			json.getJSONObject("contents").put("DataList", jsonArray);
			
			return new ResponseEntity<String>(json.toString(),httpHeaders,HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<String>(json.toString(),httpHeaders,HttpStatus.OK);
		}
	}
}
