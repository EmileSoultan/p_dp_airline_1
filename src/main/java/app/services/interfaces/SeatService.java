package app.services.interfaces;

import app.entities.Seat;

public interface SeatService {
    Seat save (Seat seat);
    Seat findById (long id);
    Seat editById(Long id, Seat Seat);
}
