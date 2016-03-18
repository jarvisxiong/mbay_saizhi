package org.sz.mbay.traffic.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtil {
	static final Logger LOGGER = LoggerFactory.getLogger(AESUtil.class);

	/**
	 * 将二进制转换为16进制
	 */
	public static String encodeBytes(byte[] bytes) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
			strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
		}
		return strBuf.toString();
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密字符串
	 * @param password
	 *            密钥
	 * @param value
	 *            向量
	 * @return
	 */
	public static String encrypt(String content, String password, String value) {
		try {
			byte[] raw = password.getBytes();
			SecretKeySpec key = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
			IvParameterSpec iv = new IvParameterSpec(value.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);// 初始化
			byte[] result = cipher.doFinal(content.getBytes());

			return encodeBytes(result); // 加密

		} catch (Exception e) {
			LOGGER.error("AESUtil加密异常", e.fillInStackTrace());
		}
		return "";
	}
}