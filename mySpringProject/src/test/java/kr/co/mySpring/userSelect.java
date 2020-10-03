package kr.co.mySpring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.mySpring.dao.UserDAOImpl;
import kr.co.mySpring.vo.UserVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class userSelect {
	
	//�α׸� ��� ����
    private static final Logger logger = LoggerFactory.getLogger(userSelect.class);
	
	@Inject
	UserDAOImpl dao;
	
	@Test
	public void selectTest() throws Exception{
		
		try {
			long start = System.currentTimeMillis(); 
			
			//int cnt = dao.listCount("asas");
			
			//System.out.println(cnt);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", "asas");
			map.put("fromRowNum", 0);
			map.put("pageCount", 10);
			
			List<UserVO> list = new ArrayList<>();
			
			//list = dao.list(map);
			
			System.out.println(list.toString());
			
			long end = System.currentTimeMillis(); //���α׷��� ������ ���� ���
			System.out.println( "���� �ð� : " + ( end - start )/1000.0 +"��"); //���� �ð� ��� �� ���

						
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
	}
}
