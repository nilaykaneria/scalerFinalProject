package com.example.paymentservice.paymentgateways;

public interface IPaymentGateway {

    String getPaymentLink(Long amount, String orderId, String phoneNum, String customerName);
}
