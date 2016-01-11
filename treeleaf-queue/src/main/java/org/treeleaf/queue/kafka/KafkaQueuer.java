package org.treeleaf.queue.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.json.Jsoner;
import org.treeleaf.common.safe.Assert;
import org.treeleaf.queue.MsgHandler;
import org.treeleaf.queue.Queuer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yaoshuhong on 2015/12/24.
 */
public class KafkaQueuer implements Queuer {

    private static Logger log = LoggerFactory.getLogger(KafkaQueuer.class);

    /**
     * 消息发送器
     */
    private static Producer<String, String> producer;

    /**
     * kafka的borker地址列表
     */
    private String borkerList = "112.74.129.99:9092";

    /**
     * zookeeper连接地址
     */
    private String zookeeperConnect = "112.74.129.99:2181";

    /**
     * 消费者组id
     */
    private String groupId = "test";

    /**
     * 主题
     */
    private String topic;

    private Map<String, String> producerConfig = new HashMap<>();

    private Map<String, String> consumerConfig = new HashMap<>();

    static int num = 0;

    /**
     * 初始化生产者
     */
    public void init() {

        Assert.hasText(topic, "发送的topic不能为空");

        Properties props = new Properties();
        props.put("metadata.broker.list", this.borkerList);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");

        props.putAll(producerConfig);

        ProducerConfig config = new ProducerConfig(props);

        producer = new Producer<>(config);
    }

    @Override
    public void add(Object msg) {
        String s = Jsoner.toJson(msg);
        log.info("往kafka-{}发生消息{}", topic, s);
        producer.send(new KeyedMessage<>(topic, s));

//        producer.close();
//
//        this.init();
    }

    @Override
    public void setHandler(MsgHandler handler) {

        Assert.hasText(topic, "消费者Topic不能为空");

        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeperConnect);
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", "1000");
        props.put("zookeeper.sync.time.ms", "1000");
        props.put("auto.commit.interval.ms", "1000");

        props.putAll(consumerConfig);

        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);

        Map<String, Integer> topicCountMap = new HashMap<>();

        int threadNum = 1;

        topicCountMap.put(topic, threadNum);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);

        log.info("MessageStreams数量:{}", consumerMap.size());

        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        log.info("获取stream条数:{}", streams.size());


        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        for (KafkaStream stream : streams) {

            executorService.submit(() -> {
                ConsumerIterator<byte[], byte[]> it = stream.iterator();
                while (it.hasNext()) {
                    String msg = new String(it.next().message());
                    handler.handler(msg);
                }
            });

        }
    }

    public void setBorkerList(String borkerList) {
        this.borkerList = borkerList;
    }

    public void setZookeeperConnect(String zookeeperConnect) {
        this.zookeeperConnect = zookeeperConnect;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setProducerConfig(Map<String, String> producerConfig) {
        this.producerConfig = producerConfig;
    }

    public void setConsumerConfig(Map<String, String> consumerConfig) {
        this.consumerConfig = consumerConfig;
    }
}
