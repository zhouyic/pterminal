package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import net.sf.json.JSONObject;

public class GetAddressByIp {

	public static void main(String[] args) {
		// String getAddressByIp = GetAddressByIp("169.235.24.133");
		long startTime = System.currentTimeMillis(); // 获取开始时间

		String getAddressByIp = GetAddressByIp("81.53.125.10");
		// {
		// "code": 0,
		// "data": {
		// "country": "����",
		// "country_id": "US",
		// "area": "",
		// "area_id": "",
		// "region": "",
		// "region_id": "",
		// "city": "",
		// "city_id": "",
		// "county": "",
		// "county_id": "",
		// "isp": "",
		// "isp_id": "",
		// "ip": "192.169.199.107"
		// }
		// }
		long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println(getAddressByIp + "///" + "程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
		// System.out.println("****************************************");
		// String getAddressByIpTest = GetAddressByIpTest("3.0.0.0");
		// System.out.println(getAddressByIpTest + " === testֵ");

	}

	/**
	 * 
	 * @param IP
	 * @return
	 */
	public static String GetAddressByIp(String IP) {
		String resout = "";
		try {
			String str = getJsonContent("http://ip.taobao.com/service/getIpInfo.php?ip=" + IP);

			// System.out.println(str);

			JSONObject obj = JSONObject.fromObject(str);
			JSONObject obj2 = (JSONObject) obj.get("data");
			String code = String.valueOf(obj.get("code"));
			if (code.equals("0")) {

				resout = (String) obj2.get("country");
				// resout = obj2.get("country") + "--" + obj2.get("area") + "--"
				// + obj2.get("city") + "--"
				// + obj2.get("isp");

			} else {
				resout = "IP未分配";
			}
		} catch (Exception e) {

			e.printStackTrace();
			resout = "内网IP" + e.getMessage();
		}
		return resout;

	}

	public static String GetAddressByIpTest(String IP) {
		String resout = "";
		try {
			String str = getJsonContent("http://ip.taobao.com/service/getIpInfo.php?ip=" + IP);

			System.out.println(str);

			// JSONObject obj = JSONObject.fromObject(str);
			// JSONObject obj2 = (JSONObject) obj.get("data");
			// String code = String.valueOf(obj.get("code"));
			// if (code.equals("0")) {
			//
			// resout = obj2.get("country") + "--" + obj2.get("area") + "--" +
			// obj2.get("city") + "--"
			// + obj2.get("isp");
			// } else {
			// resout = "IP��ַ����";
			// }
		} catch (Exception e) {

			e.printStackTrace();
			resout = "��ȡIP��ַ�쳣��" + e.getMessage();
		}
		return resout;

	}

	public static String getJsonContent(String urlStr) {
		try {// ��ȡHttpURLConnection���Ӷ���
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			// ������������
			httpConn.setConnectTimeout(3000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			// ��ȡ��Ӧ��
			int respCode = httpConn.getResponseCode();
			if (respCode == 200) {
				return ConvertStream2Json(httpConn.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String ConvertStream2Json(InputStream inputStream) {
		String jsonStr = "";
		// ByteArrayOutputStream�൱���ڴ������
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// ��������ת�Ƶ��ڴ��������
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// ���ڴ���ת��Ϊ�ַ�
			jsonStr = new String(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}
}
