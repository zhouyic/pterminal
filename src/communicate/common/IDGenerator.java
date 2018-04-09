package communicate.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class IDGenerator {
	public String generate(int len){
		Random r = new Random();
		int nRandom = 0;
		String strRandom = "";
		for (int nCount = 0; nCount < len; nCount++) {
			nRandom = r.nextInt();
			if (nRandom < 0) {
				nRandom *= -1;
			}

			nRandom %= 100;
			if (nRandom < 10) {
				strRandom += "0" + nRandom;
			} else
				strRandom += nRandom;
		}

		return strRandom;
	}

	public String generate32ID(String type) {
		if (type.length() != 10) {
			return null;
		}
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String sysDatetime = sdf.format(rightNow.getTime());

		Random r = new Random();
		int nRandom = 0;
		String strRandom = "";
		for (int nCount = 0; nCount < 3; nCount++) {
			nRandom = r.nextInt();
			if (nRandom < 0) {
				nRandom *= -1;
			}

			nRandom %= 100;
			if (nRandom < 10) {
				strRandom += "0" + nRandom;
			} else
				strRandom += nRandom;
		}

		String id = type + sysDatetime + "-" + strRandom;
		return id;
	}

	public static void main(String[] aa) {
		IDGenerator id = new IDGenerator();
		System.out.println(id.generate(2));
	}
}
