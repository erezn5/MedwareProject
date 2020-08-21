package com.medware.automation;

import com.medware.automation.conf.EnvConf;
import com.medware.automation.utils.ExcelDataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
    static Logger logger = LogManager.getLogger(MainClass.class);

    public static void main(String[] args) throws SQLException, IOException {
        ExcelDataHandler excel = new ExcelDataHandler();
        List<List<String>> listMaster = excel.getData(EnvConf.getProperty("correct.excel.file.path"));
        JDBCClientManager jdbc = new JDBCClientManager();
        ResultSet rs = jdbc.performQuery("*", "medicationinfo");
        getSqlListAndCompare(listMaster, rs);
    }

    private static void getSqlListAndCompare(List<List<String>> listMaster, ResultSet rs) throws SQLException {
        int i=0;
        while (rs.next()) {
            List<String> sqlList = new ArrayList<String>();
            sqlList.add(rs.getString(1));//description
            sqlList.add(rs.getString(2));//code
            sqlList.add(rs.getString(3));//list
            sqlList.add(rs.getString(4));//duration
            if(!compareString(listMaster.get(i), sqlList)) {
                    System.out.println("Not equlas");
            }
            i++;
        }
    }

    private static boolean compareString(List<String> excelList, List<String> SQList) {
        logger.info(String.format("Start comparing both %s from expected list and %s from MySql Database",excelList, SQList));
        return excelList.equals(SQList);
    }
}
