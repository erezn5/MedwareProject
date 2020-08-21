package com.medware.automation.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelDataHandler {
    public List<List<String>> getData(String filePath) throws IOException {
        List<List<String>> arrayListMaster = new ArrayList<>();
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("medi");
        Iterator<Row> rows = sheet.iterator();//sheet is collection of rows
        rows.next();//to skip first column names
        while(rows.hasNext()) {
            List<String> arrayList = new ArrayList<>();
            Row row = rows.next();
            Iterator<Cell> cv = row.cellIterator();
            int i=1;
                while(cv.hasNext()) {
                    Cell c = cv.next();
                    if(i==4){
                        arrayList.add(c.toString().split("\\.")[0].trim());
                        continue;
                    }
                    if(c.getCellTypeEnum()== CellType.NUMERIC){
                        arrayList.add(c.toString().trim());
                    }else{
                        arrayList.add(c.getStringCellValue().trim());
                    }
                    i++;
                }
            arrayListMaster.add(arrayList);
        }
        return arrayListMaster;
    }
}
