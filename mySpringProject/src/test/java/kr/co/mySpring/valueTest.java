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

public class valueTest {
	
/*		@Value("#{emailInfo['email']}") 
		private String email;

	*/
	
		@Test
		public void getProperties() throws Exception{
			
			 System.out.println(System.currentTimeMillis());

			
	/*		System.out.println(email);*/
		}
}
