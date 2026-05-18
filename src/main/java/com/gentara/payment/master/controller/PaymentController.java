package com.gentara.payment.master.controller;

import com.gentara.payment.base.BaseController;
import com.gentara.payment.base.Response;
import com.gentara.payment.master.model.callback.PaymentCallbackRes;
import com.gentara.payment.master.model.request.PayPaymentReq;
import com.gentara.payment.master.model.request.PaymentReq;
import com.gentara.payment.master.model.response.PaymentRes;
import com.gentara.payment.master.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/api/payment")
@RequiredArgsConstructor
public class PaymentController extends BaseController<PaymentRes> {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Response> createPayment(@RequestBody PaymentReq request) {
        PaymentRes response = paymentService.createInvoice(request);
        return super.getResponse(response);
    }

    @PostMapping("/pay/{payment-number}")
    public ResponseEntity<Response> payOrder(@PathVariable("payment-number") String paymentNumber,
                                             @RequestBody PayPaymentReq request) {
        PaymentCallbackRes response = paymentService.payInvoice(paymentNumber, request);
        return super.getResponse(response);
    }
}
