package communicate.common.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Date;

import org.apache.log4j.Logger;

public class WorkThread extends Thread {
	private static final Logger log = Logger.getLogger(WorkThread.class);

	private static void iflog(String loginfo) {
		log.debug(loginfo);
	}

	private static final int TIME_OUT = 60000;

	private int maxWorkThread;

	private boolean shutdown = true;

	private DatagramSocket udpSocket;

	private MiniWorkThread[] miniWorkThread;

	private byte[] nullBuf = new byte[1024];

	private void info(String msg) {
		log.info(this + "  info " + msg);
	}

	private void debug(String msg) {
		iflog(this + "  debug " + msg);
	}

	public WorkThread(int port, int maxWorkThread) {
		this.maxWorkThread = maxWorkThread;
		setName("WorkThread " + port);
		try {
			udpSocket = new DatagramSocket(port);
			udpSocket.setSoTimeout(TIME_OUT);
			udpSocket.setSendBufferSize(512 * 1024);
			udpSocket.setReceiveBufferSize(512 * 1024);
			shutdown = false;
		} catch (IOException ioe) {
			log.info("WorkThread init Exception!=======" + ioe.getMessage());
			ioe.printStackTrace();
		}
		miniWorkThread = new MiniWorkThread[maxWorkThread];
		for (int i = 0; i < maxWorkThread; i++) {
			miniWorkThread[i] = new MiniWorkThread(i, port, udpSocket);
		}

	}

	@Override
	public void run() {
		info("start up ...........");
		for (int i = 0; i < maxWorkThread; i++) {
			miniWorkThread[i].start();
		}
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

				count++;
				if (count >= 2 * maxWorkThread) {
					count = count % maxWorkThread;
				}
				MiniWorkThread tmp = null;
				for (int i = count; i < count + maxWorkThread; i++) {
					tmp = miniWorkThread[i % maxWorkThread];
					if (!tmp.isbusy()) {
						count = i;
						break;
					}
					tmp = null;
				}

				if (tmp == null) {
					DatagramPacket dp = new DatagramPacket(nullBuf, 1024);
					udpSocket.receive(dp);
					log.info("packet arrive  lose.......");
				} else {
					DatagramPacket dp = new DatagramPacket(tmp.getBuf(), 1024);
					udpSocket.receive(dp);
					SocketAddress sa = dp.getSocketAddress();
					if (sa != null) {
						tmp.setRecSize(dp.getLength());
						tmp.setRemoteSocketAddress(sa);
						tmp.setbusy();
						tmp.handlePacket();
						debug("packet arrive   select miniThread id=" + count
								% maxWorkThread);
					}
				}
			} catch (Exception e) {
				timeoutCount++;
				log.debug("Exception " + e.getMessage());

			}
		}
		try {
			udpSocket.close();
		} catch (Exception e) {
			log.info("Exception " + e.getMessage());
		}
		info("shut down...........");
	}

	public void shutdown() {
		for (int i = 0; i < miniWorkThread.length; i++) {
			miniWorkThread[i].shutdown();
			try {
				miniWorkThread[i].join();
			} catch (Exception e) {
			}
		}
		shutdown = true;
	}

}

class CacheBuf {
	private ByteBuffer buf = ByteBuffer.allocateDirect(1024);

	private SocketAddress sa = null;

	public ByteBuffer getBuf() {
		return buf;
	}

	public SocketAddress getSa() {
		return sa;
	}

	public void setSa(SocketAddress sa) {
		this.sa = sa;
	}

	public void clear() {
		buf.clear();
		sa = null;
	}
}
