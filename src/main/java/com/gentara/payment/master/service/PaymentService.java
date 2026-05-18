package com.gentara.payment.master.service;

import com.gentara.payment.master.model.callback.PaymentCallbackRes;
import com.gentara.payment.master.model.request.PayPaymentReq;
import com.gentara.payment.master.model.request.PaymentReq;
import com.gentara.payment.master.model.response.PaymentRes;

public interface PaymentService {
    PaymentRes createInvoice(PaymentReq request);
    PaymentCallbackRes payInvoice(String paymentNumber, PayPaymentReq request);
}
