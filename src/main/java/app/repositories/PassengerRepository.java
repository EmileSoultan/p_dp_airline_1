package app.repositories;

import app.entities.user.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.Optional;


public interface PassengerRepository extends JpaRepository<Passenger, Long> {


    Page<Passenger> findAll(Pageable pageable);

    Optional<Passenger> findByPassport_serialNumberPassport(String serialNumberPassport);

    List<Passenger> findByLastName(String lastName);

    List<Passenger> findByFirstName(String firstName);

    @Query(value = "SELECT passengers from Passenger passengers WHERE passengers.passport.middleName = ?1")
    List<Passenger> findByMiddleName(String middleName);

    Passenger findByEmail(String email);
}
