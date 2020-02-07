package com.rabbitmq.demo.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer4FanoutExchange {
    public static void main(String[] args) throws Exception {
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
        String exchangeName = "test_fanout_exchange";

        //5.发送
        for (int i = 0; i < 10; i++) {
            String msg = "Hello World RabbitMQ 4 Fanout Exchange Message ...";
            channel.basicPublish(exchangeName, "sjfklas", null, msg.getBytes());
        }
        //6.关闭连接
        channel.close();
        connection.close();
    }
}
