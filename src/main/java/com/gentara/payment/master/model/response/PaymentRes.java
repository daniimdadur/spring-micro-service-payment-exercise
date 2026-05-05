package com.gentara.payment.master.model.response;

import com.gentara.payment.enums.PaymentMethod;
import com.gentara.payment.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRes {
    private String id;
    private String paymentNumber;
    private String orderId;
    private String orderNumber;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
}
