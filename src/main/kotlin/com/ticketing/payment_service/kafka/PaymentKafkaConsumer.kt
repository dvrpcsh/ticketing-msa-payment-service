package com.ticketing.payment_service.kafka

import com.ticketing.payment_service.dto.OrderCreationMessage
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * Kafka로부터 메시지를 수신(Consume)하여 결제를 처리하고, 그 결과를 다시 발행(Produce)하는 클래스
 */
@Component
class PaymentKafkaConsumer (
    //메시지 발송을 위한 Producer 주입
    private val paymentKafkaProducer: PaymentKafkaProducer
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 'order-created' 토픽을 구독하고 있다가 메시지가 도착하면 메서드 실행
     *
     * 흐름
     * 1.@KafkaListener 어노테이션이 붙어있어, 스프링이 이 메서드를 해당 토픽의 메시지 수신기로 자동 등록합니다.
     * 2.메시지가 도착하면, 그 내용(payload)이 OrderCreationMessage 객체로 자동 변환되어 파라미터로 들어옵니다.
     * 3.(지금)수신한 주문 정보를 로그에 출력하여, 메시지가 잘 도착했는지 확인
     * 4.(추후)이 곳에서 실제 결제 처리 로직(PG 연동 등)을 호출
     * 5.결제 성공 시 성공에 대한 메시지를 'payment-completed' 토픽으로 재발행
     */
    @KafkaListener(topics = ["order-created-v2"], groupId = "payment-group-v2")
    fun handleOrderCreation(message: OrderCreationMessage) {
        logger.info("결제 시스템: '주문 생성'메시지를 수신했습니다. 결제 처리를 시작합니다. >> ${message}")

        //TODO: 결제로직 구현
        logger.info("결제 처리 중...")
        Thread.sleep(1000)

        val paymentId = UUID.randomUUID().toString() //가상의 결제ID 생성
        logger.info("결제 성공! (Payment ID: ${paymentId}")

        //'결제 완료' 메시지 발행
        paymentKafkaProducer.sendPaymentResultMessage(
            orderId = message.orderId,
            paymentId = paymentId,
            productId =  message.productId,
            seatId =  message.seatId
        )
        logger.info("'결제 완료' 메시지를 Kafka로 발행하였습니다.")
    }
}