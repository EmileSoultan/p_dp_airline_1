package app.services;

import app.entities.Booking;
import app.repositories.BookingRepository;
import app.services.interfaces.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

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

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> getAllBooksForEmailNotification(LocalDateTime departureIn, LocalDateTime gap) {
        return bookingRepository.getAllBooksForEmailNotification(departureIn, gap);
    }
}
