package org.androidpn.server.util;

import java.util.zip.CRC32;

/**
 * crc32校验
 * 
 * @author jerry
 */
public class CRC32Util {
	
	public static String getResult(String src) {
		CRC32 crc32 = new CRC32();
		crc32.update(src.getBytes());
		long crcvalue = crc32.getValue();
		String crcStr = Long.toHexString(crcvalue).toLowerCase();
		while (crcStr.length() < 8) {
			crcStr = "0" + crcStr;
		}
		return crcStr;
	}
}
