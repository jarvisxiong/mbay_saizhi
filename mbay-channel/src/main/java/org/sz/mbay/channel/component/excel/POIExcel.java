package org.sz.mbay.channel.component.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIExcel {

    public static Iterator<Row> readExcel(InputStream inputstream,EXCELType type) throws IOException{
        switch(type){
        case XLS:return readExcelOfXLS(inputstream);
        case XLSX:return readExcelOfXLSX(inputstream);
        }
        throw new IOException("不支持的文件类型");

    }

    private static Iterator<Row> readExcelOfXLS(InputStream inputstream) throws IOException{
        HSSFWorkbook wb = new HSSFWorkbook(inputstream);
        HSSFSheet sheet=wb.getSheetAt(0);
        return sheet.rowIterator();

    }

    private static Iterator<Row> readExcelOfXLSX(InputStream inputstream) throws IOException{
        XSSFWorkbook  wb = new XSSFWorkbook(inputstream);
        XSSFSheet sheet = wb.getSheetAt(0);
        return sheet.rowIterator();
    }

}
