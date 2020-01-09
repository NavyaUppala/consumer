package com.capitalone.dept.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.capitalone.dept.consumer.deserializer.CustomerAvroDeserializer;
import com.capitalone.dept.consumer.model.Customer;


@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;

	public ConsumerFactory<String, Bytes> consumerFactory(String groupId) {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomerAvroDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, false);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		return new DefaultKafkaConsumerFactory<>(props);
	}

	public ConcurrentKafkaListenerContainerFactory<String, Bytes> kafkaListenerContainerFactory(String groupId) {
		ConcurrentKafkaListenerContainerFactory<String, Bytes> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory(groupId));
		  factory.setBatchListener(true);
		return factory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Bytes> custKafkaListenerContainerFactory() {
		return kafkaListenerContainerFactory("cust");
	}
}
