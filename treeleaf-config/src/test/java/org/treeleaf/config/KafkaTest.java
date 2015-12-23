package org.treeleaf.config;

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

import java.util.*;

/**
 * Created by yaoshuhong on 2015/12/23.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextHierarchy({
//        @ContextConfiguration(name = "parent", locations = "classpath:applicationContext.xml")
//})
public class KafkaTest {

    private static Logger log = LoggerFactory.getLogger(KafkaTest.class);

//    @Autowired
//    private ApplicationContext applicationContext;

    @org.junit.Test
    public void send() {
        String topic = "test";
        Random rand = new Random();

        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);

        while (true) {

            Producer<String, String> producer = new Producer<>(config);
            for (long i = 0; i < 100; i++) {
                String msg = "消息-" + rand.nextInt();
                KeyedMessage<String, String> data = new KeyedMessage<>(topic, i + "", msg);
                producer.send(data);
                log.info("发送成功");
            }
            producer.close();
        }
    }

    @org.junit.Test
    public void consumert() {
        System.out.println("开始消费");
        log.info("开始消费");
        String topic = "test";

        Properties props = new Properties();
        props.put("zookeeper.connect", "172.30.0.104:2181");
        props.put("group.id", "cashierpay");
        props.put("zookeeper.session.timeout.ms", "1000");
        props.put("zookeeper.sync.time.ms", "1000");
        props.put("auto.commit.interval.ms", "1000");

        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);

        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(topic, 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
        log.info("createMessageStreams:{}", consumerMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        log.info("获取数据条数:{}", streams.size());
        for (KafkaStream stream : streams) {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {
//                log.info("消息:{}", new String(it.next().message()));
                System.out.println("消息:"  + new String(it.next().message()));
            }
        }

        log.info("shutdown");
        consumerConnector.shutdown();

    }

}
