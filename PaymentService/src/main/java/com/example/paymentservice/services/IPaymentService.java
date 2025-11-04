package com.example.paymentservice.services;

public interface IPaymentService {
    String initiatePayment(Long amount, String orderId, String phoneNum, String customerName);
}
