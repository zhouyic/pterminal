package communicate.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
/**
 * ��־������,������־��ʹ��
 * @author Administrator
 *
 */
public class Logger extends org.apache.log4j.Logger {
	static{
		boolean flg = false;
		Properties p = new Properties();
		try {
				p.load(new FileInputStream(new File("/home/myconfig/tmllog4j.properties")));
//				p.load(new FileInputStream(new File("D:/workspace/eclipse/pterminalV2/conf/tmllog4j.properties")));
			PropertyConfigurator.configure(p);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				p.load(new FileInputStream(new File("/home/myconfig/tmllog4j.properties")));	
//				p.load(new FileInputStream(new File("D:/workspace/eclipse/pterminalV2/conf/tmllog4j.properties")));
				PropertyConfigurator.configure(p);
			} catch (Exception e1) {
				flg = false;
				e1.printStackTrace();
			}
		}
	}
	protected Logger(String name){
		super(name);
	}
	/**
	 * ��ȡ��־��¼��ľ�̬����
	 * @param clazz 
	 * @return   org.apache.log4j.Logger 
	 */
	public static org.apache.log4j.Logger getLogger(Class clazz){
		return org.apache.log4j.Logger.getLogger(clazz);
	}

}
