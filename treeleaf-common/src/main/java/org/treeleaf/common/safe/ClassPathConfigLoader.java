package org.treeleaf.common.safe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * classpath路径下资源加载器,用于加载classpath路径下的配置文件
 * <p/>
 * Created by yaoshuhong on 2015/6/8.
 */
public class ClassPathConfigLoader {

    /**
     * 加载classpath路径下的配置文件,并转为map返回,如果不存在则返回null
     *
     * @param filename 文件名称
     * @return
     */
    public static Map<String, String> load2Map(String filename) {

        InputStream in = load(filename);
        if (in == null) {
            return null;
        }

        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> map = new HashMap<String, String>();

        Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            map.put(key, properties.getProperty(key));
        }

        return map;
    }

    /**
     * 加载classpath路径下的资源,如果存在则返回InputStream,如果不存在则返回null
     *
     * @param filename 文件名称
     * @return
     */
    public static InputStream load(String filename) {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        return in;
    }
}
