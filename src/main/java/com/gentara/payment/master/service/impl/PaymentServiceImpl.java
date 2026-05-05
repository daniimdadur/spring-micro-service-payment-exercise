package com.gentara.payment.master.service.impl;

import com.gentara.payment.enums.PaymentStatus;
import com.gentara.payment.master.model.entity.PaymentEntity;
import com.gentara.payment.master.model.request.PaymentReq;
import com.gentara.payment.master.model.response.PaymentRes;
import com.gentara.payment.master.repository.PaymentRepository;
import com.gentara.payment.master.service.PaymentService;
import com.gentara.payment.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentRes createInvoice(PaymentReq request) {
        if (paymentRepository.findByIdempotencyKey(request.getIdempotencyKey()).isPresent()) {
            throw new RuntimeException("Payment already exists for this order");
        }

        PaymentEntity result = mapToEntity(request);

        try {
            this.paymentRepository.save(result);
            return mapToResponse(result);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create payment");
        }
    }

    private PaymentRes mapToResponse(PaymentEntity paymentEntity) {
        return PaymentRes.builder()
                .id(paymentEntity.getId())
                .paymentNumber(paymentEntity.getPaymentNumber())
                .orderId(paymentEntity.getOrderId())
                .orderNumber(paymentEntity.getOrderNumber())
                .amount(paymentEntity.getAmount())
                .paymentMethod(paymentEntity.getPaymentMethod())
                .paymentStatus(paymentEntity.getPaymentStatus())
                .expiredAt(paymentEntity.getExpiredAt())
                .createdAt(paymentEntity.getCreatedAt())
                .build();
    }

    private PaymentEntity mapToEntity(PaymentReq request) {
        return PaymentEntity.builder()
                .id(CommonUtil.getUUID())
                .paymentNumber("PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .orderId(request.getOrderId())
                .orderNumber(request.getOrderNumber())
                .customerId(request.getCustomerId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .referenceNumber("REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .expiredAt(LocalDateTime.now().plusHours(1))
                .idempotencyKey(request.getIdempotencyKey())
                .build();
    }
}
