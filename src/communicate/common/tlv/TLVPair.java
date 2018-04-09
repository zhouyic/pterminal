package communicate.common.tlv;


/**
 * @version TLVPair == AVP == AttributeValuePair
 * @author user
 * 
 */

public class TLVPair {

	private int tag; // tlv tag

	private int length; // tlv length

	private byte[] value; // tlv value

	public TLVPair() {

	}

	public TLVPair(int tag, byte[] value) {

		if (value == null) {
			return;
		}

		this.tag = tag;

		this.value = value;

		this.length = value.length + 4;
	}

	public byte[] getTLV() {
		byte[] tlvbytes = new byte[length];
		tlvbytes[0] = (byte) ((tag >> 8) & 0xff);
		tlvbytes[1] = (byte) (tag & 0xff);
		tlvbytes[2] = (byte) ((length >> 8) & 0xff);
		tlvbytes[3] = (byte) (length & 0xff);
		System.arraycopy(value, 0, tlvbytes, 4, length - 4); 
		return tlvbytes;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("TLVPair[").append(tag);
		sb.append(",").append(length);
		sb.append(",").append(new String(value));
		sb.append("]").append("\r\n");

		return sb.toString();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

}
