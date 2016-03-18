package org.sz.mbay.base.view;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.web.servlet.view.AbstractView;
import org.sz.mbay.common.util.HttpException;
import org.sz.mbay.traffic.util.http.HttpSupport;

public abstract class AbstractCSVView extends AbstractView {

	private static final String CONTENT_TYPE = "application/csv;charset=GBK";

	public AbstractCSVView() {
		setContentType(CONTENT_TYPE);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType(getContentType());
		response.setCharacterEncoding("GBK");
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "GBK"));
		
		CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT);
		buildExcelDocument(model, printer, request, response);
		out.flush();
		out.close();
		
		
	}
	
	protected abstract void buildExcelDocument(Map<String, Object> model, CSVPrinter printer,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	
	public static void main(String[] args) throws HttpException, IOException {
		
		org.sz.mbay.common.util.HttpSupport.build("http://localhost:8080/ad_rest/idfa/check", new HashMap<String,String>(){{
			put("idfa","fsdfdsfds");
		}}).connect();

	}

}
