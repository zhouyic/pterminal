package util.File;

import org.junit.Test;

import util.ip.IpHelper;


/**
 * Created by Wang Zhe on 2015/8/11.
 */
public class ClientTest {

	@Test
	public void example() throws Exception {
		String ip = "192.168.10.106 ";
		String region = IpHelper.findRegionByIp(ip);
		System.out.println(region);

	}

	public static void main(String[] args) {
		String ip = "224.0.0.0";
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String region = IpHelper.findRegionByIp(ip);
		long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println(region + "///" + "程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
	}
}
