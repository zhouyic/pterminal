package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
public class TestOnlineMapTime {
	public static void main(String[] args) {
		testWhileByTime();
	}
	public static void testWhileByTime(){
		Map<String, Object> map=new HashMap<String, Object>();
		for(int i=1;i<=100000;i++){
			map.put("tmlId"+i, "process"+i);
		}
		int count=0;
		long startTime=System.currentTimeMillis(); 
		
		//遍历方式一
//		for(int j=1;j<=map.size();j++){
//		   long endTime=System.currentTimeMillis();   //获取当前毫秒数
//		   System.out.println("startTime:"+startTime);
//		   System.out.println("endTime:"+endTime);
//		   System.out.println("endTime-startTime:"+(endTime-startTime));
//		   System.out.println(count);
//		   count++;
//		   if((endTime-startTime)>=180000){
//			   System.out.println("循环三分钟 打印 数目："+count);
//			  break;
//		   }  
//		   System.out.println(map.get("tmlId"+j));
////		   System.out.println(map);
//		   if(j==map.size()){
//			   System.out.println("循环"+(endTime-startTime)/1000/60+"分钟 打印 数目："+count);
//		   }
//		   /*你要调用的方法*/        //在这里写你要调用的方法。
//		} 
		Set<String> keySet = map.keySet();
		long endTime=0;
		for(String s:keySet){
		   endTime=System.currentTimeMillis();   //获取当前毫秒数
		   System.out.println("startTime:"+startTime);
		   System.out.println("endTime:"+endTime);
		   System.out.println("endTime-startTime:"+(endTime-startTime));
		   System.out.println(count);
		   count++;
		   if((endTime-startTime)>=180000){
			   System.out.println("循环三分钟 打印 数目："+count);
			  break;
		   }  
		   System.out.println("key:  "+s+"    "+"value:  "+map.get(s));
//			   System.out.println(map);
		   /*你要调用的方法*/        //在这里写你要调用的方法。
		} 
		System.out.println("循环"+new Double(endTime-startTime)/new Double(1000)/new Double(60)+"分钟 打印 数目："+count);
	}
	public static void testFor(){
		long l1=System.currentTimeMillis();
		Map<String, Object> map=new HashMap<String, Object>();
		for(int i=1;i<=10000;i++){
			map.put("tmlId"+i, "process"+i);
		}
		
		long l2 = System.currentTimeMillis();
		for(int j=1;j<=map.size();j++){
			System.out.println(map);
		}
		long l3=System.currentTimeMillis();
		System.out.println("数据的长度："+map.size()); //10000
		System.out.println("保存数据的时间："+(l2-l1)); // 42
		
		System.out.println("轮询数据的时间："+(l3-l2));  //3704453
	}
}
