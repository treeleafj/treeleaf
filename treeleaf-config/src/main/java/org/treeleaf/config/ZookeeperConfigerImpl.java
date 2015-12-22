package org.treeleaf.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.safe.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yaoshuhong on 2015/12/22.
 */
public class ZookeeperConfigerImpl implements Configer {

    private static Logger log = LoggerFactory.getLogger(ZookeeperConfigerImpl.class);

    private String connectStr = "172.30.0.104:2181";

    private int sleepMsBetweenRetries = 2000;

    private int retryNum = Integer.MAX_VALUE;

    private CuratorFramework client;

    public void init() {
        client = CuratorFrameworkFactory.builder()
                .connectString(connectStr)
                .retryPolicy(new RetryNTimes(retryNum, sleepMsBetweenRetries))
                .connectionTimeoutMs(10000).build();
        client.start();
    }

    @Override
    public Map<String, String> getConfigs() {

        Assert.notNull(client, "未初始化CuratorFramework");

        try {
            Map<String, String> configs = full(new HashMap<>(), "/");
            Map map = new HashMap<>();

            for (Map.Entry<String, String> entry : configs.entrySet()) {
                StringBuilder key = new StringBuilder(entry.getKey().replaceAll("/", "."));
                key.deleteCharAt(0);
                map.put(key, entry.getValue());
            }

            return map;
        } catch (Exception e) {
            log.error("读取配置出错", e);
        }
        return null;
    }

    public Map<String, String> full(Map<String, String> map, String parentPath) throws Exception {
        log.info(parentPath);
        List<String> children = client.getChildren().forPath(parentPath);
        if (children.size() == 0) {//说明是最后一个了
            map.remove(parentPath);
            map.put(parentPath, new String(client.getData().forPath(parentPath), "UTF-8"));
        } else {
            for (String child : children) {
                if (!"/".equals(parentPath)) {
                    parentPath += "/";
                }
                full(map, parentPath + child);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        ZookeeperConfigerImpl zookeeperConfiger = new ZookeeperConfigerImpl();
        zookeeperConfiger.init();
        Map<String, String> configs = zookeeperConfiger.getConfigs();
        log.info("{}", configs);
    }
}
