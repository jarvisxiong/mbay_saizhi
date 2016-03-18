package org.sz.mbay.channel.web.spring.dataView;

import static org.sz.mbay.channel.action.TrafficBuyNowAction.TRAFFIC_ORDER_LIST_KEY;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVPrinter;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.exception.error.ErrorInfo;
import org.sz.mbay.base.view.AbstractCSVView;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.trafficorder.bean.TrafficOrder;

/**
 * @author han.han
 *
 */
public class TrafficOrderCSVDownlaod extends AbstractCSVView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, CSVPrinter printer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "inline;filename=" + new String("营销明细.csv".getBytes(), "iso8859-1"));

		@SuppressWarnings("unchecked")
		List<TrafficOrder> trafficcharges = (List<TrafficOrder>) model.get(TRAFFIC_ORDER_LIST_KEY);
		
		printer.printRecord("订单号", "日期", "名称", "活动编号", "手机号", "流量", "美贝", "状态");
		trafficcharges.forEach(order -> {
			try {
				printer.printRecord(order.getNumber(),
						MbayDateFormat.formatDateTime(order.getCreateTime(), MbayDateFormat.DatePattern.isoDate),
						order.getOrderName(), order.getRelateNumber(), order.getMobile(),
						String.valueOf(order.getTrafficPackage().getTraffic()),String.valueOf(order.getMbayPrice()), order.getStatus().getValue());
			} catch (Exception e) {
				throw new ServiceException(new ErrorInfo("TRAFFIC_ORDER_CSVDOWNLOAD_ERROR", "营销明细下载失败！"));
			}

		});

	}

}
