package com.paymentService.paymentService.service;

import com.paymentService.paymentService.entity.TransactionDetails;
import com.paymentService.paymentService.model.PaymentMode;
import com.paymentService.paymentService.model.PaymentRequest;
import com.paymentService.paymentService.model.PaymentResponse;
import com.paymentService.paymentService.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceimpl implements  PaymentService{

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
    @Override
    public long doPayment(PaymentRequest request) {
        log.info("dopayment");
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(request.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .referenceNumber(request.getReferenceNumber())
                .amount(request.getAmount())
                .build();
        transactionDetailsRepository.save(transactionDetails);
        log.info("transaction completed for id: {}", transactionDetails.getId());
        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(Long.valueOf(orderId));
        log.info("transactionDetails is " +transactionDetails);
        log.info("orderId is " +Long.valueOf(orderId));
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentDate(transactionDetails.getPaymentDate())
                .amount(transactionDetails.getAmount())
                .status(transactionDetails.getPaymentStatus())
                .paymentId(transactionDetails.getId())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .build();

        return paymentResponse;
    }
}
