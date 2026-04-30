package com.gentara.payment.master.controller;

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
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Response<PaymentRes>> createPayment(@RequestBody PaymentReq request) {
        PaymentRes response = paymentService.createPayment(request);
        Response<PaymentRes> res = Response.<PaymentRes>builder()
                .status(200)
                .message("Payment created successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(res);
    }
}
