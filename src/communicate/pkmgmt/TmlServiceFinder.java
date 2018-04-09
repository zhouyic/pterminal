package communicate.pkmgmt;

import org.apache.log4j.Logger;

import communicate.common.net.IService;
import communicate.common.net.IServiceFinder;

public class TmlServiceFinder implements IServiceFinder {

	private STBGatherService stbGather = new STBGatherService();

	protected static final Logger log = Logger
	.getLogger(TmlServiceFinder.class);

	public TmlServiceFinder() {

	}

	public IService findService(byte[] recBuf, int port) {
		return stbGather;
	}

}
