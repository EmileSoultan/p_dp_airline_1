package app.services;

import app.dto.SeatDTO;
import app.entities.Seat;
import app.exceptions.ViolationOfForeignKeyConstraintException;
import app.repositories.FlightSeatRepository;
import app.repositories.SeatRepository;
import app.services.interfaces.AircraftService;
import app.services.interfaces.CategoryService;
import app.services.interfaces.SeatService;
import lombok.RequiredArgsConstructor;
import app.util.mappers.SeatMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final CategoryService categoryService;
    private final AircraftService aircraftService;
    private final SeatMapper seatMapper;
    private final FlightSeatRepository flightSeatRepository;


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

    @Override
    @Transactional
    public void delete(Long id) throws ViolationOfForeignKeyConstraintException {
        if(!(flightSeatRepository.findFlightSeatsBySeat(findById(id))).isEmpty()){
            throw new ViolationOfForeignKeyConstraintException(
                    String.format("Seat with id = %d cannot be deleted because it is locked by the table \"flight_seat\"", id));
        }
        seatRepository.deleteById(id);
    }

    @Override
    public List<Seat> findByAircraftId(Long id){
        return seatRepository.findByAircraftId(id);
    }

    @Override
    public List<SeatDTO> saveManySeats(List<SeatDTO> seatsDTO, long aircraftId) {
        List<SeatDTO> savedSeatsDTO = new ArrayList<>();
        for (SeatDTO seatDTO : seatsDTO) {
            seatDTO.setAircraftId(aircraftId);
            Seat savedSeat = save(seatMapper.convertToSeatEntity(seatDTO));
            savedSeatsDTO.add(new SeatDTO(savedSeat));
        }
        return savedSeatsDTO;
    }

}
