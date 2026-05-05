package com.gentara.payment.master.service;

import com.gentara.payment.master.model.request.PaymentReq;
import com.gentara.payment.master.model.response.PaymentRes;

public interface PaymentService {
    PaymentRes createInvoice(PaymentReq request);
}
