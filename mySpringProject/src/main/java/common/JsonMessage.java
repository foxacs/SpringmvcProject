package common;

import java.util.HashMap;
import java.util.Map;

public class JsonMessage 
{	
	public static Map<String, Object> getResponseMessage(String code, String message, String debugMessage) 
	{
		try {
			Map<String, Object> responseJson = new HashMap<String, Object>();
			Map<String, Object> resultMsgJson = new HashMap<String, Object>();
			
			resultMsgJson.put("ResultCode", code);
			resultMsgJson.put("ResultMessage", message);
			
			debugMessage = debugMessage == null ? "" : debugMessage; 
			
			resultMsgJson.put("DebugMessage", debugMessage);
			responseJson.put ("Result", resultMsgJson);
			
			return responseJson;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
