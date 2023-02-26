package app.services;

import app.entities.Seat;
import app.repositories.SeatRepository;
import app.services.interfaces.AircraftService;
import app.services.interfaces.CategoryService;
import app.services.interfaces.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final CategoryService categoryService;
    private final AircraftService aircraftService;

    @Transactional
    @Override
    public Seat save (Seat seat) {
        if (seat.getId() != 0) {
            Seat aldSeat = findById(seat.getId());
            if (aldSeat != null && aldSeat.getAircraft() != null) {
                seat.setAircraft(aldSeat.getAircraft());
            }
        }
        seat.setCategory(categoryService.findByCategoryType(seat.getCategory().getCategoryType()));
        return seatRepository.saveAndFlush(seat);
    }

    @Override
    public Seat findById (long id) {
        return seatRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Seat editById(Long id, Seat seat) {
        var targetSeat = seatRepository.findById(id).orElse(null);
        if (seat.getCategory() != null && seat.getCategory().getCategoryType() != targetSeat.getCategory().getCategoryType()) {
            targetSeat.setCategory(categoryService.findByCategoryType(seat.getCategory().getCategoryType()));
        }
        if (seat.getAircraft() != null && !seat.getAircraft().equals(targetSeat.getAircraft())) {
            targetSeat.setAircraft(aircraftService.findByAircraftNumber(seat.getAircraft().getAircraftNumber()));
        }
        targetSeat.setSeatNumber(seat.getSeatNumber());
        targetSeat.setIsNearEmergencyExit(seat.getIsNearEmergencyExit());
        targetSeat.setIsLockedBack(seat.getIsLockedBack());
        return seatRepository.saveAndFlush(targetSeat);
    }

}
