package kr.co.mySpring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class valueTest {
	
/*		@Value("#{emailInfo['email']}") 
		private String email;

	*/
	
		@Test
		public void getProperties() throws Exception{
			
			/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    
			Date tempDate = (Date)formatter.parse("2020-10-08 15:32:47");
			
			
			int sec   = 60;
			int min   = 60;
			int hour  = 24;
			int day   = 30;
			int month = 12;
			
			long curTime = System.currentTimeMillis();
			long regTime = tempDate.getTime();
			long diffTime = (curTime - regTime) / 1000;

			String msg = "";
			
			if (diffTime < sec) {
				// sec
				msg = "��� ��";
			} else if ((diffTime /= sec) < min) {
				// min
				msg = diffTime + "�� ��";
			} else if ((diffTime /= min) < hour) {
				// hour
				msg = (diffTime) + "�ð� ��";
			} else if ((diffTime /= hour) < day) {
				// day
				msg = (diffTime) + "�� ��";
			} else if ((diffTime /= day) < month) {
				// day
				msg = (diffTime) + "�� ��";
			} else {
				msg = (diffTime) + "�� ��";
			}
			
			System.out.println(msg);*/
			String msg = "[{reply_key=1, board_key=52, depth=0, user_id=tester1234, comment=�׽�Ʈ�� �޾Ҿ��, mem_idx=4, update_date=2020-10-08 15:32:47, group_key=1}, {reply_key=3, board_key=52, depth=1, user_id=tester1234, target_mem_idx=4, comment=���� �׽�Ʈ�� �޾Ҿ��, target_id=tester1234, mem_idx=4, update_date=2020-10-08 15:42:41, group_key=1}, {reply_key=2, board_key=52, depth=0, user_id=tester1234, comment=�׽�Ʈ�� �޾Ҿ��x2, mem_idx=4, update_date=2020-10-08 15:34:54, group_key=2}]";
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(msg);
			
			JSONArray json = new JSONArray(jsonStr);
			
		}
			
					
					
}
