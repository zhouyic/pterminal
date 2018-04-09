package communicate.common.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import org.apache.log4j.Logger;


public class TCPWorkThread extends Thread {
	private static final Logger log = Logger.getLogger(TCPWorkThread.class);

	private static final int TIME_OUT = 60000;
	private static final int BACKLOG = 50;
	private boolean shutdown = true;

	private ServerSocket tcpSocket = null;




	private static void iflog(String loginfo) {
		log.debug(loginfo);
	}

	private void info(String msg) {
		log.info(msg);
	}

	private void debug(String msg) {
		//System.out.println(this + " debug " + msg);
		iflog(msg);
	}

	public TCPWorkThread(int port) {
		
		setName("WorkThread " + port);
		try {
			tcpSocket = new ServerSocket(port, BACKLOG);
			tcpSocket.setSoTimeout(TIME_OUT);
			shutdown = false;
		} catch (IOException ioe) {
			log.info("WorkThread init Exception!");
		}
		

	}

	@Override
	public void run() {
		info("start up ...........");
		
		int count = 0;
		int timeoutCount = 0;
		while (true) {
			if (shutdown) {
				break;
			}
			try {
				if (timeoutCount > 10) {
					timeoutCount = 0;
					log.debug("begin GC.............." + new Date());
					try {
						System.gc();
						finalize();
					} catch (Throwable t) {
						log.info("Throwable " + t.getMessage());
					}
					log.debug("end GC.............." + new Date());
				}

				Socket s = tcpSocket.accept();
				
				if (s == null) {
					timeoutCount++;
					continue;
				}
				if(QueueProcessThread.getQueueSize() >= QueueProcessThread.QUEUE_SIZE) {
					log.error("too many requests, packet arrive  lose.......");
					s.close();
				} else {
					QueueProcessThread.add(s);
				}
/*
				boolean isGetWorker = false;
				int times = 20;
				MiniWorkThread tmp = null;
				for(int i = 0; i < times; i++){
					count++;
					tmp = null;
					if (count >= 2 * maxWorkThread) {
						count = count % maxWorkThread;
					}
					tmp = miniWorkThread[count % maxWorkThread];
					if(!tmp.isbusy()){
						isGetWorker = true;
						break;
					}
					//Thread.sleep(1000);
				}

				if (!isGetWorker) {
					log.info("packet arrive  lose.......");
					s.close();
				} else {
					debug("packet arrive   select miniThread id=" + count
							% maxWorkThread);
					s.setSoLinger(true, 5);
					s.setSoTimeout(TIME_OUT);
					tmp.setRemoteSocketAddress(s.getRemoteSocketAddress());
					tmp.setTcpSocket(s);
					tmp.setbusy();
					tmp.handlePacket();
				}
*/
				
			} catch (Exception e) {
				timeoutCount++;
				log.debug("Exception " + e.getMessage());

			}
		}
		try {
			tcpSocket.close();
		} catch (Exception e) {
			log.info("Exception " + e.getMessage());
		}
		info("shut down...........");
	}

	public void shutdown() {
		shutdown = true;
	}

}
