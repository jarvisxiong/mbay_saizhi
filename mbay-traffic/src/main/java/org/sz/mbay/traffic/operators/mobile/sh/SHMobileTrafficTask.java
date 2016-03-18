package org.sz.mbay.traffic.operators.mobile.sh;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SHMobileTrafficTask extends TimerTask {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SHMobileTrafficTask.class);
	private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat fileNameformat = new SimpleDateFormat(
			"yyyy_MM_dd");

	public void run() {
		/*@Override
		LOGGER.info("执行上海移动流量充值定时任务");
		TrafficService trafficService = (TrafficService) SpringApplicationContext
				.getBean("TrafficServiceImpl");
		Area sh = new Area(31);
		List<TrafficChargeOrder> trafficOrders = trafficService
				.findAllYesterDayTrafficOrder(sh, TeleOperator.MOBILE);
		LOGGER.info("攻击充值订单：{}", trafficOrders.size());
		if (trafficOrders.size() == 0) {
			return;
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			HSSFSheet sheet = workbook.createSheet("sheet1");
			int rownum = 0;
			Row titleRow = sheet.createRow(rownum++);
			Cell tcell1 = titleRow.createCell(0);
			tcell1.setCellValue("序号");
			Cell tcell2 = titleRow.createCell(1);
			tcell2.setCellValue("手机号码");
			Cell tcell3 = titleRow.createCell(2);
			tcell3.setCellValue("开通流量包ID");
			Cell tcell5 = titleRow.createCell(3);
			tcell5.setCellValue("数据提交时间");
			Cell tcell6 = titleRow.createCell(4);
			tcell6.setCellValue("是否开通成功");
			Cell tcell7 = titleRow.createCell(5);
			tcell7.setCellValue("不成功原因");
			Cell tcell8 = titleRow.createCell(6);
			tcell8.setCellValue("备注");
			String now = format.format(new Date());
			for (TrafficChargeOrder order : trafficOrders) {

				Row row = sheet.createRow(rownum++);
				int cellnum = 0;
				Cell cell = row.createCell(cellnum++);
				cell.setCellValue(order.getNumber());// 序号

				Cell mcell = row.createCell(cellnum++);
				mcell.setCellValue(order.getMobile());// 手机号码

				String[] trafficp = SHMobilePackage.getPackCodeByTraffic(
						TrafficType.PROVINCE, order.getTraffic());
				Cell trafficCell = row.createCell(cellnum++);
				trafficCell.setCellValue(trafficp[0]); // 流量包id

				Cell postTime = row.createCell(cellnum++);
				postTime.setCellValue(now); // 提交日期

				Cell cell1 = row.createCell(cellnum++);
				cell1.setCellValue("");

				Cell cell2 = row.createCell(cellnum++);
				cell2.setCellValue("");
				Cell cell3 = row.createCell(cellnum++);
				cell3.setCellValue("");
			}
		} catch (Exception e) {
			LOGGER.info("生成Excel异常",e.fillInStackTrace());
		}
		try {
			String filePath = MbayTrafficConfig.getTrafficRechargeExcelURL();
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String filename = "test_" + fileNameformat.format(new Date())+ "_shcm.xls";
			FileOutputStream out = new FileOutputStream(filePath + filename);
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
			MbayMailMsg msg = new MbayMailMsg("mbsnoc_shcm@mbqianbao.com",null, "流量充值", "见附件");
			try {
				MbayMail.setMailWithAttachments(msg, filePath + filename);
			} catch (Exception e) {
				LOGGER.error("发送Emial excel异常",e.fillInStackTrace());
			}

		} catch (FileNotFoundException e) {
			LOGGER.error("excel文件写入本地文件异常",e.fillInStackTrace());
		} catch (IOException e) {
			LOGGER.error("excel文件写入本地文件异常",e.fillInStackTrace());
		}

		 */
	}
	public static void main(String[] args) {
		// MbayMailMsg msg = new MbayMailMsg("272130640@qq.com", "流量充值", "测试");
		// MbayMail.sendTextEmil(msg);

	}

}
