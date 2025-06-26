package com.ticketing.payment_service.dto

/**
 * 결제 처리 완료 후, 그 결과를 kafka로 발행할 때 사용하는 데이터 모델
 */
data class PaymentResultMessage(
    val orderId: Long,
    val success: Boolean, //결제 성공 여부
    val paymentId: String? = null, //결제 성공 시 부여되는 가상 결제 ID
    val reason: String? = null //결제 실패 시 사유
)
