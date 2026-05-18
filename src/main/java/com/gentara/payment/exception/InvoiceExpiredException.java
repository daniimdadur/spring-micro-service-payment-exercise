package com.gentara.payment.exception;

public class InvoiceExpiredException extends RuntimeException {
    public InvoiceExpiredException(String message) {
        super(message);
    }
}