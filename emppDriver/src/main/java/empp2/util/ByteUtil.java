package empp2.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class ByteUtil {
	
	public static String byte2hex(byte[] src) {
		return new String(Hex.encodeHex(src));
	}

	public static byte[] hex2byte(String src) {
		try {
			return Hex.decodeHex(src.toCharArray());
		} catch (DecoderException e) {
		}
		return null;
	}
	
}