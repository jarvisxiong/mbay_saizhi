package mbay_paymb;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.HttpSupport;

public class MBPayTest {
	public static void main(String[] args) {
		String url = "http://192.168.21.165:8088/mbay_paymb/trade/v1/pay";
		String orderNumber = "201484932749";
		String mb_uid = "18552015381";
		String user_number = "D2B620F4";
		String key = "0a9176a666236f5cf5b5362a852ae63d";
		String _input_charset = "UTF-8";
		String sign_type = "MD5";
		int total_fee = 2;
		String desc =Base64.encodeBase64String("金币兑换".getBytes());
		String[] parameters = { "user_number=D2B620F4", "_input_charset=UTF-8", "total_fee=1", "mb_uid=18552015381",
				"desc="+desc,"out_trade_no=201484932749"};
		Arrays.sort(parameters);
		StringBuilder sb = new StringBuilder();
		for (String str : parameters) {
			sb.append("&" + str);
		}
		String parameter = sb.toString().substring(1);
		String standBySign = parameter + key;
		System.out.println(standBySign);
		String sign = DigestUtils.md5Encrypt(standBySign);
		url = url + "?" + parameter + "&sign_type=MD5&sign=" + sign;
		System.out.println(url);
		System.out.println("_input_charset=UTF-8&desc=金币兑换&mb_uid=18552015381&orderNumber=201484932749&sign=7b3e6ae9a6255a4c15a36ef2d87951f9&sign_type=MD5&total_fee=2&user_number=D2B620F40a9176a666236f5cf5b5362a852ae63d");
		try {
			HttpSupport.connect(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
