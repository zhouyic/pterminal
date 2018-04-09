package communicate.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import communicate.pkmgmt.DefaultHandlePacket;

public class MiniWorkThread extends Thread {
	private static final Logger log = Logger.getLogger(MiniWorkThread.class);

	private IHandlePacket handlePacket;

	private DatagramSocket udpSocket;

	private Socket tcpSocket;

	private byte[] recBuf = new byte[4096];

	private SocketAddress remoteSA;

	private int recSize;

	private int sendSize;

	private int localPort;

	private byte[] sendBufUDP = new byte[1024];

	private boolean shutdown = true;

	private Object obj = new Object();

	private List<Object> list = new ArrayList<Object>();

	private boolean busy = false;

	private boolean isUDP = true;

	public MiniWorkThread(int id, int localPort, DatagramSocket ds) {
		setName("MiniWorkThread " + localPort + "  " + id);
		this.udpSocket = ds;
		this.localPort = localPort;
		handlePacket = new DefaultHandlePacket();
		shutdown = false;
		isUDP = true;
	}

	public MiniWorkThread(int id, int localPort) {
		setName("MiniWorkThread " + localPort + "  " + id);
		this.localPort = localPort;
		shutdown = false;
		isUDP = false;
		handlePacket = new DefaultHandlePacket();
	}

	public IHandlePacket getHandlePacket() {
		return handlePacket;
	}

	public void setHandlePacket(IHandlePacket handlePacket) {
		this.handlePacket = handlePacket;
	}

	public void setTcpSocket(Socket tcpSocket) {
		this.tcpSocket = tcpSocket;
	}

	public void setRecSize(int recSize) {
		this.recSize = recSize;
	}

	public boolean isbusy() {
		return busy;
	}

	public void setbusy() {
		busy = true;
	}

	public void setRemoteSocketAddress(SocketAddress remoteSA) {
		this.remoteSA = remoteSA;
	}

	public byte[] getBuf() {
		return recBuf;
	}

	public void handlePacket() {
		synchronized (list) {
			list.add(obj);
			list.notify();
		}
	}

	@Override
	public void run() {
		log.info(" miniWorkThred start up ...........");
		while (true) {
			if (shutdown) {
				break;
			}
			try {
				busy = false;
				synchronized (list) {
					while (list.size() == 0) {
						list.wait(30000);
//						log.info(" miniWorkThred wake up in 30000 111 list.size()=..........." +list.size()
//								+"  busy=" +busy );
						
					}
					list.clear();
				}
//				log.info(" miniWorkThred wake up in 30000 222...........");
				busy = true;
				if (shutdown) {
					break;
				}

				if (remoteSA == null) {
					continue;
				}

				String remoteIP = null;
				if (remoteSA instanceof InetSocketAddress) {
					InetSocketAddress addr = (InetSocketAddress) remoteSA;
					remoteIP = addr.getAddress().getHostAddress();
				}

				if (isUDP) {
					sendSize = handlePacket.handlePacketUdp(recBuf, recSize,
							remoteIP, localPort, sendBufUDP);
					if (sendSize > 0) {
						sendBack(sendBufUDP, sendSize);
					}
				} else {
					InputStream input = tcpSocket.getInputStream();
					recSize = input.read(recBuf);

					log.debug("################ recSize"
							+ "="+recSize+", recBuf.len="+recBuf.length);
					if (recSize > 0) {
						byte[] send = null;
						send = handlePacket.handlePacket(recBuf,recSize,remoteIP, localPort);
						if (send != null) {
							sendSize = send.length;
							if (sendSize > 0) {
								sendBack(send, sendSize);
							}
						}
					}
				}
				//				tcpSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("Exception " + e);
			} finally {
				try {
					//if (tcpSocket != null && tcpSocket.isConnected()) {
					if (tcpSocket != null) {
						tcpSocket.close();
					}
					//}
				} catch (IOException e) {
					log.error(this, e);
				}
				remoteSA = null;
				tcpSocket = null;
				list.clear();
			}
		}
		busy = true;
		log.info("shut down...........");
	}

	public void sendBack(byte[] send, int len) throws Exception {
		if (isUDP) {
			udpSocket.send(new DatagramPacket(sendBufUDP, 0, sendSize, remoteSA));
		} else {
			log.info("sendback,len:"+len);
			tcpSocket.getOutputStream().write(send, 0, len);
			tcpSocket.getOutputStream().flush();
		}
	}

	public void shutdown() {
		shutdown = true;
		handlePacket();
	}

}