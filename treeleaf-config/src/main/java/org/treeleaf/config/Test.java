package org.treeleaf.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * Created by yaoshuhong on 2015/12/22.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        String path = "/test_path";

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("172.30.0.104:2181")
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
                .connectionTimeoutMs(5000).build();
        client.start();

        client.create().withMode(CreateMode.PERSISTENT).forPath("/b");

//        client.create().creatingParentsIfNeeded().forPath("/leaf", "test".getBytes());
//

        // 启动 上面的namespace会作为一个最根的节点在使用时自动创建
//        client.start();

        // 创建一个节点
//        client.create().forPath("/head", new byte[0]);
//
//        // 异步地删除一个节点
//        client.delete().inBackground().forPath("/head");
//
//        // 创建一个临时节点
//        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/head/child", new byte[0]);
//
//        // 取数据
//        Object o = client.getData().watched().inBackground().forPath("/test");
//        System.out.println("o:" + String.valueOf(o));
//
//        // 检查路径是否存在
//        client.checkExists().forPath(path);
//
//        // 异步删除
//        client.delete().inBackground().forPath("/head");
//
//        // 注册观察者，当节点变动时触发
//        client.getData().usingWatcher(new Watcher() {
//            @Override
//            public void process(WatchedEvent event) {
//                System.out.println("node is changed");
//            }
//        }).inBackground().forPath("/test");

        // 结束使用
        client.close();

    }

}
