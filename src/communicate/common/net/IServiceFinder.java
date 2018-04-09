package communicate.common.net;

public interface IServiceFinder {

	public IService findService(byte[] recBuf, int port);

}