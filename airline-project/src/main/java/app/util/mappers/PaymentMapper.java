package app.util.mappers;

import app.dto.PaymentDto;
import app.entities.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    public Payment convertToPaymentEntity(PaymentDto paymentDto){
        Payment payment = new Payment();
        payment.setId(paymentDto.getId());
        payment.setPaymentState(paymentDto.getPaymentState());
        payment.setBookingsId(paymentDto.getBookingsId());
        return payment;
    }

}
