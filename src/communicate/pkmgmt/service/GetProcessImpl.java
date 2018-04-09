package communicate.pkmgmt.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import communicate.common.Constants;

public class GetProcessImpl implements GetProcessService.Iface{
	public GetProcessImpl() {
	}
	private static Map<String, List<String>> tmlProcessMap=null;
	private static final Logger log = Logger.getLogger(GetProcessImpl.class);
	private static List<String> processList=null;
	public String setProcess(String tmlId, String process) throws org.apache.thrift.TException {
		tmlProcessMap =Constants.tmlProcessMap;
		processList = tmlProcessMap.get(tmlId);
		if(processList==null){
			processList=new ArrayList<String>();
			processList.add(process);
			tmlProcessMap.put(tmlId, processList);
		}else{
			processList.add(process);
		}
		log.debug("判断前端是否将数据存入!");
		log.info("是否获取到前端传过来的值tmlid:"+tmlId+",process:"+process);	
		log.debug("判断是否put成功"+ tmlProcessMap.get(tmlId));
	    log.info("存储操作指令的 tmlProcessMap的大小："+tmlProcessMap.size());
	    for(String tmlIdStr:tmlProcessMap.keySet()){
	    	log.debug("终端Id tmlId: "+tmlIdStr);
	    	log.debug("终端Id tmlId 对应的process的集合："+tmlProcessMap.get(tmlIdStr));
	    }
	    
		return "set tmlId: " + tmlId + "   process:  " + process;
	}
	
	public static void main(String[] args) {
		 Map<String, String> map = new IdentityHashMap<String, String>();  
		    
		  
	        map.put("students", "11");  
	        map.put("students", "22");  
	  
	        Gson gson = new Gson();  
	        String s = gson.toJson(map);  
	        System.out.println(map.get("students"));  
	}
}
