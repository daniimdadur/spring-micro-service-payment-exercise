package com.gentara.payment.master.controller;

import com.gentara.payment.base.BaseController;
import com.gentara.payment.base.Response;
import com.gentara.payment.master.model.request.PaymentReq;
import com.gentara.payment.master.model.response.PaymentRes;
import com.gentara.payment.master.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
