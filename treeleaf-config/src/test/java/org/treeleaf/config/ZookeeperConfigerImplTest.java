package org.treeleaf.config;

import org.junit.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by yaoshuhong on 2015/12/23.
 */
public class ZookeeperConfigerImplTest {

    private static Logger log = LoggerFactory.getLogger(ZookeeperConfigerImpl.class);

    @Test
    public void testGetConfigs() throws Exception {
        ZookeeperConfigerImpl zookeeperConfiger = new ZookeeperConfigerImpl();
        zookeeperConfiger.setNamespace("");
        zookeeperConfiger.setConnectStr("172.30.0.104:2181");
        zookeeperConfiger.init();

        Map<String, String> configs = zookeeperConfiger.getConfigs();

        log.info("{}", configs);

//        Thread.sleep(Integer.MAX_VALUE);
    }
}