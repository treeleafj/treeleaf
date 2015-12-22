package org.treeleaf.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库链接上下文,专门用户获取数据库链接
 * <p>
 * Created by leaf on 2015/1/4 0004.
 */
public class ConnectionContext {

    private static Logger log = LoggerFactory.getLogger(ConnectionContext.class);

    private static DBConnectionFactory dbConnectionFactory;

    /**
     * 当前线程中通过ConnectionContext.getConnection()获取的数据库链接
     */
    private static ThreadLocal<List<Connection>> currentConnections = new ThreadLocal<>();

    /**
     * 设置当前获取数据库链接的工程
     *
     * @param dbConnectionFactory
     */
    public static void setDbConnectionFactory(DBConnectionFactory dbConnectionFactory) {
        ConnectionContext.dbConnectionFactory = dbConnectionFactory;
    }

    /**
     * 获取当前的Connection连接
     *
     * @return
     */
    public static Connection getConnection() {
        List<Connection> connections = currentConnections.get();

        try {
            if (connections != null && connections.size() > 0) {
                for (Connection conn : connections) {
                    if (!conn.isClosed()) {
                        return conn;
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("检查数据库连接是否关闭时出现异常", e);
        }

        Connection connection = dbConnectionFactory.getConnection();
        if (connections == null) {
            connections = new ArrayList<>();
            currentConnections.set(connections);
        }

        connections.add(connection);
        return connection;
    }

    /**
     * 关闭当前线程中获取的所有数据库链接
     */
    public static void closeCurrentConnections() {
        List<Connection> connections = currentConnections.get();
        if (connections == null) {
            return;
        }
        int count = connections.size();
        int successCount = 0;
        for (int i = 0; i < count; i++) {
            successCount++;
            try {
                if (!connections.get(i).isClosed()) {
                    connections.get(i).close();
                }
            } catch (SQLException e) {
                log.error("关闭数据库链接异常.", e);
            }
        }
        if (count != successCount) {
            log.warn("尝试关闭数据库链接" + count + "个,其中成功" + successCount + "个");
        }
        clearCurrentConnections();
    }

    /**
     * 删除当前线程中持有的数据库链接缓存
     */
    public static void clearCurrentConnections() {
        currentConnections.remove();
    }
}
