package com.gentara.payment.master.service.impl;

import com.gentara.payment.enums.Status;
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
    public PaymentRes createPayment(PaymentReq request) {
        // Check if payment already exists for the order
        if (paymentRepository.findByOrderId(request.getOrderId()).isPresent()) {
            throw new RuntimeException("Payment already exists for this order");
        }

        // Generate payment number
        String paymentNumber = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Set expired at, e.g., 1 hour from now
        LocalDateTime expiredAt = LocalDateTime.now().plusHours(1);

        PaymentEntity paymentEntity = PaymentEntity.builder()
                .id(CommonUtil.getUUID())
                .paymentNumber(paymentNumber)
                .orderId(request.getOrderId())
                .orderNumber(request.getOrderNumber())
                .customerId(request.getCustomerId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .status(Status.PENDING)
                .notes(request.getNotes())
                .expiredAt(expiredAt)
                .build();

        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);

        return mapToResponse(savedPaymentEntity);
    }

    private PaymentRes mapToResponse(PaymentEntity paymentEntity) {
        return new PaymentRes(
                paymentEntity.getId(),
                paymentEntity.getPaymentNumber(),
                paymentEntity.getOrderId(),
                paymentEntity.getOrderNumber(),
                paymentEntity.getCustomerId(),
                paymentEntity.getAmount(),
                paymentEntity.getPaymentMethod(),
                paymentEntity.getStatus(),
                paymentEntity.getNotes(),
                paymentEntity.getReferenceNumber(),
                paymentEntity.getPaidAt(),
                paymentEntity.getExpiredAt(),
                paymentEntity.getCreatedAt()
        );
    }
}
