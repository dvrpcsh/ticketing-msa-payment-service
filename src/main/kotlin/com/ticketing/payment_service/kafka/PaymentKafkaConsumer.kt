package com.ticketing.payment_service.kafka

import com.ticketing.payment_service.dto.OrderCreationMessage
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

/**
 * Kafka로부터 메시지를 수신(Consume)하여 결제 관련 처리를 시작하는 클래스
 */
@Component
class PaymentKafkaConsumer {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 'order-created' 토픽을 구독하고 있다가 메시지가 도착하면 메서드 실행
     *
     * 흐름
     * 1.@KafkaListener 어노테이션이 붙어있어, 스프링이 이 메서드를 해당 토픽의 메시지 수신기로 자동 등록합니다.
     * 2.메시지가 도착하면, 그 내용(payload)이 OrderCreationMessage 객체로 자동 변환되어 파라미터로 들어옵니다.
     * 3.(지금)수신한 주문 정보를 로그에 출력하여, 메시지가 잘 도착했는지 확인
     * 4.(추후)이 곳에서 실제 결제 처리 로직(PG 연동 등)을 호출
     */
    @KafkaListener(topics = ["order-created"], groupId = "payment-group")
    fun handleOrderCreation(message: OrderCreationMessage) {
        logger.info("결제 시스템: '주문 생성'메시지를 수신했습니다. 결제 처리를 시작합니다. >> ${message}")
        //TODO: 결제로직 구현
    }
}