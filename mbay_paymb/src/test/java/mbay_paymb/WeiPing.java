package mbay_paymb;

import org.sz.mbay.common.util.DigestUtils;

public class WeiPing {
	public static void main(String[] args) {
		String url = "http://h5.izhangxin.com?pn=com.izhangxin.hldwc.h5.meibei";
		String key = "0a9176a666236f5cf5b5362a852ae63d";
		String candidate="mb_uid=18552015381"+key;
		System.out.println("待签名串："+candidate);
		String sign = DigestUtils.md5Encrypt(candidate);
		url = url + "&mb_uid=18552015381" +"&sign=" + sign;
		System.out.println(url);
	}

}
