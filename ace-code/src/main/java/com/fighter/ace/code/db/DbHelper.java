package com.fighter.ace.code.db;

import com.fighter.ace.code.util.GeneratorConfigReader;
import com.fighter.ace.code.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by hanebert on 17/10/26.
 */
public class DbHelper {

    private static final Logger logger = LoggerFactory.getLogger(DbHelper.class);

    private Connection connection;

    private void loadJdbcDriver() {
        String driver = GeneratorConfigReader.getBuildConfigFile().getBuildPropertyValueByName("jdbc.driverClass");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.error("not found jdbc driver class:[" + driver + "]");
            throw new RuntimeException("not found jdbc driver class:[" + driver + "]", e);
        }
    }


    public Connection getConnection() {
        try {
            XMLHelper e = GeneratorConfigReader.getBuildConfigFile();
            if(this.connection == null || this.connection.isClosed()) {
                this.loadJdbcDriver();
                this.connection = DriverManager.getConnection(e.getBuildPropertyValueByName("jdbc.url"), e.getBuildPropertyValueByName("jdbc.userName"), e.getBuildPropertyValueByName("jdbc.userPwd"));
            }
            return this.connection;
        } catch (SQLException var2) {
            this.logger.error("数据库连接失败，请检查数据库配置是否正确。");
            throw new RuntimeException(var2);
        }
    }

    public boolean isOracleDataBase() {
        boolean ret = false;

        try {
            ret = this.getMetaData().getDatabaseProductName().toLowerCase().indexOf("oracle") != -1;
        } catch (Exception var3) {
            ;
        }

        return ret;
    }


    public String queryForString(String sql) {
        Statement s = null;
        ResultSet rs = null;

        String result;
        try {
            s = getConnection().createStatement();
            rs = s.executeQuery(sql);
            if(!rs.next()) {
                result = null;
                return result;
            }
            result = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            Object var5 = null;
            return (String)var5;
        } finally {
            this.close(rs, (PreparedStatement)null, new Statement[]{s});
        }

        return result;
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return this.getConnection().getMetaData();
    }

    public String getCatalog() {
        return GeneratorConfigReader.getBuildConfigFile().getBuildPropertyValueByName("jdbc.catalog");
    }

    public String getSchema() {
        return GeneratorConfigReader.getBuildConfigFile().getBuildPropertyValueByName("jdbc.schema");
    }



    public void close(ResultSet rs, PreparedStatement ps, Statement... statements) {
        try {
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
                rs.close();
            }
            Statement[] e = statements;
            int len = statements.length;
            for(int i = 0; i < len; ++i) {
                Statement s = e[i];
                s.close();
            }
        } catch (Exception e) {

        }
    }


    public static void main(String[] args) {
        DbHelper dbhelper = new DbHelper();
        dbhelper.getConnection();
        String name = dbhelper.queryForString("select * from t_user");
        System.out.println(name);
    }

}
