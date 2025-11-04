package com.example.paymentservice.paymentgateways;


import com.stripe.Stripe;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements  IPaymentGateway {

    @Value("${stripe.apikey}")
    private String stripeApiKey;
//    private String stripeApiSecret;

    @Override
    public String getPaymentLink(Long amount, String orderId, String phoneNum, String customerName) {

        try {
            Stripe.apiKey = stripeApiKey;

            Price price = getPrice(amount); //API CALL FIRST THEN USE THE PRICE BELOW
            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice(price.getId())
                                            .setQuantity(1L)
                                            .build()
                            )//NOW WE NEED TO CREATE A CALL BACK URL, MUST HAPPEN BEFORE BUILD
                            .setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                    .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                            .setUrl("http://scaler.com")
                                            .build())
                                    .build()
                            )
                            .build();
            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //WE ARE MAKINGN A API CALL HERE
    // THIS HAPPENS FIRST
    //THEN WE MAKE THE COMPLETE PAYMENT AFTER GETTING THE ENCODED PRICE VALUE
    private Price getPrice(Long amount) {
        try {
            PriceCreateParams params =
                    PriceCreateParams.builder()
                            .setCurrency("usd")
                            .setUnitAmount(amount)
                            .setRecurring(
                                    PriceCreateParams.Recurring.builder()
                                            .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                            .build()
                            )
                            .setProductData(
                                    PriceCreateParams.ProductData.builder().setName("Netflix Plan").build()
                            )
                            .build();
            Price price = Price.create(params);
            return price;
        }
        catch (Exception e) {
            throw  new RuntimeException(e.getMessage());
        }
    }
}
