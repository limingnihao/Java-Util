package org.limingnihao.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库操作
 */
public class MySqlUtil {
    private static final Logger logger = LoggerFactory.getLogger(MySqlUtil.class);

    private Connection connection = null;

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public MySqlUtil(String url, String username, String password) throws SQLException {
        logger.info("MySqlUtil - url=" + url + ", username=" + username + ", password=" + password);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("链接mysql出现错误");
        }
    }

    /**
     * 执行delete
     * @param sql
     * @return
     */
    public boolean execute(String sql) throws SQLException {
        PreparedStatement pst = null;
        try {
            pst = this.connection.prepareStatement(sql);
            //logger.info("execute: " + sql);
            return pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("execute - error - sql=" + sql);
            throw e;
        }
        finally {
            if(pst !=null ){
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 执行insert,update
     * @param sql
     * @return
     */
    public int executeUpdate(String sql, List<Object> pras) throws SQLException {
        PreparedStatement pst = null;
        try {
            pst = this.connection.prepareStatement(sql);
            if(pras != null){
                for(int i=0;i<pras.size(); i++){
                    pst.setObject(i+1, pras.get(i));
                }
            }
            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("execute - error - sql=" + sql);
            throw e;
        }
        finally {
            if(pst !=null ){
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 查询
     * @param sql
     * @param pras
     * @return
     */
    public ResultSet executeQuery(String sql, List<Object> pras) throws SQLException {
        PreparedStatement pst = null;
        try {
            pst = this.connection.prepareStatement(sql);
            if(pras != null){
                for(int i=0;i<pras.size(); i++){
                    pst.setObject(i+1, pras.get(i));
                }
            }
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("executeQuery - error - sql=" + sql);
            throw e;
        }
        finally {
            if(pst !=null ){
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 存储过程
     * @param sql
     * @param parameter
     * @return
     */
    public boolean procedure(String sql, HashMap<String, String> parameter) throws SQLException {
        CallableStatement cst = null;
        try {
            cst = this.connection.prepareCall(sql);
            Iterator iter = parameter.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                cst.setString(key.toString(), val.toString());
            }
            return cst.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("procedure - error - sql=" + sql);
            throw e;
        } finally {
            if(cst !=null ){
                try {
                    cst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
