package app.services.interfaces;

import app.entities.account.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PassengerService {
    Passenger save(Passenger passenger);

    Passenger update(Passenger passenger);

    Page<Passenger> findAllByKeyword(Pageable pageable, String firstName, String lastName, String email, String serialNumberPassport);

    Optional<Passenger> findById(Long id);

    void deleteById(Long id);

    Page<Passenger> findAll (Pageable pageable);

    Page<Passenger> findAll(int page, int size);
}