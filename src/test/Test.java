package test;

import net.sf.json.JSONObject;



public class Test {

	public static void main(String[] args) {
	
//		String json = "[{\"a\":\"111\",\"b\":\"222\",\"c\":\"333\"},{\"a\":\"1000\",\"b\":\"2000\",\"c\":\"000\"},{\"a\":\"999\",\"b\":\"300\",\"c\":\"700\"}]";
		String json = "{\"result\":[{\"cmdNo\":\"70\",\"isSucceed\":\"222\"},{\"cmdNo\":\"80\",\"isSucceed\":\"2000\"},{\"cmdNo\":\"90\",\"isSucceed\":\"300\"}]}";

		JSONObject jsonObject = JSONObject.fromObject(json);
        System.out.println(jsonObject.getString("result"));
       
		}
}