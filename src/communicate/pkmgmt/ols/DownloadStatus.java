package communicate.pkmgmt.ols;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import communicate.common.IDGenerator;
import communicate.common.exception.SqlException;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.ITmlService;
import communicate.pkmgmt.OrderMapMemory;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.TmlServiceImpl;
import communicate.pkmgmt.dto.TmlDoInfo;
import communicate.pkmgmt.tmljdbc.ITmlJDBC;
import communicate.pkmgmt.tmljdbc.TmlConst;
import communicate.pkmgmt.tmljdbc.TmlJDBCImpl;

public class DownloadStatus {
	private static Logger log = communicate.common.Logger.getLogger(DownloadStatus.class);
	private ITmlService service  = new TmlServiceImpl();

	public byte[] parseData(byte[] recBuf, int len, int offset) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(recBuf, offset, len-offset);
		} catch (ArrayIndexOutOfBoundsException e) {
			log.error(this, e);
			return null;
		}

		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		// 节目下载状态
		byte[][] downstatus = tlvs.getAllBinaryValues(TLVTag.DOWNLOAD_STATUS);
		byte[][] downing = tlvs.getAllBinaryValues(TLVTag.DOWN_MSG);
		byte[][] playing = tlvs.getAllBinaryValues(TLVTag.PLAY_MSG);

		if(downstatus != null){
			int downStatusLen = downstatus.length;
			for(int i=0; i<downStatusLen;){
				tlvs.loadTLVs(downstatus[i], 0, downstatus[i].length);
				String orderId = tlvs.getStringValue(TLVTag.OrderId);
				int status = tlvs.getIntValue(TLVTag.Result_Code);

				OrderMapMemory map = OrderMapMemory.getInstance();
				if (status == TmlConst.orderStatus.stb_download_success) {
					map.updOrderStatus(orderId, TmlConst.orderStatus.download_success);
				}

				if (status == TmlConst.orderStatus.stb_download_fail) {
					map.updOrderStatus(orderId, TmlConst.orderStatus.download_fail);
				}

				if (status == TmlConst.orderStatus.stb_download_fail_del) {
					map.updOrderStatus(orderId, TmlConst.orderStatus.download_fail_del);
				}

				i++;
			}
		}

		List<TmlDoInfo> dolist = new ArrayList<TmlDoInfo>();
		Date date = new Date();
		IDGenerator gen = new IDGenerator();
		// 播放信息
		if(playing != null){
			int playingLen = playing.length;
			for(int z=0; z<playingLen;){
				TmlDoInfo info = new TmlDoInfo();
				tlvs.loadTLVs(playing[z], 0, playing[z].length);
				String playContent = tlvs.getStringValue(TLVTag.Content_Name,"UTF-8");
				int cntType = tlvs.getIntValue(TLVTag.Service_Type);
				playContent = service.getContentName(playContent);
				log.info(tmlId+","+playContent+","+cntType);
				info.setTmlId(tmlId);
				info.setTmlStatus(TmlConst.tmlStatus.play);
				info.setTmlDoing(playContent);
				info.setServiceType(cntType);
				info.setInTime(PackageConstant.sdf.format(date));
				info.setId(info.hashCode()+Integer.valueOf(gen.generate(2)));
				dolist.add(info);
				z++;
			}
		}

		// 节目下载信息
		if(downing != null){
			int downingLen = downing.length;
			for(int j=0; j<downingLen;){
				TmlDoInfo info = new TmlDoInfo();
				tlvs.loadTLVs(downing[j], 0, downing[j].length);
				String downContent = tlvs.getStringValue(TLVTag.Content_Name,"UTF-8");
				String downrate = tlvs.getStringValue(TLVTag.tmlDownRate);
				String downsource = tlvs.getStringValue(TLVTag.tmlDownSource);
				downContent = service.getContentName(downContent);
				log.info(tmlId+","+downContent+","+downrate+","+downsource);
				info.setTmlId(tmlId);
				info.setTmlStatus(TmlConst.tmlStatus.download);
				info.setTmlDoing(downContent);
				info.setDownSource(downsource);
				info.setDownRate(downrate);
				info.setInTime(PackageConstant.sdf.format(date));
				info.setId(info.hashCode()+Integer.valueOf(gen.generate(2)));
				dolist.add(info);
				j++;
			}
		}

		if (dolist != null && dolist.size() != 0) {
			ITmlJDBC db = new TmlJDBCImpl();
			try {
				db.updateTmlDoCnt(tmlId, dolist);
			} catch (SqlException e) {
				log.error(this,e);
			}
		}

		return null;
	}

	public static void main(String[] a) {
		DownloadStatus d = new DownloadStatus();
		/*byte[] as = {0x4D,0x30,0x36,0x31,0x35,0x35,0x32,0x35,0x31,0x36,0x35,0x31,0x36,0x30,0x30,0x33,0x2E,0x6D,0x70,0x33};
		try {
			String str = new String(as, "UTF-8");
			System.out.print(str);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		byte[] s = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x0F,0x39,0x0E,0x00,(byte)0x8B,0x00
				,0x06,0x00,0x10,0x30,0x30,0x31,0x32,0x34,0x30,0x37,0x31,0x32,0x38,0x31,0x33,0x00,0x04,0x00,0x05,0x02,0x07,(byte)0xDA,0x00,0x40,0x00
				,0x17,0x00,0x17,0x45,0x5F,0x48,0x31,0x37,0x33,0x31,0x31,0x30,0x31,0x32,0x36,0x30,0x30,0x30,0x33,0x2E,0x74,0x73,0x00,(byte)0xD2,0x00,0x0E
				,0x31,0x32,0x31,0x2E,0x34,0x38,0x4B,0x42,0x2F,0x73,0x00,(byte)0xD3,0x00,0x17,0x32,0x32,0x33,0x2E,0x32,0x30,0x32,0x2E,0x31,0x37,0x2E,0x31,0x37
				,0x34,0x3A,0x37,0x30,0x30,0x30,0x07,(byte)0xDB,0x00,0x20,0x00,0x17,0x00,0x17,0x45,0x5F,0x48,0x30,0x31,0x32,0x30,0x39,0x31,0x32,0x31,0x36
				,0x30,0x30,0x30,0x35,0x2E,0x74,0x73,0x00,0x15,0x00,0x05,0x0C};
		d.parseData(s, s.length, 22);
	}
}