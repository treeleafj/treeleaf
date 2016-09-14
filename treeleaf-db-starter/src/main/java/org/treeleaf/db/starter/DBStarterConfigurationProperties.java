package org.treeleaf.db.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * web端配置
 *
 * @author yaoshuhong
 * @date 2016-09-14 12:50
 */
@Component
@ConfigurationProperties("treeleaf.db")
public class DBStarterConfigurationProperties {

}
