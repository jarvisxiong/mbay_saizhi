package org.sz.mbay.traffic.operators.mobile.gd;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.traffic.operators.TrafficRecharge;
import org.sz.mbay.traffic.operators.TrafficRechargeNotify;
import org.sz.mbay.traffic.response.ChargeResult;

@Component("GDMobile")
public class GDMobile implements TrafficRecharge {

	static final Logger LOGGER = LoggerFactory.getLogger(GDMobile.class);

	@Override
	public ChargeResult charge(String mobile, TrafficType traffictype,
			int traffic, String ordernumber) {
		try {
			ADCServiceForECStub stub = new ADCServiceForECStub();
			AdcServices adcServices = new AdcServices();
			NGEC param = new NGEC();
			// 地市缩写，如GZ；全省业务填GD
			param.setAreacode("GD");
			// 业务代码
			param.setBIPCode("EC0001");
			// 业务流程版本号
			param.setBIPVer("V1.0");
			// 集团编码
			param.setECCode("2000460450");
			// 集团用户名
			param.setECUserName("adcadministrator");
			// 集团密码,密文
			param.setECUserPwd("z8MzqCmpWBSN8DqBPTUt74dKHnL1eYEt");
			// 发起方应用域代码
			param.setOrigDomain("NGEC");
			// 处理时间
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String str = formatter.format(curDate);
			param.setProcessTime(str);
			param.setResponse(new Response());
			// 报文体
			StringBuffer query = new StringBuffer();
			query.append("<MemberShipRequest>");
			query.append("<BODY>");
			// EC企业代码
			query.append("<ECCode>2000460450</ECCode>");
			// 集团产品号码
			query.append("<PrdOrdNum>50415000016</PrdOrdNum>");
			// 成员列表信息
			query.append("<Member>");
			// 操作类型:0-开通,1-暂停,2-注销,3-恢复,4-修改
			query.append("<OptType>0</OptType>");
			// 计费标志:0＝集团代付,1＝个人支付
			query.append("<PayFlag>0</PayFlag>");
			// 使用周期数
			query.append("<UsecyCle>1</UsecyCle>");
			// 手机号
			query.append("<Mobile>" + mobile + "</Mobile>");
			// 用户名
			query.append("<UserName>" + mobile + "</UserName>");
			// 生效方式:立即(2),下月(3),默认(0)
			query.append("<EffType>2</EffType>");
			// 成员产品列表
			query.append("<PrdList>");
			query.append("<PrdCode>AppendAttr.8585</PrdCode>");
			// 操作类型:0-订购,2-修改,4-取消
			query.append("<OptType>0</OptType>");
			// 业务列表:最多一个业务
			query.append("<Service>");
			query.append("<ServiceCode>Service8585.Mem</ServiceCode>");
			// 服务扩展信息列表,无扩展属性可不填
			query.append("</Service>");
			query.append("</PrdList>");
			query.append("<PrdList>");
			// 附加产品编码
			query.append(
					"<PrdCode>" + GDMobilePackage.getPackCodeByTraffic(traffic)
							+ "</PrdCode>");
			// 操作类型:0-订购,2-修改,4-取消
			query.append("<OptType>0</OptType>");
			query.append("</PrdList>");
			query.append("</Member>");
			query.append("</BODY>");
			query.append("</MemberShipRequest>");
			param.setSvcCont(query.toString());
			// 发起方交易流水号
			param.setTransIDO(ordernumber);
			adcServices.setRequest(param);
			Options options = stub._getServiceClient().getOptions();
			options.setProperty(HTTPConstants.SO_TIMEOUT, new Integer(120000));
			options.setProperty(HTTPConstants.CONNECTION_TIMEOUT,
					new Integer(120000));
			stub._getServiceClient().setOptions(options);
			AdcServicesResponse response = stub.adcServices(adcServices);
			NGEC ngec = response.getAdcServicesResult();
			Response res = ngec.getResponse();
			if ("0000".equals(res.getRspCode())) {
				String response_value = ngec.getSvcCont();
				Document doucment = DocumentHelper.parseText(response_value);
				Element root = doucment.getRootElement();
				Element body = root.element("BODY");
				Element member = body.element("Member");
				String flag = member.elementTextTrim("CRMApplyCode");
				// 如果flag有值,则表示成功
				if (flag == null) {
					String resultMsg = member.elementTextTrim("ResultMsg");
					LOGGER.info("订单{}充值广东移动流量返回:{}", ordernumber, resultMsg);
					TrafficRechargeNotify.failNotify(ordernumber, resultMsg);
					return new ChargeResult(ChargeResult.CHARGE_FAILE,
							"流量充值失败,失败编码：" + resultMsg);
				} else {
					LOGGER.info("订单{}充值广东移动流量返回:{}", ordernumber, "成功");
					TrafficRechargeNotify.successNotify(ordernumber);
					return ChargeResult.SUCCESS;
				}
			} else {
				String rspDesc = res.getRspDesc();
				LOGGER.info("订单{}充值广东移动流量返回:{}", ordernumber, rspDesc);
				TrafficRechargeNotify.failNotify(ordernumber, rspDesc);
				return new ChargeResult(ChargeResult.CHARGE_FAILE,
						"流量充值失败,失败编码：" + rspDesc);
			}
		} catch (Exception e) {
			LOGGER.error("请求广东移动接口异常", e.fillInStackTrace());
			TrafficRechargeNotify.failNotify(ordernumber, "请求广东移动接口失败");
			return new ChargeResult(ChargeResult.CHARGE_FAILE, "请求广东移动接口失败");
		}
	}

	public static void main(String[] args) {
		new GDMobile().charge("13411410090", TrafficType.PROVINCE, 10,
				"201501041146");
	}

	@Override
	public boolean isSupportTraffic(TrafficType ttype, int traffic) {
		return GDMobilePackage.getPackCodeByTraffic(traffic) != null;
	}
}