package com.rabbitmq.demo.exchange.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer4FanoutExchange {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //是否支持自动重连
        connectionFactory.setAutomaticRecoveryEnabled(true);
        //自动重连间隔:针对于MQ集群网络跳动自动重连的设置
        connectionFactory.setNetworkRecoveryInterval(3000);

        //2.通过连接工厂创建连接对象
        Connection connection = connectionFactory.newConnection();

        //3.通过连接对象创建通道
        Channel channel = connection.createChannel();

        //4.声明
        String exchangeName = "test_fanout_exchange";
        String exchangeType = "fanout";
        String queueName = "test_fanout_queue";
        String routingKey = "";//不设置路由键

        //5.声明交换机
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        //6.声明队列
        channel.queueDeclare(queueName, false, false, false, null);
        //7.建立队列和交换机的绑定关系
        channel.queueBind(queueName, exchangeName, routingKey);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        };

        channel.basicConsume(queueName, true, defaultConsumer);
    }
}
