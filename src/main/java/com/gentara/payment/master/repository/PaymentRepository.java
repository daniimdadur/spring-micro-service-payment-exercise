package com.gentara.payment.master.repository;

import com.gentara.payment.master.model.entity.PaymentEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<@NonNull PaymentEntity, @NonNull String> {
    Optional<PaymentEntity> findByPaymentNumber(String paymentNumber);
    Optional<PaymentEntity> findByOrderId(String orderId);
    Optional<PaymentEntity> findByIdempotencyKey(String idempotencyKey);
}
