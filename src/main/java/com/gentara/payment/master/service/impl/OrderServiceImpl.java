package com.gentara.payment.master.service.impl;

import com.gentara.payment.base.Response;
import com.gentara.payment.master.client.OrderClient;
import com.gentara.payment.master.model.callback.PaymentCallbackReq;
import com.gentara.payment.master.model.callback.PaymentCallbackRes;
import com.gentara.payment.master.model.entity.PaymentEntity;
import com.gentara.payment.master.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderClient orderClient;

    @Override
    public PaymentCallbackRes callbackToOrderService(PaymentEntity paymentEntity) {
        try {
            ResponseEntity<@NonNull Response<PaymentCallbackRes>> response = orderClient.payOrder(mapPaymentEntityToRes(paymentEntity));
            assert response.getBody() != null;
            return response.getBody().data();
        } catch (Exception e) {
            throw new RuntimeException("Payment creation failed", e);
        }
    }

    private PaymentCallbackReq mapPaymentEntityToRes(PaymentEntity paymentEntity) {
        return PaymentCallbackReq.builder()
                .orderNumber(paymentEntity.getOrderNumber())
                .paymentNumber(paymentEntity.getPaymentNumber())
                .paymentStatus(paymentEntity.getPaymentStatus())
                .paidAt(paymentEntity.getPaidAt())
                .build();
    }
}
