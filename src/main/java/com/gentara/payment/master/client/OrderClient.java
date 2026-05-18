package com.gentara.payment.master.client;

import com.gentara.payment.base.Response;
import com.gentara.payment.master.model.callback.PaymentCallbackReq;
import com.gentara.payment.master.model.callback.PaymentCallbackRes;
import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "${feign.client.order-service.url}")
public interface OrderClient {
    @PostMapping("/order/payment-callback")
    ResponseEntity<@NonNull Response<PaymentCallbackRes>> payOrder(@RequestBody PaymentCallbackReq request);
}
