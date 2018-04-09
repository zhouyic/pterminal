package communicate.pkmgmt.service;

import org.apache.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

public class Server {
	public static final int SERVER_PORT = 10200;
	private static final Logger log = Logger.getLogger(Server.class);
	public void startServer() {
		try {
			System.out.println("getprocess Server start ....");
 
			TProcessor tprocessor = new GetProcessService.Processor(new GetProcessImpl());
			 TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
			 TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(
			 serverTransport);
			 ttpsArgs.processor(tprocessor);
			 ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
 
			// 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
			 TServer server = new TThreadPoolServer(ttpsArgs);
			 server.serve();
 
		} catch (Exception e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.startServer();
	}
}
