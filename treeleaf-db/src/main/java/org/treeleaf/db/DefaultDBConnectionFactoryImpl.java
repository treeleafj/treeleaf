package org.treeleaf.db;

import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author leaf
 * 2015/1/11 0011 2:16.
 */
public class DefaultDBConnectionFactoryImpl extends DBConnectionFactory {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() {
        //使用DataSourceUtils,便于spring的事务处理
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void releaseConnection(Connection connection) {
        DbUtils.closeQuietly(connection);
    }
}
