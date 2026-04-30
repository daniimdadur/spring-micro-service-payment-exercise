package com.gentara.payment.master.model.response;

import com.gentara.payment.enums.PaymentMethod;
import com.gentara.payment.enums.Status;
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
public class PaymentRes {
    private String id;
    private String paymentNumber;
    private String orderId;
    private String orderNumber;
    private String customerId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private Status status;
    private String notes;
    private String referenceNumber;
    private LocalDateTime paidAt;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
}
