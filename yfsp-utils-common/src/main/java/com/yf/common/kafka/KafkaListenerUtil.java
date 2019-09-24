package com.yf.common.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 *
 * @author ouyangyufeng
 * @date 2019/2/18
 * 统一处理消息机制格式
 */
public class KafkaListenerUtil {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerUtil.class);

    public static Optional<?> MessageListener(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        try {
            if (kafkaMessage.isPresent()) {
                logger.info("kafka Consume消费消息成功！topic=" + record.topic() +
                        ",partition" + record.partition()+
                        ",key=" + record.key() +
                        ",offset=" + record.offset()+
                        ",value=" + record.value());
            }
        } catch (Exception ex) {
            logger.error("获取consume返回值失败", ex);
        }
        return kafkaMessage;
    }

}

