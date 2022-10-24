package app.services;

import app.entities.Passport;

import java.util.List;
import java.util.Optional;

public interface PassportService {

    Passport save(Passport passport);

    Passport findById(Long id);


    Optional<Passport> findBySerialNumberPassport(String serialNumberPassport);

    void deleteById(Long id);

    List<Passport> findAll();


}
