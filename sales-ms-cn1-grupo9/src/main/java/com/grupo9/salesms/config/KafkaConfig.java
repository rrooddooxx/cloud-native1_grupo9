package com.grupo9.salesms.config;

import com.grupo9.salesms.sales.Sale;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

  private final KafkaProperties properties;

  @Bean
  public DefaultKafkaProducerFactory<String, Sale> saleFinishedKafkaListenerContainerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
    props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
    props.put(JsonSerializer.TYPE_MAPPINGS, "sale:" + Sale.class.getName());
    return new DefaultKafkaProducerFactory<>(props);
  }

  @Bean
  public KafkaTemplate<String, Sale> saleFinishedKafkaTemplate() {
    return new KafkaTemplate<>(saleFinishedKafkaListenerContainerFactory());
  }

  @Bean
  public NewTopic saleFinishedTopic() {
    return TopicBuilder.name(properties.getSaleFinishedTopic())
        .partitions(3)
        .config(TopicConfig.RETENTION_MS_CONFIG, "604800000")
        .replicas(2)
        .compact()
        .build();
  }
}
