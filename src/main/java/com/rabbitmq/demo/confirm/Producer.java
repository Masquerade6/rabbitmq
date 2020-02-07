package com.rabbitmq.demo.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class Producer {
    public static void main(String[] args) throws Exception {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.获取connection
        Connection connection = connectionFactory.newConnection();

        //3.通过连接对象创建通道
        Channel channel = connection.createChannel();

        //4.指定消息投递模式:消息的确认模式
        channel.confirmSelect();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        //5.发送消息
        String msg = "Hello RabbitMQ Send Confirm msg!";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        //6.添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {

            /**
             * 成功的回调方法
             * @param deliveryTag:消息的唯一标签
             * @param multiple:是否批量
             * @throws IOException
             */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(" --- ack --- ");
            }

            /**
             * 失败的回调方法
             * @param deliveryTag:消息的唯一标签
             * @param multiple:是否批量
             * @throws IOException
             */
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(" --- no ack --- ");
            }
        });

    }
}
