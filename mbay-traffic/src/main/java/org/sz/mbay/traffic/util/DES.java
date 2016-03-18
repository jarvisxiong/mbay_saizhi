package org.sz.mbay.traffic.util;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 创建:汤亚男
 *
 * 时间:2013-07-31
 */
public class DES {

    public static String encode(String data, String DESkey){
        try {
            Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec keySpec = new DESKeySpec(DESkey.getBytes());
            AlgorithmParameterSpec iv = new IvParameterSpec(DESkey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key key = keyFactory.generateSecret(keySpec);
            enCipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return base64Encoder.encode(pasByte);
        } catch (Throwable e) {
            return null;
        }
    }

    public static String decode(String data, String DESkey){
        try {
            Cipher deCipher = Cipher.getInstance("DES/CBC/Nopadding");
            DESKeySpec keySpec = new DESKeySpec(DESkey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key key = keyFactory.generateSecret(keySpec);
            deCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(DESkey.getBytes()));
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));
            return new String(pasByte, "UTF-8");
        } catch (Throwable e) {
            return null;
        }
    }

}
