package com.rabbitmq.demo.quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
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

        String queueName = "test001";

        //4.声明(创建)一个队列:
        //  队列名称
        //  是否持久化(即使服务器重启消息也不会消失)
        //  独占: 为true只有当前连接可以消费,如果队列里面有很多消息需要顺序消费,由于集群模式一般会有负载均衡,
        //       如果有多个节点可能会导致消息出现在多个节点上消费,无法保证消费的顺序
        //  是否自动删除:如果队列与其exchange没有(脱离)绑定关系了,会自动删除
        //  扩展参数:
        channel.queueDeclare(queueName, true,  false, false, null);

        //5.创建消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("收到消息: " + new String(body));
            }
        };

        //6.绑定消费者与消息队列
        String consumeRes = channel.basicConsume(queueName, true, defaultConsumer);

        System.out.println(consumeRes);
    }
}
