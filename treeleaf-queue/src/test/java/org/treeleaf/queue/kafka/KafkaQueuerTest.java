package org.treeleaf.queue.kafka;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Int;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaoshuhong on 2015/12/24.
 */
public class KafkaQueuerTest {

    private static Logger log = LoggerFactory.getLogger(KafkaQueuer.class);

    public static final String TOPIC = "orderDone";

    @Test
    public void testAdd() throws Exception {
        KafkaQueuer kafkaQueuer = new KafkaQueuer();
        kafkaQueuer.setTopic(TOPIC);
        kafkaQueuer.init();

        for (int i = 0; i < 100; i++) {
            Map map = new HashMap<>();
            map.put("t2", i);
            kafkaQueuer.add(map);
        }
    }

    @Test
    public void testSetHandler() throws Exception {
        KafkaQueuer kafkaQueuer = new KafkaQueuer();
        kafkaQueuer.setTopic(TOPIC);
        kafkaQueuer.setGroupId("abc");

        kafkaQueuer.setHandler(o -> {
            log.info("Thread-{}:{}", Thread.currentThread().getId(), o);
        });

        Thread.sleep(Int.MaxValue());
    }
}