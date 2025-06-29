package com.ticketing.payment_service.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig {

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val props = mutableMapOf<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "payment-group-v2"

        // 이 그룹이 처음 토픽을 읽거나, 이전에 읽었던 위치(오프셋)를 찾을 수 없을 때,
        // 무조건 가장 최신 메시지부터 읽도록 설정합니다.
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "latest"

        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[JsonDeserializer.TRUSTED_PACKAGES] = "*"
        props[JsonDeserializer.TYPE_MAPPINGS] =
            "com.ticketing.ticketing_poc.domain.order.dto.OrderCreationMessage:com.ticketing.payment_service.dto.OrderCreationMessage"

        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}
