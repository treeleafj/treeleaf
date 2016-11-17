package org.treeleaf.db;

import java.sql.Connection;

/**
 * Created by leaf on 2015/1/4 0004.
 */
public abstract class DBConnectionFactory {

    public abstract Connection getConnection();

    public abstract void releaseConnection(Connection connection);
}
