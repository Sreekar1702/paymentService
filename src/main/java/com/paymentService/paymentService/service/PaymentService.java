package com.paymentService.paymentService.service;

import com.paymentService.paymentService.model.PaymentRequest;
import com.paymentService.paymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest request);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
