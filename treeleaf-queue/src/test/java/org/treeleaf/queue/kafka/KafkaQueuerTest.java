package org.treeleaf.queue.kafka;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Int;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        for (int i = 0; i < 100; i++) {
            Map map = new HashMap<>();
            map.put("t2", dateFormat.format(new Date()) + "   " + i);
            kafkaQueuer.add(map);
        }
    }

    @Test
    public void testSetHandler() throws Exception {
        KafkaQueuer kafkaQueuer = new KafkaQueuer();
        kafkaQueuer.setTopic(TOPIC);
        kafkaQueuer.setGroupId("1abc3");

        kafkaQueuer.setHandler(o -> {
            log.info("Thread-{}:{}", Thread.currentThread().getId(), o);
        });

        Thread.sleep(Int.MaxValue());
    }
}