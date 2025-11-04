package com.example.paymentservice.paymentgateways;

import com.razorpay.PaymentLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.json.JSONObject;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Component
public class RazorpayPaymentGateway implements IPaymentGateway {

    @Autowired
    private RazorpayClient razorpayClient;

    @Override
    public String getPaymentLink(
            Long amount,
            String orderId,
            String phoneNum,
            String customerName

    ){
try {
    RazorpayClient razorpay = razorpayClient;
    JSONObject paymentLinkRequest = new JSONObject();
    paymentLinkRequest.put("amount",1000);
    paymentLinkRequest.put("currency","INR");
    paymentLinkRequest.put("accept_partial",true);
    paymentLinkRequest.put("first_min_partial_amount",100);
    paymentLinkRequest.put("expire_by",1691097057);
    paymentLinkRequest.put("reference_id",orderId);
    paymentLinkRequest.put("description","Payment for policy no #23456");
    JSONObject customer = new JSONObject();
    customer.put("name",phoneNum);
    customer.put("contact",customerName);
    customer.put("email","gaurav.kumar@example.com");
    paymentLinkRequest.put("customer",customer);
    JSONObject notify = new JSONObject();
    notify.put("sms",true);
    notify.put("email",true);
    paymentLinkRequest.put("notify",notify);
    paymentLinkRequest.put("reminder_enable",true);
    JSONObject notes = new JSONObject();
    notes.put("policy_name","Life Insurance Policy");
    paymentLinkRequest.put("notes",notes);
    paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
    paymentLinkRequest.put("callback_method","get");

    PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
    return payment.get("short_url").toString();
}
catch (Exception e){
    throw new RuntimeException(e.getMessage());
}


    }
}
