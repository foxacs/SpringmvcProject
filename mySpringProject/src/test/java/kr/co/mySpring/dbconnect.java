package kr.co.mySpring;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

public class dbconnect {
	@Test
	public void dbtest() throws Exception{
		String driver = "com.mysql.cj.jdbc.Driver";
		String url 	  = "jdbc:mysql://localhost:3308/testdb?useSSL=false&serverTimezone=Asia/Seoul";
		String uid    = "root";
		String upw	  = "1234";
				
				
		Class.forName(driver);
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM user_info_tbl limit 0,10");
			
			while(rs.next()) {
				String user_id = rs.getString("user_id");
				System.out.println(user_id);
			}
						
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null)conn.close();
		}
	}
	
}
