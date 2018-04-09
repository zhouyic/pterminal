package util.File;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Wang Zhe on 2015/7/29.
 */
public class FileUtil {

	private static final String UTF_8 = "UTF-8";

	private static final String Unicode = "Unicode";

	private static final String UTF_16BE = "UTF-16BE";

	private static final String ANSI_ASCII = "ANSI|ASCII";

	private static final String GBK = "GBK";

	/**
	 * �Զ�����ļ������ʽ��ȡ�ļ�
	 *
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static BufferedReader readFile(String filePath) throws Exception {
		return new BufferedReader(new InputStreamReader(new FileInputStream(filePath), codeString(filePath)));
	}

	public static String codeString(String fileName) throws Exception {
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
		int p = (bin.read() << 8) + bin.read();
		String code;
		// ���е� 0xefbb��0xfffe��0xfeff��0x5c75��Щ��������ļ���ǰ�������ֽڵ�16������
		switch (p) {
		case 0xefbb:
			code = UTF_8;
			break;
		case 0xfffe:
			code = Unicode;
			break;
		case 0xfeff:
			code = UTF_16BE;
			break;
		case 0x5c75:
			code = ANSI_ASCII;
			break;
		default:
			code = GBK;
		}

		return code;
	}

}
