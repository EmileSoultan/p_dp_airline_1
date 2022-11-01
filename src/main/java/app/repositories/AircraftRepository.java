package app.repositories;

import app.entities.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    Aircraft findByAircraftNumber(String aircraftNumber);

    @Query(
            value = "SELECT aircraft FROM Aircraft aircraft LEFT JOIN FETCH aircraft.seatList WHERE aircraft.id > 0"
    )
    List<Aircraft> findAll();
}
