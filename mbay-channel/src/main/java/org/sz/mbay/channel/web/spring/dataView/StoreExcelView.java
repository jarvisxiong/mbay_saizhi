package org.sz.mbay.channel.web.spring.dataView;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.exception.error.ErrorInfo;
import org.sz.mbay.channel.action.util.QRCodeUtil;
import org.sz.mbay.channel.bean.Store;

/**
 * o2o活动excel视图处理类
 * 
 * @author Fenlon
 * 
 */
public class StoreExcelView extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "inline;filename="
				+ new String("门店列表.xls".getBytes(), "iso8859-1"));
		@SuppressWarnings("unchecked")
		List<Store> stores = (List<Store>) model.get("storeList");
		
		HSSFSheet sheet = workbook.createSheet("stores");
		String[] fields = { "编号", "门店名称", "授权码", "是否激活", "门店状态", "操作员数量", "门店二维码" };
		/*
		 * createTag(fields, sheet);
		 * addValue(storelist, sheet, workbook);
		 */
		
		fillContent(stores, sheet, workbook, fields);
	}
	
	private void fillContent(List<Store> stores, HSSFSheet sheet, HSSFWorkbook workbook, String[] fields) {
		String baseURL = ResourceConfig.getWebDomain()
				+ ResourceConfig.getContextPath();
		int rowsize = stores.size() + 2;
		int col = fields.length + 1;
		HSSFPatriarch patri = null;
		HSSFClientAnchor anchor = null;
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
			sheet.setDefaultColumnWidth(18);
			// sheet.setDefaultRowHeight((short)500);
			Row row = null;
			Cell cell = null;
			
			for (int r = 0; r < rowsize; r++) {
				row = sheet.createRow(r);
				
				// 设置第1行当高度
				if (r == 0 || r == 1) {
					row.setHeightInPoints(30);
				} else {
					row.setHeightInPoints(100);
				}
				if (r == 0) {
					cell = row.createCell(0);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, fields.length));
					cell.setCellStyle(style);
					cell.setCellValue("门店信息表");
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
								cell.setCellValue(stores.get(r - 2).getNumber());
								break;
							case 1:
								cell.setCellValue(stores.get(r - 2).getName());
								break;
							case 3:
								cell.setCellValue(stores.get(r - 2).getAuthCode());
								break;
							case 4:
								cell.setCellValue(stores.get(r - 2).isActive() == true ? "已激活" : "未激活");
								break;
							case 5:
								cell.setCellValue(stores.get(r - 2).isEnable() == true ? "启用" : "禁用");
								break;
							case 6:
								cell.setCellValue(stores.get(r - 2).getOperatorNum());
								break;
							case 7:
								patri = (HSSFPatriarch) sheet.createDrawingPatriarch();
								anchor = new HSSFClientAnchor(10, 2, 0, 0, (short) c, r, (short) (c + 1), r + 1);
								String url = baseURL + "/store/" + stores.get(r - 2).getId() + "/listCampaign.mbay";
								patri.createPicture(anchor, workbook.addPicture(QRCodeUtil.getQRCodeBytes(url, null, null), HSSFWorkbook.PICTURE_TYPE_JPEG));
								break;
							default:
								break;
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException(new ErrorInfo("EXCEL ERROR", "export excel error"));
		}
	}
}
