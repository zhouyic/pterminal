package communicate.common;

import java.util.StringTokenizer;


public class StringUtil {
	private final static String[] hexDigits = {
		"0", "1", "2", "3", "4", "5", "6", "7",
		"8", "9", "A", "B", "C", "D", "E", "F"};

	public static String intToHexString(int i){
		byte[] buffer = new byte[4];
		int offset = 0;
		buffer[offset++] = (byte)((i >> 24) & 0xff);
		buffer[offset++] = (byte)((i >> 16) & 0xff);
		buffer[offset++] = (byte)((i >> 8) & 0xff);
		buffer[offset++] = (byte)(i & 0xff);
		return byteArrayToHexString(buffer);
	}

	public static String byteArrayToHexString(byte[] b) {

		StringBuffer resultSb = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	public static String byteArrayToHexString(byte[] b,int offset,int len) {
		int blen = b.length;
		int tlen = offset+len;
		if(blen<tlen){
			return null;
		}
		StringBuffer resultSb = new StringBuffer();

		for (int i = offset; i < tlen; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}


	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static int getCount(String str,String sign){
		if(str==null || str.length()==0) return 0;
		StringTokenizer s=new StringTokenizer(str,sign);
		return s.countTokens();
	}

	public StringUtil() {
	}

	public static void main(String[] args) {
		String tt = "i";
		System.out.println(getCount(tt,"y"));

		byte[] p = new byte[4];
		for(int i = 0; i < p.length; i++){
			p[i] = (byte)12;

		}

		System.out.println(byteArrayToHexString(p,0,4));
	}

}
