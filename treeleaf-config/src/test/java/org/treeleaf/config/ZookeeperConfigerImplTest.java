package org.treeleaf.config;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by yaoshuhong on 2015/12/23.
 */
public class ZookeeperConfigerImplTest {

    private static Logger log = LoggerFactory.getLogger(ZookeeperConfigerImpl.class);

    @Test
    public void testGetConfigs() throws Exception {
        ZookeeperConfigerImpl zookeeperConfiger = new ZookeeperConfigerImpl();
        zookeeperConfiger.setNamespace("");
        zookeeperConfiger.setConnectStr("112.74.129.99:2181");
        zookeeperConfiger.init();

        Map<String, String> configs = zookeeperConfiger.getConfigs();

        log.info("{}", configs);

//        Thread.sleep(Integer.MAX_VALUE);
    }
}