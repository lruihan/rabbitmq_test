package com.fdu.rissy.workqueues;

import com.fdu.rissy.util.QueueUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    private static final Logger logger = LoggerFactory.getLogger(NewTask.class);

    public static void main(String[] argv) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //durable queue
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        String message = QueueUtil.getMessage(argv);
        //durable message by using PERSISTENT_TEXT_PLAIN
        channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        logger.info(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
