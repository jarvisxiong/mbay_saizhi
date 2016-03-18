package org.sz.mbay.channel.web.spring.dataView;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.exception.error.ErrorInfo;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.trafficorder.bean.TrafficOrder;

/**
 * app诱惑营销明细excel导出
 * 
 * @author jerry
 */
public class AppTemptationRecordExcel extends AbstractExcelView {
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			HSSFWorkbook workbook,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "inline;filename="
				+ new String("app诱惑结果查询列表.xls".getBytes(), "iso8859-1"));
		List<TrafficOrder> data = (List<TrafficOrder>) model.get("data");
		
		HSSFSheet sheet = workbook.createSheet("campaigns");
		String[] fields = { "订单号", "订单名称", "活动编号", "手机号", "流量", "美贝价格", "充值日期",
				"状态" };
		fillContent(data, sheet, workbook, fields);
	}
	
	private void fillContent(
			List<TrafficOrder> campaigns,
			HSSFSheet sheet,
			HSSFWorkbook workbook,
			String[] fields) {
		int rowsize = campaigns.size() + 2;
		int col = fields.length + 1;
		try {
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFCellStyle.THIN_BACKWARD_DIAG);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			
			style.setWrapText(true);
			
			// 设置列的宽度
			sheet.setDefaultColumnWidth(20);
			sheet.setDefaultRowHeightInPoints(30);
			Row row = null;
			Cell cell = null;
			
			for (int r = 0; r < rowsize; r++) {
				row = sheet.createRow(r);
				
				// 设置第1行当高度
				row.setHeightInPoints(30);
				
				if (r == 0) {
					cell = row.createCell(0);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
							fields.length));
					cell.setCellStyle(style);
					cell.setCellValue("充值订单表");
					continue;
				} else if (r == 1) {
					sheet.addMergedRegion(new CellRangeAddress(r, r, 1, 2));
					for (int c = 0; c < col; c++) {
						cell = row.createCell(c);
						cell.setCellStyle(style);
						if (c <= 1) {
							cell.setCellValue(fields[c]);
						} else if (c > 1) {
							cell.setCellValue(fields[c - 1]);
						}
					}
					continue;
				} else if (r > 1) {
					for (int c = 0; c < col; c++) {
						cell = row.createCell(c);
						cell.setCellStyle(style);
						sheet.addMergedRegion(new CellRangeAddress(r, r, 1, 2));
						switch (c) {
							case 0:
								cell.setCellValue(campaigns.get(r - 2)
										.getNumber());
								break;
							case 1:
								cell.setCellValue(campaigns.get(r - 2)
										.getOrderName());
								break;
							case 3:
								cell.setCellValue(campaigns.get(r - 2)
										.getRelateNumber());
								break;
							case 4:
								cell.setCellValue(campaigns.get(r - 2)
										.getMobile());
								break;
							case 5:
								cell.setCellValue(campaigns.get(r - 2)
										.getTrafficPackage().getTraffic());
								break;
							case 6:
								cell.setCellValue(campaigns.get(r - 2)
										.getMbayPrice());
								break;
							case 7:
								cell.setCellValue(MbayDateFormat
										.formatDateTime(campaigns.get(r - 2)
												.getCreateTime(),
												DatePattern.isoTime));
								break;
							case 8:
								if (campaigns.get(r - 2).getStatus() != null) {
									cell.setCellValue(campaigns.get(r - 2)
											.getStatus().getValue());
								}
								break;
							default:
								break;
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException(new ErrorInfo("EXCEL ERROR",
					"export excel error"));
		}
	}
}
