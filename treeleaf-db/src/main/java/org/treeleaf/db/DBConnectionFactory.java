package org.treeleaf.db;

import java.sql.Connection;

/**
 * Created by Administrator on 2015/1/4 0004.
 */
public abstract class DBConnectionFactory {

    public abstract Connection getConnection();


}
