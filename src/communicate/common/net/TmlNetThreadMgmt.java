package communicate.common.net;

import org.apache.log4j.Logger;

import communicate.pkmgmt.PackageConstant;


//$JAVA_HOME/bin/java -Xms512m -Xmx800m  -Xgcthreads100

public class TmlNetThreadMgmt {

	private static final Logger log = Logger.getLogger(TmlNetThreadMgmt.class);

	private WorkThread[] udpWorkThreads = new WorkThread[1];

	private TCPWorkThread tcpWorkThread;
	private QueueProcessThread queueProcessThread;

	//private TcpsslThread tcpsslThread;

	public TmlNetThreadMgmt(int udpMaxWorkThread, int tcpMaxWorkThread) {
		if (udpMaxWorkThread <= 0) {
			udpMaxWorkThread = 1;
		}

		if(tcpMaxWorkThread <= 0){
			tcpMaxWorkThread = 1;
		}

		/*if (tcpSSLMaxthread <= 0) {
			tcpSSLMaxthread = 1;
		}
		 */
		//udpWorkThreads[0] = new WorkThread(PackageConstant.TML_PORT, udpMaxWorkThread);
		tcpWorkThread = new TCPWorkThread(PackageConstant.TML_PORT);
		queueProcessThread = new QueueProcessThread(PackageConstant.TML_PORT, tcpMaxWorkThread);

		// ------------------------
		/*KeysManage k = new KeysManage();
		SSLContext ctx = k.createKey();
		tcpsslThread = new TcpsslThread(ctx, PackageConstant.TML_STB_PORT, tcpSSLMaxthread);*/
	}

	public void start() {
		log.info("start up ...........");
		/*for (int i = 0; i < udpWorkThreads.length; i++) {
			udpWorkThreads[i].start();
		}*/

		tcpWorkThread.start();
		queueProcessThread.start();

		//tcpsslThread.start();
	}

	public void shutdown() {
		/*for (int i = 0; i < udpWorkThreads.length; i++) {
			if (udpWorkThreads[i] != null) {
				udpWorkThreads[i].shutdown();
				try {
					udpWorkThreads[i].join();
				} catch (Exception e) {
					log.warn(e,e);
				}
			}
		}*/

		if(tcpWorkThread != null){
			tcpWorkThread.shutdown();
			queueProcessThread.shutdown();
			try {
				tcpWorkThread.join();
				queueProcessThread.join();
			} catch (Exception e) {
				log.warn(e,e);
			}
		}

		/*if(tcpsslThread != null){
			tcpsslThread.interrupt();
			try {
				tcpsslThread.join();
			} catch (Exception e) {
				log.warn(e,e);
			}
		}*/
	}
}
