package app.services;

import app.entities.Booking;
import app.repositories.BookingRepository;
import app.services.interfaces.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    @Override
    public Booking save(Booking book) {
        return bookingRepository.save(book);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> getAllBooksForEmailNotification(LocalDateTime departureIn, LocalDateTime gap) {
        return bookingRepository.getAllBooksForEmailNotification(departureIn, gap);
    }

    @Override
    public Booking findByBookingNumber(String number) {
        return bookingRepository.findByBookingNumber(number).orElse(null);
    }
}
