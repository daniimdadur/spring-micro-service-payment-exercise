package com.gentara.payment.master.model.callback;

import com.gentara.payment.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCallbackReq {
    private String orderNumber;
    private String paymentNumber;
    private PaymentStatus paymentStatus;
    private LocalDateTime paidAt;
}
