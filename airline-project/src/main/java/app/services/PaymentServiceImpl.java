package app.services;

import app.clients.PaymentFeignClient;
import app.entities.Booking;
import app.entities.Payment;
import app.repositories.PaymentRepository;
import app.services.interfaces.BookingService;
import app.services.interfaces.PaymentService;
import app.util.mappers.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingService bookingService;
    private final PaymentFeignClient feignClientPayment;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public Payment savePayment(Payment payment) {
        payment.getBookingsId().forEach(b -> {
            Booking bookingFromDb = bookingService.findById(b);
            if (bookingFromDb == null) {
                throw new NoSuchElementException(String.format("booking with id=%d not exists", b));
            }
        });
        return paymentRepository.save(paymentMapper.convertToPaymentEntity(feignClientPayment.savePayment(payment)));
    }

    @Override
    public List<Payment> findAllPayments(){
        return paymentRepository.findAll();
    }

    @Override
    public Payment findPaymentById(long id){
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Payment> pagePagination(int page, int count) {
        return paymentRepository.findAll(PageRequest.of(page, count));
    }

}
