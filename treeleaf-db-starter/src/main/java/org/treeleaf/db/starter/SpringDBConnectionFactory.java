package org.treeleaf.db.starter;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.treeleaf.db.DBConnectionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 基于spring-jdbc的数据库连接获取工具
 *
 * @author leaf
 * @date 2016-11-17 11:39
 */
public class SpringDBConnectionFactory extends DBConnectionFactory {

    private DataSource dataSource;

    public SpringDBConnectionFactory() {
    }

    public SpringDBConnectionFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        return DataSourceUtils.getConnection(this.dataSource);
    }

    public void releaseConnection(Connection connection) {
        DataSourceUtils.releaseConnection(connection, this.dataSource);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
