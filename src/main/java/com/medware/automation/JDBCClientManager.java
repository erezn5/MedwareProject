package com.medware.automation;

import com.medware.automation.conf.EnvConf;
import com.medware.automation.utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JDBCClientManager {
    private static String FILE_PATH = EnvConf.getProperty("output.folder") + "/report.txt";
    private static String SQL_INSTANCE_HOSTNAME = EnvConf.getProperty("sql.hostname");
    private static String SQL_INSTANCE_PORT = String.valueOf(EnvConf.getAsInteger("sql.port"));
    private static String SQL_DB_NAME = EnvConf.getProperty("sql.db.name");
    private static String SQL_USERNAME = EnvConf.getProperty("sql.username");
    private static String SQL_PASSWORD = EnvConf.getProperty("sql.password");
    private static String jdbcUrl = "jdbc:mysql://" + SQL_INSTANCE_HOSTNAME + ":" + SQL_INSTANCE_PORT + "/" + SQL_DB_NAME + "?serverTimezone=UTC";
    private static Connection con;
    private static String GENERIC_QUERY = "SELECT %s FROM %s;";
    public static Logger log = LogManager.getLogger(JDBCClientManager.class.getName());

    static{
        try {
            connectToSqlServer();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void connectToSqlServer() throws SQLException {
        con = DriverManager.getConnection(jdbcUrl, SQL_USERNAME, SQL_PASSWORD);
        log.info("Connection is Successful!");
    }

    public ResultSet performQuery(String value, String from) throws SQLException {
        Statement s = con.createStatement();
        return s.executeQuery(String.format(GENERIC_QUERY,value,from));
    }

    public void closeMySQLConnection() throws SQLException {
        log.info("Closing SQL Connection...");
        if(!con.isClosed()){
            con.close();
        }
    }

    public boolean getSqlListAndCompare(List<List<String>> listMaster, ResultSet rs) throws SQLException {
        int i=0;
        boolean flag = true;
        while (rs.next()) {
            List<String> sqlList = new ArrayList<String>();
            sqlList.add(rs.getString(1));//description
            sqlList.add(rs.getString(2));//code
            sqlList.add(rs.getString(3));//list
            sqlList.add(rs.getString(4));//duration
            if(!compareString(listMaster.get(i), sqlList)) {
                log.error("Not Equlas");
                FileUtils.writeToFile(FILE_PATH, listMaster.get(i),"Excel Sheet");
                FileUtils.writeToFile(FILE_PATH, sqlList, "SQL List");
                flag=false;
            }
            i++;
        }

        if(flag){
            FileUtils.writeToFile(FILE_PATH, Collections.singletonList("Pass"), "No Type");
        }

        return flag;
    }

    private static boolean compareString(List<String> excelList, List<String> SQList) {
        log.info(String.format("Start comparing both %s from expected list and %s from MySql Database",excelList, SQList));
        return excelList.equals(SQList);
    }

}
