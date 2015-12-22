package org.treeleaf.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.treeleaf.common.safe.Des3;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * 解密配置文件,主要用于对采用了3desc加密方式的配置文件进行解密读取
 * <p>
 * Created by yaoshuhong on 2015/5/6.
 */
public class RemotePropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Logger log = LoggerFactory.getLogger(RemotePropertyConfigurer.class);

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {

        super.processProperties(beanFactoryToProcess, props);



//        props.setProperty(name, value);

    }

}
