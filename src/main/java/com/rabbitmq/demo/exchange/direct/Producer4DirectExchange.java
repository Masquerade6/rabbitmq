package com.rabbitmq.demo.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer4DirectExchange {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.通过连接工厂创建连接对象
        Connection connection = connectionFactory.newConnection();

        //3.通过连接对象创建通道
        Channel channel = connection.createChannel();

        //4.声明
        String exchangeName = "test_direct_exchange";
        String routingKey = "test.direct";

        //5.发送
        String msg = "Hello World RabbitMQ 4 Direct Exchange Message ...";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        //6.关闭连接
        channel.close();
        connection.close();
    }
}
