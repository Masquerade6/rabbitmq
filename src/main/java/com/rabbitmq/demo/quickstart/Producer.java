package com.rabbitmq.demo.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

        //4.通过channel发送数据
        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ " + i;
            channel.basicPublish("", "test001", null, msg.getBytes());
        }

        //5.关闭连接
        channel.close();
        connection.close();
    }
}
