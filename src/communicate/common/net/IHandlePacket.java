package communicate.common.net;


public interface IHandlePacket {

	//public String handlePacket(SocketChannel sc,byte[]buf,int length);
	
	public byte[] handlePacket(byte[] recBuf, int len, String remoteIP, int remotePort);

	public int handlePacketUdp(byte[] recBuf, int len, String remoteIP, int remotePort, byte[] send);
}
