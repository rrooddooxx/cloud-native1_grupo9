package com.grupo9.promotionsmscn1grupo9.config;

import com.grupo9.promotionsmscn1grupo9.sales.SaleFinishedEvent;
import com.grupo9.promotionsmscn1grupo9.stock.StockUpdatedEvent;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaConfig {

  private final KafkaProperties kafkaProperties;

  @Bean
  public Map<String, Object> saleFinishedEventConsumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        String.join(",", kafkaProperties.getConsumer().getBootstrapServers()));
    props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.grupo9.promotionsmscn1grupo9.sales");
    props.put(
        JsonDeserializer.VALUE_DEFAULT_TYPE,
        "com.grupo9.promotionsmscn1grupo9.sales.SaleFinishedEvent");
    return props;
  }

  @Bean
  public ConsumerFactory<String, SaleFinishedEvent> saleFinishedEventConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(saleFinishedEventConsumerConfigs());
  }

  @Bean
  KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SaleFinishedEvent>>
      saleFinishedEventKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, SaleFinishedEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(saleFinishedEventConsumerFactory());
    factory.setConcurrency(3);
    factory.getContainerProperties().setPollTimeout(3000);
    factory.setCommonErrorHandler(kafkaErrorHandler());
    return factory;
  }

  @Bean
  public Map<String, Object> stockUpdatedEventConsumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        String.join(",", kafkaProperties.getConsumer().getBootstrapServers()));
    props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.grupo9.promotionsmscn1grupo9.stock");
    props.put(
        JsonDeserializer.VALUE_DEFAULT_TYPE,
        "com.grupo9.promotionsmscn1grupo9.stock.StockUpdatedEvent");
    return props;
  }

  @Bean
  public ConsumerFactory<String, StockUpdatedEvent> stockUpdatedEventConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(stockUpdatedEventConsumerConfigs());
  }

  @Bean
  KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, StockUpdatedEvent>>
      stockUpdatedEventKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, StockUpdatedEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(stockUpdatedEventConsumerFactory());
    factory.setConcurrency(3);
    factory.getContainerProperties().setPollTimeout(3000);
    factory.setCommonErrorHandler(kafkaErrorHandler());
    return factory;
  }

  @Bean
  public DefaultErrorHandler kafkaErrorHandler() {
    DefaultErrorHandler handler =
        new DefaultErrorHandler(
            (consumerRecord, exception) ->
                log.warn(
                    "🛑 Deserialization failed for record at offset {}: Topic: {}: Error: {}",
                    consumerRecord.offset(),
                    consumerRecord.topic(),
                    exception.getMessage()),
            new FixedBackOff(0L, 0));

    handler.addNotRetryableExceptions(RecordDeserializationException.class);

    return handler;
  }
}
