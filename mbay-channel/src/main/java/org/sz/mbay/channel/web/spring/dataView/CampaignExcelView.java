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
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;

/**
 * 活动excel视图处理类
 * 
 * @author Fenlon
 * 
 */
public class CampaignExcelView extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "inline;filename="
				+ new String("活动列表.xls".getBytes(), "iso8859-1"));
		@SuppressWarnings("unchecked")
		List<StoreCampaign> campaigns = (List<StoreCampaign>) model
				.get("campaignList");
		
		HSSFSheet sheet = workbook.createSheet("campaigns");
		String[] fields = { "活动编号", "活动名称", "创建日期", "有效期", "活动状态", "发放红包数量",
				"兑换红包数量" };
		
		fillContent(campaigns, sheet, workbook, fields);
	}
	
	private void fillContent(List<StoreCampaign> campaigns, HSSFSheet sheet,
			HSSFWorkbook workbook, String[] fields) {
		int rowsize = campaigns.size() + 2;
		int col = fields.length + 1;
		try {
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFCellStyle.THIN_BACKWARD_DIAG);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// style.setLeftBorderColor(HSSFColor.RED.index);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			
			style.setWrapText(true);
			
			// sheet = workbook.createSheet("sheet " + ((int) (100000 *
			// Math.random())));
			
			// 设置列的宽度
			sheet.setDefaultColumnWidth(20);
			sheet.setDefaultRowHeightInPoints(30);
			// sheet.setDefaultRowHeight((short)500);
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
					cell.setCellValue("活动信息表");
					continue;
				} else if (r == 1) {
					sheet.addMergedRegion(new CellRangeAddress(r, r, 1,
							2));
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
										.getName());
								break;
							case 3:
								cell.setCellValue(
										MbayDateFormat.formatDateTime(campaigns
												.get(r - 2).getDateCreated(),
												DatePattern.isoTime));
								break;
							case 4:
								cell.setCellValue(campaigns.get(r - 2)
										.getValidity());
								break;
							case 5:
								cell.setCellValue(campaigns.get(r - 2)
										.getStatus().getValue());
								break;
							case 6:
								cell.setCellValue(campaigns.get(r - 2)
										.getDeliverNum());
								break;
							case 7:
								cell.setCellValue(campaigns.get(r - 2)
										.getRedeemNum());
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
