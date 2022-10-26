package app.services;

import app.entities.Seat;

public interface SeatService {
    void save (Seat seat);
    Seat findById (long id);
}
