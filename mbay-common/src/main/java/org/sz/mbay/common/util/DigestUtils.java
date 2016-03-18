package org.sz.mbay.common.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.zip.CRC32;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密工具类
 * 
 * @author frank.zong
 */
public final class DigestUtils {
	
	private DigestUtils() {
	}
	
	/**
	 * 通过crc32,根据字符串生成8位随机数
	 * 
	 * @return
	 */
	public static String crc32(String str) {
		CRC32 crc32 = new CRC32();
		crc32.update(str.getBytes());
		String prefix = Long.toHexString(Long.valueOf(crc32.getValue() + "")).toUpperCase();
		// 判断crc32生成的数是否为8位，如果不足8位，则去补全，补全方法通过MbayRandom工具类实现
		int length = 8 - prefix.length();
		if (length > 0) {
			String rest = MbayRandom.getRandom(length);
			String result = prefix + rest;
			return result;
		}
		return prefix;
	}
	
	/**
	 * PBE加密
	 * 
	 * @param str
	 * @return
	 */
	public static String pbeEncrypt(String str) {
		return PBEEncryption.encode(str);
	}
	
	/**
	 * PBE解密
	 * 
	 * @param str
	 * @return
	 */
	public static String pbeDecrypt(String str) {
		return PBEEncryption.decode(str);
	}
	
	/**
	 * md5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String md5Encrypt(String str) {
		return Md5Encrypt.md5(str);
	}
	
	/**
	 * des3加密
	 * 
	 * @param str
	 * @return
	 */
	public static String des3Decrypt(String str) {
		return DES3.decrypt(str);
	}
	
	/**
	 * des3解密
	 * 
	 * @param str
	 * @return
	 */
	public static String des3Encrypt(String str) {
		return DES3.encrypt(str);
	}
	
	/**
	 * PBE加密,主要用于URL参数加密
	 * @author frank.zong
	 */
	private static class PBEEncryption {
		
		// 随机盐
		private static final byte[] SALT = { (byte) 0x21, (byte) 0x21, (byte) 0xF0,
				(byte) 0x55, (byte) 0xC3, (byte) 0x9F, (byte) 0x5A, (byte) 0x75 };
		
		// 加密深度
		private final static int ITERATION_COUNT = 21;
		
		private PBEEncryption() {
		}
		
		public static String encode(String input) {
			if (input == null || input.length() == 0) {
				throw new IllegalArgumentException();
			}
			try {
				
				KeySpec keySpec = new PBEKeySpec(null, SALT, ITERATION_COUNT);
				AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);
				
				SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
						.generateSecret(keySpec);
				Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
				ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
				
				byte[] enc = ecipher.doFinal(input.getBytes());
				
				String res = new String(Base64.encodeBase64(enc));
				// escapes for url
				res = res.replace('+', '-').replace('/', '_').replace("%", "%25")
						.replace("\n", "%0A");
				
				return res;
				
			} catch (Exception e) {
			}
			
			return "";
			
		}
		
		public static String decode(String token) {
			if (token == null || token.length() == 0) {
				return null;
			}
			try {
				String input = token.replace("%0A", "\n").replace("%25", "%")
						.replace('_', '/').replace('-', '+');
				byte[] dec = Base64.decodeBase64(input.getBytes());
				KeySpec keySpec = new PBEKeySpec(null, SALT, ITERATION_COUNT);
				AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT,
						ITERATION_COUNT);
				SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
						.generateSecret(keySpec);
				Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
				dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
				byte[] decoded = dcipher.doFinal(dec);
				String result = new String(decoded);
				return result;
			} catch (Exception e) {
				
			}
			
			return null;
		}
	}
	
	/**
	 * md5加密
	 * @author frank.zong
	 */
	private static class Md5Encrypt {
		
		private static final char DIGITS[] = { '0', '1', '2', '3', '4', '5', '6',
				'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		
		private Md5Encrypt() {
		}
		
		private static String md5(String text) {
			MessageDigest msgDigest = null;
			try {
				msgDigest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				return null;
			}
			try {
				msgDigest.update(text.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				return null;
			}
			byte bytes[] = msgDigest.digest();
			String md5Str = new String(encodeHex(bytes));
			return md5Str;
		}
		
		public static char[] encodeHex(byte data[]) {
			int l = data.length;
			char out[] = new char[l << 1];
			int i = 0;
			int j = 0;
			for (; i < l; i++) {
				out[j++] = DIGITS[(0xf0 & data[i]) >>> 4];
				out[j++] = DIGITS[0xf & data[i]];
			}
			return out;
		}
	}
	
	/**
	 * 3des 加解密
	 * 
	 * @author jerry
	 */
	private static class DES3 {
		
		private static final Logger LOGGER = LoggerFactory.getLogger(DES3.class);
		
		// 3个密钥
		private static final String DESKEY_1 = "12345678";
		private static final String DESKEY_2 = "abcdefgh";
		private static final String DESKEY_3 = "873847jd";
		
		// 算法
		private static final String ALGORITHM = "DES";
		
		// 编码
		private static final String CHARSET = "UTF-8";
		
		/**
		 * des加密
		 * 
		 * @param data
		 * @param desKey
		 * @return
		 * @throws Exception
		 */
		private static byte[] DESEncode(byte[] data, String desKey) throws Exception {
			SecretKeySpec key = new SecretKeySpec(getKey(desKey), ALGORITHM);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedData = cipher.doFinal(data);
			return encryptedData;
		}
		
		/**
		 * des解密
		 * 
		 * @param data
		 * @param desKey
		 * @return
		 * @throws Exception
		 */
		private static byte[] DESDecode(byte[] data, String desKey) throws Exception {
			SecretKeySpec key = new SecretKeySpec(getKey(desKey), ALGORITHM);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte decryptedData[] = cipher.doFinal(data);
			return decryptedData;
		}
		
		/**
		 * 3des加密
		 * 
		 * @param data
		 * @return
		 */
		private static String encrypt(String data) {
			try {
				byte[] pas0 = data.getBytes(CHARSET);
				
				byte[] pas1 = DESEncode(pas0, DESKEY_1);
				byte[] pas2 = DESEncode(pas1, DESKEY_2);
				byte[] pas3 = DESEncode(pas2, DESKEY_3);
				
				return Base64.encodeBase64String(pas3);
			} catch (Exception e) {
				LOGGER.error("AES encrypt error:" + e.fillInStackTrace());
				return "";
			}
		}
		
		/**
		 * 3des解密
		 * 
		 * @param data
		 * @return
		 */
		private static String decrypt(String data) {
			try {
				byte[] pas0 = Base64.decodeBase64(data);
				
				byte[] pas1 = DESDecode(pas0, DESKEY_3);
				byte[] pas2 = DESDecode(pas1, DESKEY_2);
				byte[] pas3 = DESDecode(pas2, DESKEY_1);
				
				return new String(pas3, CHARSET);
			} catch (Exception e) {
				LOGGER.error("AES encrypt error:" + e.fillInStackTrace());
				return "";
			}
		}
		
		/**
		 * 自定义一个key
		 * 
		 * @param string
		 */
		private static byte[] getKey(String keyRule) {
			Key key = null;
			byte[] keyByte = keyRule.getBytes();
			byte[] byteTemp = new byte[8];
			for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
				byteTemp[i] = keyByte[i];
			}
			key = new SecretKeySpec(byteTemp, ALGORITHM);
			return key.getEncoded();
		}
	}
}
