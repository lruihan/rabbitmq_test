package com.fdu.rissy.pubsub;

import com.fdu.rissy.util.QueueUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lins13
 * @date 10/31/17 5:58 PM
 **/
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    private static final Logger logger = LoggerFactory.getLogger(EmitLog.class);

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String message = QueueUtil.getMessage(args);

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());

        logger.info(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
