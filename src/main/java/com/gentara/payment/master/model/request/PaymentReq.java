package com.gentara.payment.master.model.request;

import com.gentara.payment.enums.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentReq {
    private String orderId;
    private String orderNumber;
    private String customerId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private String idempotencyKey;
    private String callBackUrl;
    private String notes;
}
