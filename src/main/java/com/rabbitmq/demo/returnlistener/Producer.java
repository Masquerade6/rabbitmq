package com.rabbitmq.demo.returnlistener;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接工厂:ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.通过工厂获取连接对象
        Connection connection = connectionFactory.newConnection();

        //3.通过连接对象获取通道
        Channel channel = connection.createChannel();

        String exchange = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "abc.save";

        String msg = "Hello RabbitMQ Return Listener";

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("--handle return ---");
                System.out.println("replyCode : " + replyCode);
                System.out.println("replyText : " + replyText);
                System.out.println("exchange : " + exchange);
                System.out.println("properties : " + properties);
                System.out.println("body : " + new String(body));
            }
        });

//        channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());

        channel.basicPublish(exchange, routingKeyError, true, null, msg.getBytes());
    }
}
