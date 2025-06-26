package com.ticketing.payment_service.kafka

import com.ticketing.payment_service.dto.PaymentResultMessage
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

/**
 * 결제 처리 결과 메시지를 Kafka로 발행(Produce)하는 클래스
 */
@Component
class PaymentKafkaProducer (
    private val kafkaTemplate: KafkaTemplate<String, PaymentResultMessage>
) {
    //결제 결과 메시지를 보낼 새로운 토픽을 정의합니다.
    private val TOPIC_NAME = "payment-completed"

    /**
     * '결제 완료' 메시지를 Kafka로 전송합니다.
     */
    fun sendPaymentResultMessage(orderId:Long, paymentId: String) {
        val message = PaymentResultMessage(
            orderId = orderId,
            success =  true,
            paymentId = paymentId
        )
        kafkaTemplate.send(TOPIC_NAME, message)
    }
}