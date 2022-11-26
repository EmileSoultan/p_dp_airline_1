package app.services.interfaces;

import app.entities.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    Booking save(Booking book);

    List<Booking> findAll();

    Booking findById(Long id);

    List<Booking> getAllBooksForEmailNotification(LocalDateTime departureIn, LocalDateTime gap);

    void deleteById(Long id);
}
