package com.gentara.payment.master.model.response;

import com.gentara.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayPaymentRes {
    private String paymentNumber;
    private PaymentStatus paymentStatus;
    private String referenceNumber;
    private LocalDateTime paidAt;
}
