package com.ticketing.payment_service.dto

/**
 * Kafka로부터 수신한 '주문 생성' 메시지를 담는 데이터 모델
 * 반드시 보내는 쪽(ticketing-poc)의 DTO와 구조가 동일해야 함
 */
data class OrderCreationMessage(
    val orderId: Long,
    val userId: Long,
    val productId: Long,
    val seatId: String
)
