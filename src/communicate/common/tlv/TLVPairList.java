package communicate.common.tlv;

import java.io.UnsupportedEncodingException;

public class TLVPairList {

	public static final int INVALID_INT = 0;

	private static final int GROWBY = 10;

	private TLVPair[] list = new TLVPair[GROWBY];

	private int active;

	public byte[] createBlock(){

		int total_len = 0;
		for (int i = 0; i < active; i++) {
			total_len += list[i].getLength();
		}

		byte abyte0[] = new byte[total_len];
		int dstpos = 0;
		for (int j = 0; j < active; j++) {
			byte[] abyte1 = list[j].getTLV();
			int len = list[j].getLength();
			System.arraycopy(abyte1, 0, abyte0, dstpos, len);
			dstpos += len;
		}
		return abyte0;
	}

	public void loadTLVs(byte[] abyte, int startpos, int length)
	throws ArrayIndexOutOfBoundsException {
		if (length == 0 || abyte == null || abyte.length - startpos - length < 0) {

			throw new ArrayIndexOutOfBoundsException(
					" loadTLVs params error! abyte[]==" + abyte
					+ "    startpos == " + startpos + "    length=="
					+ length);
		}

		int endpos = startpos + length;

		if (active > 0) {
			init();
		}

		for (int offset = startpos; offset < endpos;) {

			int tag = (abyte[offset++] & 0xff) << 8 | abyte[offset++] & 0xff;
			int len = ((abyte[offset++] & 0xff) << 8 | abyte[offset++] & 0xff);
			if (len < 4) {
				throw new ArrayIndexOutOfBoundsException(
						"Attribute with a bad length (min = 4): " + len
						+ "   tag = " + tag);
			}

			byte[] abyte1 = new byte[len-4];
			System.arraycopy(abyte, offset, abyte1, 0, len-4);
			offset += (len-4);
			addTLV(tag, abyte1);
		}
	}

	public int loadTLV(byte[] abyte, int startpos, int length)	throws ArrayIndexOutOfBoundsException {

		if (length == 0 || abyte == null || abyte.length - startpos - length < 0) {

			throw new ArrayIndexOutOfBoundsException(
					" loadTLVs params error! abyte[]==" + abyte
					+ "    startpos == " + startpos + "    length=="
					+ length);
		}
		int ret = 0;
		int endpos = startpos + length;

		if (active > 0) {
			init();
		}

		for (int offset = startpos; offset < endpos;) {

			int tag = (abyte[offset++] & 0xff) << 8 | abyte[offset++] & 0xff;
			int len = ((abyte[offset++] & 0xff) << 8 | abyte[offset++] & 0xff);
			if (len < 4) {
				throw new ArrayIndexOutOfBoundsException(
						"Attribute with a bad length (min = 4): " + len
						+ "   type = " + tag);
			}

			byte[] abyte1 = new byte[len-4];
			System.arraycopy(abyte, offset, abyte1, 0, len-4);
			offset += (len-4);
			addTLV(tag, abyte1);
			ret++;
		}

		return ret;
	}

	public byte[][] getAllBinaryValues(int tag) {
		int len = active;
		int cnt = 0;

		for (int j = 0; j < len; j++) {
			if (list[j].getTag() == tag) {
				cnt++;
			}
		}

		if (cnt == 0) {
			return null;
		}

		byte[][] abyte = new byte[cnt][];
		cnt = 0;
		for (int k = 0; k < len; k++) {
			if (list[k].getTag() == tag) {
				// XXX System.arraycopy()???
				abyte[cnt++] = list[k].getValue();
			}
		}
		return abyte;
	}

	public int[] getAllIntValues(int tag) {
		byte[][] tmp = this.getAllBinaryValues(tag);
		if (tmp == null) {
			return null;
		} else {
			int cnt = tmp.length;
			int[] aint = new int[cnt];
			for (int k = 0; k < cnt; k++) {
				aint[k] = this.binaryToInt(tmp[k], 0, tmp[k].length);
			}
			return aint;
		}
	}

	public String[] getAllStringValues(int tag, String characterType) {
		byte[][] tmp = this.getAllBinaryValues(tag);
		if (tmp == null) {
			return null;
		} else {
			int cnt = tmp.length;
			String[] aStr = new String[cnt];
			for (int k = 0; k < cnt; k++) {
				try {
					aStr[k] = new String(tmp[k], characterType);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					aStr[k] = new String(tmp[k]);
				}
			}
			return aStr;
		}
	}

	public String[] getAllStringValues(int tag) {
		byte[][] tmp = this.getAllBinaryValues(tag);
		if (tmp == null) {
			return null;
		} else {
			int cnt = tmp.length;
			String[] aStr = new String[cnt];
			for (int k = 0; k < cnt; k++) {
				aStr[k] = new String(tmp[k]);
			}
			return aStr;
		}
	}

	public byte[] getBinaryValue(int tag) {
		for (int j = 0; j < active; j++) {
			if (list[j].getTag() == tag) {
				return list[j].getValue();
			}
		}
		return null;
	}

	public int getIntValue(int tag) {
		byte[] tmp = this.getBinaryValue(tag);
		if (tmp == null) {
			return INVALID_INT;
		} else {
			return this.binaryToInt(tmp, 0, tmp.length);
		}
	}

	public int getIntValueWithExption(int tag) throws ArrayIndexOutOfBoundsException{
		byte[] tmp = this.getBinaryValue(tag);
		if (tmp == null) {
			throw new ArrayIndexOutOfBoundsException("No VALUE of this type in list.");
		} else {
			return this.binaryToInt(tmp, 0, tmp.length);
		}
	}

	public String getStringValue(int tag) {
		byte[] tmp = this.getBinaryValue(tag);
		if (tmp == null) {
			return null;
		} else {
			return new String(tmp);
		}
	}

	public String getStringValue(int tag, String characterType){

		byte[] tmp = this.getBinaryValue(tag);
		if (tmp == null) {
			return null;
		} else {
			String str = null;
			try {
				str = new String(tmp, characterType);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				str = new String(tmp);
			}
			return str;
		}
	}

	public void addTLV(int tag, String value, String characterType) {
		if(value==null){
			return;
		}

		byte[] abytes = null;
		try {
			abytes = value.getBytes(characterType);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			abytes = value.getBytes();
		}
		addTLV(new TLVPair(tag, abytes));
	}

	public void addTLV(int tag, String value) {
		if(value==null){
			return;
		}

		addTLV(new TLVPair(tag, value.getBytes()));
	}

	public void addTLV(int tag, int value) {
		byte[] abytes = new byte[4];
		abytes[0] = (byte)((value >> 24) & 0xff);
		abytes[1] = (byte)((value >> 16) & 0xff);
		abytes[2] = (byte)((value >> 8) & 0xff);
		abytes[3] = (byte)(value & 0xff);
		addTLV(new TLVPair(tag, abytes));
	}

	public void addTLV(int tag, byte value[]) {
		addTLV(new TLVPair(tag, value));
	}

	public void addTLV(TLVPair tlvPair) {
		if (tlvPair == null) {
			return;
		}

		if (active >= list.length) {
			TLVPair[] tlvs = new TLVPair[list.length + GROWBY];
			System.arraycopy(list, 0, tlvs, 0, active);
			list = tlvs;
		}

		list[active++] = tlvPair;
	}

	public void init() {
		list = new TLVPair[GROWBY];
		active = 0;
	}

	public int binaryToInt(byte[] abyte, int startpos, int len) {
		if (abyte == null || startpos + len > abyte.length) {
			return 0;
		}
		int tmp = 0;
		switch (len) {
		case 0:
			tmp = 0;
			break;
		case 1:
			tmp = abyte[startpos] & 0xff;
			break;
		case 2:
			tmp = (abyte[startpos++] & 0xff) << 8 | abyte[startpos] & 0xff;
			break;
		case 3:
			tmp = (abyte[startpos++] & 0xff) << 16
			| (abyte[startpos++] & 0xff) << 8 | abyte[startpos] & 0xff;
			break;
		default:
			tmp = (abyte[startpos++] & 0xff) << 24
			| (abyte[startpos++] & 0xff) << 16
			| (abyte[startpos++] & 0xff) << 8 | abyte[startpos] & 0xff;
		}
		return tmp;
	}



	public static void main(String[] s) {
		int i = 455;
		byte[] b = new byte[4];
		int offset = 0;
		// b[offset++] = (byte)((i >> 24) & 0xff);
		// b[offset++] = (byte)((i >> 16) & 0xff);
		b[offset++] = (byte) ((i >> 8) & 0xff);
		b[offset++] = (byte) (i & 0xff);

		TLVPairList p = new TLVPairList();
		int startpos = 0;
		int tmp = p.binaryToInt(b, startpos, 2);
		System.out.println(tmp);
		System.out.println(startpos);
	}

}
