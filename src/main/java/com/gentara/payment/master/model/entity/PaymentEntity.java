package com.gentara.payment.master.model.entity;

import com.gentara.payment.base.BaseAuditableSoftDelete;
import com.gentara.payment.enums.PaymentMethod;
import com.gentara.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE t_payments SET deleted_at=CURRENT_TIMESTAMP WHERE id=?")
@Table(name = "t_payments")
public class PaymentEntity extends BaseAuditableSoftDelete {

    @Id
    @Column
    private String id;

    @Column(name = "payment_number", unique = true, nullable = false)
    private String paymentNumber;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "idempotency_key", nullable = false)
    private String idempotencyKey;
}
