package app.util.mappers;

import app.dto.PaymentRequest;
import app.dto.PaymentResponse;
import app.entities.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    public Payment convertToPaymentEntity(PaymentResponse paymentDto) {
        var payment = new Payment();
        payment.setId(paymentDto.getId());
        payment.setPaymentState(paymentDto.getPaymentState());
        payment.setBookingsId(paymentDto.getBookingsId());
        payment.setPrice(paymentDto.getPrice());
        payment.setCurrency(paymentDto.getCurrency());
        return payment;
    }

    public Payment convertToPaymentEntity(PaymentRequest paymentRequest) {
        var payment = new Payment();
        payment.setId(paymentRequest.getId());
        payment.setPaymentState(paymentRequest.getPaymentState());
        payment.setBookingsId(paymentRequest.getBookingsId());
        payment.setPrice(paymentRequest.getPrice());
        payment.setCurrency(paymentRequest.getCurrency());
        return payment;
    }

    public PaymentResponse convertToDto(Payment payment) {
        var paymentResponse = new PaymentResponse();
        paymentResponse.setId(payment.getId());
        paymentResponse.setPaymentState(payment.getPaymentState());
        paymentResponse.setBookingsId(payment.getBookingsId());
        paymentResponse.setCurrency(payment.getCurrency());
        paymentResponse.setPrice(payment.getPrice());
        return paymentResponse;
    }
}
