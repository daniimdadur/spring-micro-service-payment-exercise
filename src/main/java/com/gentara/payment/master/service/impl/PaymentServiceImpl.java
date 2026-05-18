package com.gentara.payment.master.service.impl;

import com.gentara.payment.enums.PaymentStatus;
import com.gentara.payment.exception.BadRequestException;
import com.gentara.payment.exception.InvoiceExpiredException;
import com.gentara.payment.exception.NotFoundException;
import com.gentara.payment.master.model.callback.PaymentCallbackRes;
import com.gentara.payment.master.model.entity.PaymentEntity;
import com.gentara.payment.master.model.request.PayPaymentReq;
import com.gentara.payment.master.model.request.PaymentReq;
import com.gentara.payment.master.model.response.PaymentRes;
import com.gentara.payment.master.repository.PaymentRepository;
import com.gentara.payment.master.service.OrderService;
import com.gentara.payment.master.service.PaymentService;
import com.gentara.payment.util.CommonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Override
    public PaymentRes createInvoice(PaymentReq request) {
        if (paymentRepository.findByIdempotencyKey(request.getIdempotencyKey()).isPresent()) {
            throw new BadRequestException("Payment already exists for this order");
        }

        PaymentEntity result = mapToEntity(request);

        try {
            this.paymentRepository.save(result);
            return mapToResponse(result);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create payment");
        }
    }

    @Transactional
    @Override
    public PaymentCallbackRes payInvoice(String paymentNumber, PayPaymentReq request) {
        PaymentEntity paymentEntity = this.getPaymentByPaymentNumber(paymentNumber);
        validatePayment(paymentEntity);
        if (request.getPaidAmount().compareTo(paymentEntity.getAmount()) >= 0) {
            paymentEntity.setPaidAt(request.getPaidAt());
            paymentEntity.setPaymentStatus(PaymentStatus.PAID);
            paymentEntity.setExpiredAt(null);
        } else {
            throw new BadRequestException("Paid amount is less than the amount to be paid");
        }
        try {
            PaymentCallbackRes paymentCallbackRes = orderService.callbackToOrderService(paymentEntity);
            this.paymentRepository.save(paymentEntity);
            return paymentCallbackRes;
        } catch (Exception e) {
            throw new RuntimeException("Failed to pay payment");
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

    private PaymentEntity getPaymentByPaymentNumber(String paymentNumber) {
        return this.paymentRepository.findByPaymentNumber(paymentNumber)
                .orElseThrow(() -> new NotFoundException(String.format("Payment not found for payment number: %s", paymentNumber)));
    }

    private void validatePayment(PaymentEntity paymentEntity) {
        if (paymentEntity.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new InvoiceExpiredException("Payment has expired");
        }
    }
}
