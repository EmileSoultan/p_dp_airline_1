package app.services;

import app.dto.SeatDTO;
import app.entities.Aircraft;
import app.entities.Category;
import app.entities.Seat;
import app.enums.CategoryType;
import app.repositories.FlightSeatRepository;
import app.repositories.SeatRepository;
import app.services.interfaces.AircraftService;
import app.services.interfaces.CategoryService;
import app.services.interfaces.SeatService;
import app.util.mappers.SeatMapper;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SeatServiceImplTest {

    private static final Long AIRCRAFT_TEST_ID = 1L;
    private static final String AIRCRAFT_TEST_MODEL = "Airbus A320";

    private final SeatRepository seatRepository = mock(SeatRepository.class);
    private final CategoryService categoryService = mock(CategoryService.class);
    private final AircraftService aircraftService = mock(AircraftService.class);
    private final SeatMapper seatMapper = mock(SeatMapper.class);
    private final FlightSeatRepository flightSeatRepository = mock(FlightSeatRepository.class);

    private final SeatService seatService = new SeatServiceImpl(
            seatRepository,
            categoryService,
            aircraftService,
            seatMapper,
            flightSeatRepository
    );

    @Test
    public void generateSuccessfullyTest() {

        Category economyCategory = new Category();
        economyCategory.setCategoryType(CategoryType.ECONOMY);

        Category businessCategory = new Category();
        businessCategory.setCategoryType(CategoryType.BUSINESS);

        Aircraft aircraft = new Aircraft();
        aircraft.setId(AIRCRAFT_TEST_ID);
        aircraft.setModel(AIRCRAFT_TEST_MODEL);

        Seat seat = new Seat();
        seat.setCategory(economyCategory);
        seat.setAircraft(aircraft);

        when(categoryService.findByCategoryType(CategoryType.ECONOMY))
                .thenReturn(economyCategory);
        when(categoryService.findByCategoryType(CategoryType.BUSINESS))
                .thenReturn(businessCategory);
        when(aircraftService.findById(AIRCRAFT_TEST_ID))
                .thenReturn(aircraft);
        when(seatRepository.findByAircraftId(eq(AIRCRAFT_TEST_ID), any()))
                .thenReturn(new PageImpl<Seat>(Collections.singletonList(seat)));
        when(seatMapper.convertToSeatEntity(any()))
                .thenReturn(seat);
        when(seatRepository.saveAndFlush(any()))
                .thenReturn(seat);

        List<SeatDTO> seatDTOs = seatService.generate(AIRCRAFT_TEST_ID);

        long businessSeatsCount = seatDTOs.stream()
                .map(SeatDTO::getCategory)
                .map(Category::getCategoryType)
                .filter(categoryType -> CategoryType.BUSINESS == categoryType)
                .count();

        assertFalse(seatDTOs.isEmpty());
        assertEquals(158, seatDTOs.size());
        assertEquals(8, businessSeatsCount);
    }

}
