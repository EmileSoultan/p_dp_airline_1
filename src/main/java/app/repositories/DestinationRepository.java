package app.repositories;

import app.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByCityNameContainingIgnoreCase(String name);

    List<Destination> findByCountryNameContainingIgnoreCase(String name);
}
