package app.repositories;

import app.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query(value = "select seat from Seat seat join seat.aircraft a where a.id = :id")
    List<Seat> findByAircraftId(Long id);
}
