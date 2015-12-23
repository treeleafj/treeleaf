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

    private String connectStr;

    private int sleepMsBetweenRetries = 2000;

    private int retryNum = Integer.MAX_VALUE;

    private String namespace = "config";

    private CuratorFramework client;

    public void init() {
        client = CuratorFrameworkFactory.builder()
                .connectString(connectStr)
                .namespace(namespace)
                .retryPolicy(new RetryNTimes(retryNum, sleepMsBetweenRetries))
                .connectionTimeoutMs(10000).build();
        client.start();

        // 注册观察者，当节点变动时触发
//        try {
////            client.getData().usingWatcher((Watcher) event -> {
////
////                log.info("{}节点变动", event.getPath());
////
////            }).inBackground().forPath("/config");
//
//            ExecutorService pool = Executors.newFixedThreadPool(1);
//
//            PathChildrenCache childrenCache = new PathChildrenCache(client, "/", true);
//            childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
//            childrenCache.getListenable().addListener((client1, event) -> {
//                switch (event.getType()) {
//                    case CHILD_ADDED:
//                        log.info("节点添加: {}", event.getData().getPath());
//                        break;
//                    case CHILD_REMOVED:
//                        log.info("节点删除: {}", event.getData().getPath());
//                        break;
//                    case CHILD_UPDATED:
//                        log.info("节点修改: {}", event.getData().getPath());
//                        break;
//                    default:
//                        break;
//                }
//            }, pool);
//
//        } catch (Exception e) {
//            log.info("注册监听失败", e);
//        }
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

    protected Map<String, String> full(Map<String, String> map, String parentPath) throws Exception {
        List<String> children = client.getChildren().forPath(parentPath);
        if (children.size() == 0) {//说明是最后一个了
            map.remove(parentPath);
            byte[] bytes = client.getData().forPath(parentPath);
            if (bytes != null) {
                map.put(parentPath, new String(bytes, "UTF-8"));
            }
        } else {
            for (String child : children) {
                if (!parentPath.endsWith("/")) {
                    parentPath += "/";
                }
                full(map, parentPath + child);
            }
        }
        return map;
    }

    public void setConnectStr(String connectStr) {
        this.connectStr = connectStr;
    }

    public void setSleepMsBetweenRetries(int sleepMsBetweenRetries) {
        this.sleepMsBetweenRetries = sleepMsBetweenRetries;
    }

    public void setRetryNum(int retryNum) {
        this.retryNum = retryNum;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
