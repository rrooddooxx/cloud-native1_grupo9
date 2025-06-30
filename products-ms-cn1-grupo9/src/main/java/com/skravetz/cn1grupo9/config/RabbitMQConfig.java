package com.skravetz.cn1grupo9.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Sales Queue Configuration
    public static final String SALES_QUEUE = "sales.queue";
    public static final String SALES_EXCHANGE = "sales.exchange";
    public static final String SALES_ROUTING_KEY = "sales.routing.key";

    // Price Change Queue Configuration
    public static final String PRICE_CHANGE_QUEUE = "price.change.queue";
    public static final String PRICE_CHANGE_EXCHANGE = "price.change.exchange";
    public static final String PRICE_CHANGE_ROUTING_KEY = "price.change.routing.key";

    @Bean
    public Queue salesQueue() {
        return QueueBuilder.durable(SALES_QUEUE).build();
    }

    @Bean
    public Queue priceChangeQueue() {
        return QueueBuilder.durable(PRICE_CHANGE_QUEUE).build();
    }

    @Bean
    public TopicExchange salesExchange() {
        return new TopicExchange(SALES_EXCHANGE);
    }

    @Bean
    public TopicExchange priceChangeExchange() {
        return new TopicExchange(PRICE_CHANGE_EXCHANGE);
    }

    @Bean
    public Binding salesBinding() {
        return BindingBuilder
                .bind(salesQueue())
                .to(salesExchange())
                .with(SALES_ROUTING_KEY);
    }

    @Bean
    public Binding priceChangeBinding() {
        return BindingBuilder
                .bind(priceChangeQueue())
                .to(priceChangeExchange())
                .with(PRICE_CHANGE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}