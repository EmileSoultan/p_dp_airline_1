package app.services;

import app.entities.Seat;
import app.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Transactional
    @Override
    public void save (Seat seat) {
        seatRepository.save(seat);
    }

    @Override
    public Seat findById (long id) {
        return seatRepository.findById(id).orElse(null);
    }

}
