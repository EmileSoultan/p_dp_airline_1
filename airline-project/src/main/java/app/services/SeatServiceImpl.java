package app.services;

import app.dto.SeatDTO;
import app.entities.Aircraft;
import app.entities.Category;
import app.entities.Seat;
import app.enums.seats.SeatsNumbersByAircraft;
import app.enums.seats.interfaces.AircraftSeats;
import app.enums.CategoryType;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Page<Seat> findByAircraftId(Long id, Pageable pageable){
        return seatRepository.findByAircraftId(id, pageable);
    }

    @Override
    @Transactional
    public List<SeatDTO> generate(long aircraftId) {
        Category economyCategory = categoryService.findByCategoryType(CategoryType.ECONOMY);
        Category businessCategory = categoryService.findByCategoryType(CategoryType.BUSINESS);

        List<SeatDTO> savedSeatsDTO = new ArrayList<>(getNumbersOfSeatsByAircraft(aircraftId).getTotalNumberOfSeats());
        if (findByAircraftId(aircraftId, Pageable.unpaged()).getTotalElements() > 0) {
            return savedSeatsDTO;
        }
        int enumSeatsCounter = 0;
        for (SeatDTO seatDTO : getSeatsDTO(aircraftId)) {
            seatDTO.setSeatNumber(getAircraftSeats(aircraftId)[enumSeatsCounter].getNumber());
            seatDTO.setAircraftId(aircraftId);
            if (enumSeatsCounter < getNumbersOfSeatsByAircraft(aircraftId).getNumberOfBusinessClassSeats()) { //Назначаем категории
                seatDTO.setCategory(businessCategory);
            } else {
                seatDTO.setCategory(economyCategory);
            }
            seatDTO.setIsNearEmergencyExit(getAircraftSeats(aircraftId)[enumSeatsCounter].isNearEmergencyExit());
            seatDTO.setIsLockedBack(getAircraftSeats(aircraftId)[enumSeatsCounter].isLockedBack());
            enumSeatsCounter += 1;

            Seat savedSeat = save(seatMapper.convertToSeatEntity(seatDTO));
            savedSeatsDTO.add(new SeatDTO(savedSeat));
        }
        return savedSeatsDTO;
    }
    private SeatsNumbersByAircraft getNumbersOfSeatsByAircraft(long aircraftId) {
        Aircraft aircraft = aircraftService.findById(aircraftId); //создается объект САМОЛЕТ
        return SeatsNumbersByAircraft.valueOf(aircraft.getModel() //количество мест в самолете
                .toUpperCase().replace(" ", "_"));
    }
    private AircraftSeats[] getAircraftSeats(long aircraftId) {
        return getNumbersOfSeatsByAircraft(aircraftId).getAircraftSeats();
    }
    private List<SeatDTO> getSeatsDTO(long aircraftId) {
        List<SeatDTO> seatsDTO = Stream.generate(SeatDTO::new)
                .limit(getNumbersOfSeatsByAircraft(aircraftId).getTotalNumberOfSeats()).collect(Collectors.toList());
        return seatsDTO;
    }
    @Override
    public Page<Seat> findAll(Pageable pageable) {
        return seatRepository.findAll(pageable);
    }
}
