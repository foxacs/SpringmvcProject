package common;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.util.StringUtils;

public class util {
	
	
	public static String makeAuth(int size) {
		
		StringBuffer buffer = new StringBuffer();
		
		Random rnd = new Random();
		
		for(int i =0; i<size; i++) {
			if(rnd.nextInt(2)==0) {
				buffer.append(String.valueOf((char) ((int) (rnd.nextInt(26)) + 97)));
			}else {
				buffer.append(String.valueOf(((int) (rnd.nextInt(10)))));
			}
			
			
		}
		
		return buffer.toString();
	}
	
	
	public static int sendMail(String email, String msg) 
	{
		String user 	= "foxacsco@gmail.com"; // ���̹��� ��� ���̹� ����, gmail��� gmail ����
        String password = "qudwls#1";   		// �н�����

        // SMTP ���� ������ �����Ѵ�.
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com"); 
        prop.put("mail.smtp.port", 465); 
        prop.put("mail.smtp.auth", "true"); 
        prop.put("mail.smtp.ssl.enable", "true"); 
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));

            //�����ڸ����ּ�
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); 

            //���� ������ �Է�
            message.setSubject("SIDE PROJECT : ȸ������ ������ȣ �ȳ�"); 

            //���� ������ �Է�
            message.setContent("<!DOCTYPE html>" + 
			            		"<html>" + 
			            		"<head>" + 
			            		"<meta charset=\"UTF-8\">" + 
			            		"<title>Insert title here</title>" + 
			            		"</head>" + 
			            		"<body>" + 
			            		"	<table width=\"670\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-top: 2px solid lightgray;border-right:1px solid #e7e7e7;border-left:1px solid #e7e7e7;border-bottom:1px solid #e7e7e7;\">" + 
			            		"        <tbody><tr>" + 
			            		"            <td style=\"background-color:#ffffff;padding:40px 30px 0 35px;text-align:center\">" + 
			            		"                <table width=\"605\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"text-align:left;font-family:'\\00b9d1\\00c740  \\00ace0\\00b515','\\00b3cb\\00c6c0'\">" + 
			            		"                    <tbody><tr>" + 
			            		"                        <td style=\"color: black;font-size:25px;text-align:left;width:352px;word-spacing:-1px;vertical-align:top;\">" + 
			            		"                        SIDE PROJECT ������ȣ�Դϴ�." + 
			            		"                        </td>" + 
			            		"                        <td rowspan=\"3\"></td>" + 
			            		"                    </tr>" + 
			            		"                    <tr>" + 
			            		"                        <td height=\"39\"></td>" + 
			            		"                    </tr>" + 
			            		"                    <tr>" + 
			            		"                        <td colspan=\"2\" style=\"font-size:13px;word-spacing:-1px;height:30px\">�Ʒ� ������ȣ�� �Է����ּ���</td>" + 
			            		"                </tr></tbody></table>" + 
			            		"            </td>" + 
			            		"        </tr>" + 
			            		"        <tr>" + 
			            		"            <td style=\"padding:39px 196px 70px\">" + 
			            		"                <table style=\"background-color: lightgray;font-family:'\\00b9d1\\00c740  \\00ace0\\00b515','\\00b3cb\\00c6c0';\" width=\"278\">" + 
			            		"                    <tbody><tr>" + 
			            		"                        <td height=\"49\" style=\"text-align:center;font-weight: bold;color: white;\">������ȣ : <span>"+msg+"</span></td>" + 
			            		"                    </tr>" + 
			            		"                </tbody></table>" + 
			            		"            </td>" + 
			            		"        </tr>" + 
			            		"    </tbody></table>" + 
			            		"</body>" + 
			            		"</html>","text/html; charset=UTF-8");
            
            // ����
            Transport.send(message);
            
            return 1;
            
        }catch(Exception e) {
        	e.printStackTrace();
        	return -1;
        }
        
	}
	
	public static boolean checkFile(String fileName) 
	{
		String[] PERMISSION_FILE_EXT_ARR = {"GIF", "JPEG", "JPG", "PNG", "BMP", "PDF", "MP4"};
		 
		if( !StringUtils.hasText(fileName) ) {
			return false;
		}
		
		String[] fileNameArr = fileName.split("\\.");
		
		if( fileNameArr.length == 0 ) {
			return false;
		}
		
		String ext = fileNameArr[fileNameArr.length - 1].toUpperCase();
		 
		boolean isPermissionFileExt = false;
		
		for( int i = 0; i < PERMISSION_FILE_EXT_ARR.length; i++ ) {
			if( PERMISSION_FILE_EXT_ARR[i].equals(ext) ) {
				isPermissionFileExt = true;
				break;
			}
		}
		
		return isPermissionFileExt;
	}

	
	public static String formatTimeString(Date tempDate) 
	{
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
		
		return msg;
	}
	
}
