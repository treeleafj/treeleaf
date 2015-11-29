package org.treeleaf.db;

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

    /**
     * 初始化ConnectionContext,便于其他地方获取数据库链接
     */
    public void init() {
        ConnectionContext.setDbConnectionFactory(this);
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
}
