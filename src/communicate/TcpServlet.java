package communicate;

import org.apache.log4j.Logger;

import communicate.common.GetProperty;
import communicate.common.net.TmlNetThreadMgmt;
import communicate.pkmgmt.service.Server;
import communicate.timer.TimerServlet;

public class TcpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = communicate.common.Logger.getLogger(TcpServlet.class);

	public void init(){
//		OrderMapMemory o = OrderMapMemory.getInstance();
//		o.init();
//		log.info("order map start!");
//
		TcpStart();
		log.info("tcp server pool start!");

		
		TimerServlet t = new TimerServlet();
		t.init();
		log.info("init TimerServlet start!");
		
		
		Server server = new Server();
		server.startServer();
		log.info("getprocess server start");
//		
//		Subagentx nms = new Subagentx();
//		nms.start();
//		
		// 针对多屏互动
//		SessionMap s = SessionMap.getInstance();
//		s.init();
//		log.info("SessionMap start");
//		
//		TcpserverServlet tcpserver = new TcpserverServlet();
//		tcpserver.init();
//		log.info("tcp keepalive start");
//		
//		Server server = new Server();
//		server.start();
//		log.info("iphone server start");
	}

	public void TcpStart() {
		log.info("read sys properties...");
		GetProperty properties = new GetProperty();
		int udpMaxthread = 10;
		int tcpMaxthread = 1000;
		//int tcpSSLMaxthread = 2;
		String udpMaxThreadString = properties.udpmaxthread;
		String tcpMaxThreadString = properties.tcpmaxthread;
		//String tcpSSLMaxthreadStr = (String) properties.getProperty("tcpSSLMaxthread");
		//String udpMaxThreadString = String.valueOf(udpMaxthread);
		//String tcpMaxThreadString = String.valueOf(tcpMaxthread);
  
		try {
			udpMaxthread = Integer.parseInt(udpMaxThreadString);
			tcpMaxthread = Integer.parseInt(tcpMaxThreadString);
			//stcpSSLMaxthread = Integer.parseInt(tcpSSLMaxthreadStr);

		} catch (NumberFormatException nfex) {
			log.warn("Init thread fail!" + nfex.getMessage());
		}

		//TmlNetThreadMgmt t = new TmlNetThreadMgmt(udpMaxthread,tcpMaxthread,tcpSSLMaxthread);
		TmlNetThreadMgmt t = new TmlNetThreadMgmt(udpMaxthread,tcpMaxthread);
		t.start();
	}

	public static void main(String[] args){
		TcpServlet t = new TcpServlet();
		//	t.start();
		t.init();
	}

}
