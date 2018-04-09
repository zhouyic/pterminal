package communicate.common.net;

public interface IService {
	public int respond(byte[] recBuf, int len, String remoteIP, byte[] sendBuf);
}