package com.grupo9.inventorymscn1grupo9.config;

import com.grupo9.inventorymscn1grupo9.sales.SaleEvent;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
@RequiredArgsConstructor
@Slf4j
public class KafkaConfig {

  private final KafkaTopicPropertiesConfig properties;
  private final KafkaServicePropertiesConfig serviceProperties;
  

  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        String.join(",", serviceProperties.getConsumer().getBootstrapServers()));
    props.put(ConsumerConfig.GROUP_ID_CONFIG, serviceProperties.getConsumer().getGroupId());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.grupo9.inventorymscn1grupo9.sales");
    props.put(
        JsonDeserializer.VALUE_DEFAULT_TYPE, "com.grupo9.inventorymscn1grupo9.sales.SaleEvent");
    return props;
  }

  @Bean
  public ConsumerFactory<String, SaleEvent> saleFinishedConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  @Bean
  KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SaleEvent>>
      saleFinishedKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, SaleEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(saleFinishedConsumerFactory());
    factory.setConcurrency(3);
    factory.getContainerProperties().setPollTimeout(3000);
    factory.setCommonErrorHandler(kafkaErrorHandler());
    return factory;
  }

  @Bean
  public NewTopic stockTopic() {
    return TopicBuilder.name(properties.getStockTopic())
        .partitions(3)
        .config(TopicConfig.RETENTION_MS_CONFIG, "604800000")
        .replicas(2)
        .compact()
        .build();
  }

  @Bean
  public DefaultErrorHandler kafkaErrorHandler() {
    DefaultErrorHandler handler =
        new DefaultErrorHandler(
            (consumerRecord, exception) ->
                log.warn(
                    "🛑 Deserialization failed for record at offset {}: {}",
                    consumerRecord.offset(),
                    exception.getMessage()),
            new FixedBackOff(0L, 0));

    handler.addNotRetryableExceptions(RecordDeserializationException.class);

    return handler;
  }
}
