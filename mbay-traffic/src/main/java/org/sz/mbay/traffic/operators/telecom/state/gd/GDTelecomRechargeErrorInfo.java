package org.sz.mbay.traffic.operators.telecom.state.gd;

import java.util.HashMap;
import java.util.Map;

public class GDTelecomRechargeErrorInfo {

	private static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put("00000", "成功");
		map.put("10001", "非法手机号码");
		map.put("10002", "非法批次");
		map.put("10003", "非法参数(参数只能是特殊字符或为空值)");
		map.put("10004", "非法订购日期");
		map.put("10005", "签名验证失败");
		map.put("10006", "非法合作方");
		map.put("10007", "非法销售品");
		map.put("10008", "非法请求流水号");
		map.put("10009", "非法渠道");
		map.put("10010", "号码欠费");
		map.put("10015", "客户未订购该套餐");
		map.put("10016", "客户已订购该套餐");
		map.put("10026", "生成订单号失败");
		map.put("10027", "超出可订购流量包的数量");
		map.put("10028", "活动尚未开始");
		map.put("10029", "活动任务已结束");
		map.put("10030", "非法合同编号");
		map.put("10033", "用户有在途工单，无法受理");
		map.put("10040", "无权限调用该服务");
		map.put("10043", "无权限访问该接口");
		map.put("10054", "在同一销售品组A 下，能且仅能受理一款销售品");
		map.put("10055", "产品未配置");
		map.put("10056", "营销资源未配置");
		map.put("10057", "号码归属地信息不正确");
		map.put("10108", "用户为预开通卡用户，请先办理资料返档");
		map.put("10109", "不存在对应的业务开展省份");
		map.put("90001", "3G副卡不支持，建议用户升级4G");
		map.put("90002", "CRM 内部错误");
		map.put("90003", "异常报竣");
		map.put("99999", "其他错误");
	}

	public static String getErrorMsg(String errorCode) {
		return map.get(errorCode);
	}

}
