package com.medware.automation;

import com.medware.automation.conf.EnvConf;
import com.medware.automation.utils.ExcelDataHandler;
import com.medware.automation.utils.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VerifyDataBaseDataIntegrityTest {
    ExcelDataHandler excel;

    JDBCClientManager jdbc;
    ResultSet rs;

    private static final String CORRECT_DATA_EXCEL_FILE_PATH = EnvConf.getProperty("correct.excel.file.path");
    private static final String MALFORMED_DATA_EXCEL_FILE_PATH = EnvConf.getProperty("malformed.excel.file.path");
    private static String OUTPUT_FOLDER = EnvConf.getProperty("output.folder");

    @BeforeClass
    public void setUp() throws SQLException {
        FileUtils.createFolder(new File(OUTPUT_FOLDER), false);
        excel = new ExcelDataHandler();
        jdbc = new JDBCClientManager();
        rs = jdbc.performQuery("*", "medicationinfo");
    }

    @Test
    public void verifyTrueData() throws SQLException, IOException {
        List<List<String>> listMaster = excel.getData(CORRECT_DATA_EXCEL_FILE_PATH);
        Assert.assertTrue(jdbc.getSqlListAndCompare(listMaster, rs));
    }

    @Test void verifiedMalformedData() throws SQLException, IOException {
        List<List<String>> listMaster = excel.getData(MALFORMED_DATA_EXCEL_FILE_PATH);
        Assert.assertTrue(jdbc.getSqlListAndCompare(listMaster, rs));
    }

    @AfterClass
    public void tearDown() throws Exception {
        jdbc.closeMySQLConnection();
    }
}
