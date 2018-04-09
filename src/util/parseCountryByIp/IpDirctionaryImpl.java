package util.parseCountryByIp;
//
//import java.util.List;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.cfg.AnnotationConfiguration;
//
//import com.domain.IpDictionary;
//import com.util.HibernateUtils;
//
public class IpDirctionaryImpl {
//
//	private static final SessionFactory sessionFactory = new AnnotationConfiguration().configure()
//			.buildSessionFactory();
//
//	public static Session getSession() {
//		return sessionFactory.getCurrentSession();
//	}
//
//	public static SessionFactory getSessionFactory() {
//		return sessionFactory;
//	}
//
//	/**
//	 * 判断IP地址的合法性，这里采用了正则表达式的方法来判断 return true，合法
//	 */
//	public static boolean ipCheck(String text) {
//		if (text != null && !text.isEmpty()) {
//			// 定义正则表达式
//			String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
//					+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
//					+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
//			// 判断ip地址是否与正则表达式匹配
//			if (text.matches(regex)) {
//				// 返回判断信息
//				return true;
//			} else {
//				// 返回判断信息
//				return false;
//
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * 获取终端ID与经销商名称的映射关系
//	 * 
//	 * @return
//	 */
//	public static String getIpAndCourty(String ip) {
//		boolean b = ipCheck(ip);
//		if (b) {
//			int ipInt = ipToInt(ip);
//			Session session = sessionFactory.getCurrentSession();
//			Transaction transaction = session.beginTransaction();
//			List<IpDictionary> list = session.createQuery("from IpDictionary").list();
//			IpDictionary ipDictionary = null;
//			String ip_s;
//			String ip_e;
//			int ips;
//			int ipe;
//			for (int i = 0; i < list.size(); i++) {
//				ipDictionary = list.get(i);
//				ip_s = ipDictionary.getIpIdStart();
//				ip_e = ipDictionary.getIpIdEnd();
//				ips = ipToInt(ip_s);
//				ipe = ipToInt(ip_e);
//
//				if (ips <= ipInt && ipInt <= ipe) {
//					// System.out.println(ip_s + "/" + ips + "<" + ip + "<"
//					// +
//					// ip_e +
//					// "/" + ipe);
//					String country = list.get(i).getCountry();
//					// System.out.println(list.get(i).getCountry());
//
//					return country;
//				} /*
//					 * else { String country = "未分配的IP或者内网IP"; return country; }
//					 */
//			}
//		}
//		return "您输入的IP有误，请重新 输入。";
//
//	}
//
//	/**
//	 * 静�?方法，传入ip地址，返回ip地址�?��城市或地�? * @param ip IP地址，例�?8.30.15.255
//	 * 
//	 * @return 返回IP地址�?��城市或地区，例：北京�?
//	 */
//	String ip = "46.16.248.110";
//
//	public String findRegionByIp(String ip) {
//		return findIp(ip);
//	}
//
//	// public List<IpDictionary> getIpRelation() throws Exception {
//	// List<IpDictionary> list = getIpAndCourty();
//	// return list;
//	//
//	// }
//
//	/**
//	 * IpTree
//	 */
//
//	private IpNode rootNode = new IpNode();
//
//	private final String NO_ADDRESS = "未知";
//
//	private IpDirctionaryImpl() {
//
//	}
//
//	public void train(String ipStart, String ipEnd, String addressCode) {
//
//		int ip_s = ipToInt(ipStart);
//		int ip_e = ipToInt(ipEnd);
//
//		if (ip_e == -1 || ip_s == -1)
//			return;
//
//		IpNode curNode = rootNode;
//		IpNode leftNode = null;
//		IpNode rightNode = null;
//		boolean flag = false;
//
//		for (int i = 0; i < 32; i++) {
//
//			int ip_s_bit = (0x80000000 & ip_s) >>> 31;
//			int ip_e_bit = (0x80000000 & ip_e) >>> 31;
//
//			if (flag == false) {
//
//				if ((ip_s_bit ^ ip_e_bit) == 0) {
//
//					if (ip_s_bit == 1) {
//						if (curNode.rightNode == null) {
//							curNode.rightNode = new IpNode();
//						}
//						curNode = curNode.rightNode;
//					} else {
//						if (curNode.leftNode == null) {
//							curNode.leftNode = new IpNode();
//						}
//						curNode = curNode.leftNode;
//					}
//					if (i == 31) {
//						curNode.addressCode = addressCode;
//					}
//
//				} else {
//					flag = true;
//					if (curNode.leftNode == null) {
//						curNode.leftNode = new IpNode();
//					}
//					leftNode = curNode.leftNode;
//
//					if (curNode.rightNode == null) {
//						curNode.rightNode = new IpNode();
//					}
//
//					rightNode = curNode.rightNode;
//
//					if (i == 31) {
//						leftNode.addressCode = addressCode;
//						rightNode.addressCode = addressCode;
//					}
//				}
//			} else {
//				if (ip_s_bit == 1) {
//					if (leftNode.rightNode == null) {
//						leftNode.rightNode = new IpNode();
//					}
//					leftNode = leftNode.rightNode;
//				} else {
//					if (leftNode.leftNode == null) {
//						leftNode.leftNode = new IpNode();
//					}
//					if (leftNode.rightNode == null) {
//						leftNode.rightNode = new IpNode();
//					}
//					leftNode.rightNode.addressCode = addressCode;
//					leftNode = leftNode.leftNode;
//				}
//				if (i == 31)
//					leftNode.addressCode = addressCode;
//
//				if (ip_e_bit == 1) {
//					if (rightNode.rightNode == null) {
//						rightNode.rightNode = new IpNode();
//					}
//					if (rightNode.leftNode == null) {
//						rightNode.leftNode = new IpNode();
//					}
//					rightNode.leftNode.addressCode = addressCode;
//					rightNode = rightNode.rightNode;
//				} else {
//					if (rightNode.leftNode == null) {
//						rightNode.leftNode = new IpNode();
//					}
//					rightNode = rightNode.leftNode;
//				}
//				if (i == 31)
//					rightNode.addressCode = addressCode;
//			}
//
//			ip_s = ip_s << 1;
//			ip_e = ip_e << 1;
//		}
//	}
//
//	public String findIp(String ip) {
//
//		IpNode curNode = rootNode;
//
//		int ip_int = ipToInt(ip);
//
//		if (ip_int == -1)
//			return NO_ADDRESS;
//
//		for (int i = 0; i < 32; i++) {
//
//			int ip_s_bit = (0x80000000 & ip_int) >>> 31;
//
//			if (ip_s_bit == 0)
//				curNode = curNode.leftNode;
//			else
//				curNode = curNode.rightNode;
//
//			if (curNode == null) {
//				return NO_ADDRESS;
//			}
//
//			if (curNode.addressCode != null && !curNode.addressCode.trim().equals(""))
//				return curNode.addressCode;
//
//			ip_int = ip_int << 1;
//		}
//
//		return NO_ADDRESS;
//	}
//
//	private static int ipToInt(String strIP) {
//		try {
//
//			int[] ip = new int[4];
//
//			int position1 = strIP.indexOf(".");
//			int position2 = strIP.indexOf(".", position1 + 1);
//			int position3 = strIP.indexOf(".", position2 + 1);
//
//			ip[0] = Integer.parseInt(strIP.substring(0, position1));
//			ip[1] = Integer.parseInt(strIP.substring(position1 + 1, position2));
//			ip[2] = Integer.parseInt(strIP.substring(position2 + 1, position3));
//			ip[3] = Integer.parseInt(strIP.substring(position3 + 1));
//			int ip_int = (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
//
//			return ip_int;
//
//		} catch (Exception e) {
//			return -1;
//		}
//	}
//
//	private class IpNode {
//		private IpNode leftNode;
//
//		private IpNode rightNode;
//
//		private String addressCode;
//
//	}
//
//	private void loopTree(IpNode ipNode, int depth) {
//		System.out.println(depth + "\t" + ipNode.addressCode);
//		if (ipNode.leftNode != null) {
//			System.out.println("left");
//			loopTree(ipNode.leftNode, depth + 1);
//		}
//		if (ipNode.rightNode != null) {
//			System.out.println("right");
//			loopTree(ipNode.rightNode, depth + 1);
//		}
//	}
//
//	/**
//	 * 
//	 * {main方法入口}
//	 * 
//	 * @param args
//	 * @author: Lee
//	 * @data: 2017年7月28日
//	 */
//	public static void main(String[] args) {
//		// String ip = "666.666.666.666";
//		String ip = "5.0.0.0";
//		long startTime = System.currentTimeMillis(); // 获取开始时间
//		String country = getIpAndCourty(ip);
//		long endTime = System.currentTimeMillis(); // 获取结束时间
//		System.out.println((endTime - startTime) + "ms");
//		System.out.println(country);
//	}
//
}
