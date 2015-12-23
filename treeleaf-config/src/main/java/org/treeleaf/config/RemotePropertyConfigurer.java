package org.treeleaf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Map;
import java.util.Properties;

/**
 * 解密配置文件,主要用于对采用了3desc加密方式的配置文件进行解密读取
 * <p>
 * Created by yaoshuhong on 2015/5/6.
 */
public class RemotePropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Logger log = LoggerFactory.getLogger(RemotePropertyConfigurer.class);

    private Configer configer;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {

        super.processProperties(beanFactoryToProcess, props);

        Map<String, String> configs = configer.getConfigs();

        for (Map.Entry<String, String> entry : configs.entrySet()) {
            log.debug("加载配置:{}={}", entry.getKey(), entry.getValue());
            props.setProperty(entry.getKey(), entry.getValue());
        }

    }

}
