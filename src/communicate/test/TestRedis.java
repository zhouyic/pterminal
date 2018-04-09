package communicate.test;

import communicate.common.net.JedisUtil;

public class TestRedis {
	/** 
     * @param args 
     */  
    public static void main(String[] args) {  
    	JedisUtil.setString("foo", "AAAAAAAAAAAAAAAAAAAAAAAAAAA");  
        String foo = JedisUtil.getString("foo");  
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(foo);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }  
}
