package communicate.common.net;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import communicate.pkmgmt.dto.TmlDto;

public class QueueProcessThread extends Thread {
	private static final Logger log = Logger.getLogger(QueueProcessThread.class);
	public static final int QUEUE_SIZE = 10000;
	private static final int TIME_OUT = 60000;
	private static final int BACKLOG = 5;
	private static Queue<Socket> queue = new ArrayBlockingQueue<Socket>(QUEUE_SIZE);

	private int maxWorkThread;
	private MiniWorkThread[] miniWorkThread;  //接收数据，处理数据，发送数据
	
	public static int getQueueSize() {
		return queue.size();
	}
	public static void add(Socket s) {
		queue.add(s);
	}
	public QueueProcessThread(int port, int maxWorkThread) {
		this.maxWorkThread = maxWorkThread;
		miniWorkThread = new MiniWorkThread[maxWorkThread];
		for (int i = 0; i < maxWorkThread; i++) {
			miniWorkThread[i] = new MiniWorkThread(i, port);
		}
	}

	@Override
	public void run() {
		log.info("-------------running-------------");
		for (int i = 0; i < maxWorkThread; i++) {
			miniWorkThread[i].start();
		}
		int count = 0;
		while (true) {
			while(queue.size()>0) {
				Socket s = queue.poll();
				try{
					if(s == null || s.isClosed() || !s.isConnected()) {
						log.error("this socket is closed : " + s);
						if(s != null)
							s.close();
						continue;
					}
					boolean isGetWorker = false;
					int times = 2 * maxWorkThread;
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
					}
		
					if (!isGetWorker) {
						log.info("all work is busy, socket back to queue, wait and sleep 2 seconds");
						queue.add(s);
						sleep(2000);
					} else {
						log.debug("packet arrive   select miniThread id=" + count
								% maxWorkThread);
						s.setSoLinger(true, 5);
						s.setSoTimeout(TIME_OUT);
						tmp.setRemoteSocketAddress(s.getRemoteSocketAddress());
						tmp.setTcpSocket(s);
						tmp.setbusy();
						tmp.handlePacket();
					}
				}catch(Exception e){
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
		}
		
	}
	public void shutdown() {
		for (int i = 0; i < miniWorkThread.length; i++) {
			miniWorkThread[i].shutdown();
			try {
				miniWorkThread[i].join();
			} catch (Exception e) {
				log.info("Exception " + e.getMessage());
			}
		}
	}

}
