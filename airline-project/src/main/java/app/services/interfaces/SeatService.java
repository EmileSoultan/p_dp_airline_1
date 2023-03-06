package app.services.interfaces;

import app.dto.SeatDTO;
import app.entities.Seat;
import app.exceptions.ViolationOfForeignKeyConstraintException;

import java.util.List;

public interface SeatService {
    Seat save (Seat seat);
    Seat findById (long id);
    Seat editById(Long id, Seat Seat);
    void delete(Long id) throws ViolationOfForeignKeyConstraintException;
    List<Seat> findByAircraftId(Long id);
    List<SeatDTO> saveManySeats(List<SeatDTO> seatsDTO, long aircraftId);
}
