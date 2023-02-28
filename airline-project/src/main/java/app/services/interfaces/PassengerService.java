package app.services.interfaces;

import app.entities.account.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PassengerService {
    Passenger save(Passenger passenger);

    Passenger update(Long id, Passenger passenger);

    Page<Passenger> findAll(Pageable pageable);

    Passenger findById(Long id);

    Passenger findBySerialNumberPassport(String serialNumberPassport);

    Passenger findByEmail(String email);

    List<Passenger> findByLastName(String lastName);

    List<Passenger> findByFullName(String fullName);

    List<Passenger> findByAnyName(String name);

    void deleteById(Long id);
}
