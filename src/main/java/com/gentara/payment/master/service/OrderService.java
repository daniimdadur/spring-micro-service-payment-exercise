package com.gentara.payment.master.service;

import com.gentara.payment.master.model.callback.PaymentCallbackRes;
import com.gentara.payment.master.model.entity.PaymentEntity;

public interface OrderService {
    PaymentCallbackRes callbackToOrderService(PaymentEntity paymentEntity);
}
