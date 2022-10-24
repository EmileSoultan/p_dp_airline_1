package app.services;

import app.entities.Passenger;

import java.util.List;

public interface PassengerService {
    Passenger save(Passenger passenger);

    List<Passenger> findAll();

    Passenger findById(Long id);

    Passenger findBySerialNumberPassport(String serialNumberPassport);

    Passenger findByEmail(String email);

    List<Passenger> findByLastName(String lastName);

    List<Passenger> findByFullName(String fullName);

    List<Passenger> findByAnyName(String name);

    void deleteById(Long id);

}
