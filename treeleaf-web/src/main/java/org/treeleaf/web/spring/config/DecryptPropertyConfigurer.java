package org.treeleaf.web.spring.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.treeleaf.common.safe.ThreeDesUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * 解密配置文件,主要用于对采用了3desc加密方式的配置文件进行解密读取
 * <p/>
 * Created by yaoshuhong on 2015/5/6.
 */
public class DecryptPropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Logger log = LoggerFactory.getLogger(DecryptPropertyConfigurer.class);

    /**
     * 3des加密的密钥KEY
     */
    private String key;

    /**
     * 要解密的键名称
     */
    private List<String> decryptKeyNames = new ArrayList<String>();


    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {

        Enumeration names = props.propertyNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if (!this.decryptKeyNames.contains(name)) {
                continue;
            }
            String value = props.getProperty(name);
            if (StringUtils.isBlank(value)) {
                continue;
            }
            try {
                value = ThreeDesUtils.decrypt(value, key);
            } catch (Exception e) {
                log.error("3desc解析配置文件失败,错误的值:{}", value, e);
                throw new RuntimeException("3desc解析配置文件失败,错误的值:" + value, e);
            }
            props.setProperty(name, value);
        }

        super.processProperties(beanFactoryToProcess, props);
    }

    public void setDecryptKeyNames(List<String> decryptKeyNames) {
        this.decryptKeyNames = decryptKeyNames;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
