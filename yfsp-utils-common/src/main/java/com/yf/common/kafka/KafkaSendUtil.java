package com.yf.common.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Created by ouyangyufeng on 2019/2/18.
 */
@Component("KafkaSendUtil")
public class KafkaSendUtil {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static Logger logger = LoggerFactory.getLogger(KafkaSendUtil.class);

    public void send(String topic, String message) {
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, message);
        sendCallBack(listenableFuture);
    }

    static void sendCallBack(ListenableFuture<SendResult<String, String>> listenableFuture) {
        try {
            //消息同步
            //SendResult<String, String> sendResult = listenableFuture.get();
            listenableFuture.addCallback(
                    SuccessCallback -> {
                        logger.info("kafka Producer发送消息成功！");
                    },
                    FailureCallback ->
                            logger.error("kafka Producer发送消息失败！"));
        } catch (Exception e) {
            logger.error("获取producer返回值失败", e);
        }
    }

}

