package app.repositories;

import app.entities.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    Aircraft findByAircraftNumber(String aircraftNumber);
}
