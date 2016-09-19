package org.treeleaf.db.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.treeleaf.web.spring.interceptor.DBConnectionHandlerInterceptor;

/**
 * @author yaoshuhong
 * @date 2016-09-14 12:47
 */
@Configuration
@ComponentScan(basePackages = "org.treeleaf.db.starter")
@EnableConfigurationProperties(DBStarterConfigurationProperties.class)
public class DBStarterConfiguration {

    private static Logger log = LoggerFactory.getLogger(DBStarterConfiguration.class);

    @Autowired
    private DBStarterConfigurationProperties DBStarterConfigurationProperties;

    @Bean
    public DBConnectionHandlerInterceptor dbConnectionHandlerInterceptor() {
        return new DBConnectionHandlerInterceptor();
    }
}
