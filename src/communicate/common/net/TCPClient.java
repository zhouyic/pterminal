package communicate.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.log4j.Logger;

public class TCPClient {

	private static final Logger log = Logger.getLogger(TCPClient.class);

	private int serverPort;

	private String serverAddr;

	private int timeout = 10000;



	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public TCPClient(String serverAddr, int serverPort){//��
		this.serverAddr = serverAddr;
		this.serverPort = serverPort;
	}


	public TCPClient(int serverPort, String serverAddr, int timeout) {
		super();
		this.serverPort = serverPort;
		this.serverAddr = serverAddr;
		this.timeout = timeout;
	}

	public void sendAsync(byte[] sendBuff, int len)throws IOException {
		Socket s = null;
		InputStream input = null;
		try {
			s = new Socket(this.serverAddr, this.serverPort);
			s.getOutputStream().write(sendBuff, 0, len);
			s.getOutputStream().flush();
			log.debug("TCPClientBean.sendAsync send to" + this.serverAddr 
					+ ":" + this.serverPort + "  date len = " + len);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			try {
				if (input != null)
					input.close();
				if (s != null)
					s.close();
			} catch (IOException ex1) {
				ex1.printStackTrace();
			}
		}
	}

	public int sendSynch(byte[] recBuff, byte[] sendBuff, int len) throws IOException{//��&&��
		Socket s = null;
		int recLen = 0;
		InputStream input = null;
		try {
			s = new Socket(this.serverAddr, this.serverPort);
			s.getOutputStream().write(sendBuff, 0, len);
			s.getOutputStream().flush();
			log.debug("TCPClientBean.sendSynch send to " + this.serverAddr 
					+ ":" + this.serverPort + "  date len = " + len);
			s.setSoTimeout(timeout);
			input = s.getInputStream();
			recLen = input.read(recBuff);
			log.debug("TCPClientBean.sendSynch recv from " + this.serverAddr 
					+ ":" + this.serverPort + "  date len = " + recLen);
		} catch (IOException ex) {
			log.warn(ex,ex);
			throw ex;
		} finally {
			try {
				if (input != null)
					input.close();
				if (s != null)
					s.close();
			} catch (IOException ex1) {
				ex1.printStackTrace();
			}
		}
		return recLen;
	}
	//接收字节长度>1024的时候
	public byte[] sendSynch(byte[] sendBuff,int len)throws IOException {
		Socket s = null;
		int recLen = 0;
		InputStream input = null;
		byte[] rcvBytes = new byte[4096];
		ByteArrayBuffer rcvBuffer = new ByteArrayBuffer(rcvBytes.length);

		try {
			s = new Socket(this.serverAddr, this.serverPort);
			s.getOutputStream().write(sendBuff, 0, len);
			s.getOutputStream().flush();
			log.info("TCPClientBean.sendSynch send to " + this.serverAddr 
					+ ":" + this.serverPort + "  date len = " + len);
			s.setSoTimeout(timeout);
			input = s.getInputStream();

			recLen = input.read(rcvBytes);
			log.info("===============recv length:"+recLen);
			if(recLen!=-1){
				rcvBuffer.append(rcvBytes, 0, recLen);
				while(recLen == rcvBytes.length){
					recLen = input.read(rcvBytes);
					if(recLen>0){
						rcvBuffer.append(rcvBytes, 0, recLen);
					}
					log.info("===============recv length:"+recLen);
				}
				log.info("TCPClient.sendSynch recv from " + this.serverAddr 
						+ ":" + this.serverPort + "  date len = " + recLen);
			}
			else{
				log.error("TCPClient.sendSynch SOCKET READ ERROR from " + this.serverAddr 
						+ ":" + this.serverPort);
			}
		} catch (IOException ex) {
			log.warn(ex,ex);
			throw ex;
		} finally {
			try {
				if (input != null)
					input.close();
				if (s != null)
					s.close();
			} catch (IOException ex1) {
				ex1.printStackTrace();
			}
		}
		return rcvBuffer.toByteArray();
	}
}
