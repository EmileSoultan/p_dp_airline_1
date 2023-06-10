package app.util.mappers;

import app.dto.AircraftDTO;
import app.entities.Aircraft;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AircraftMapper {

    public Aircraft convertToAircraftEntity (AircraftDTO aircraftDTO) {
        Aircraft aircraft = new Aircraft();
        aircraft.setId(aircraftDTO.getId());
        aircraft.setAircraftNumber(aircraftDTO.getAircraftNumber());
        aircraft.setModel(aircraftDTO.getModel());
        aircraft.setModelYear(aircraftDTO.getModelYear());
        aircraft.setFlightRange(aircraftDTO.getFlightRange());

        return aircraft;
    }

    public AircraftDTO convertToAircarftDTOEntity(Aircraft aircraft){
        AircraftDTO aircraftDTO = new AircraftDTO();
        aircraftDTO.setId(aircraft.getId());
        aircraftDTO.setAircraftNumber(aircraft.getAircraftNumber());
        aircraftDTO.setModel(aircraft.getModel());
        aircraftDTO.setModelYear(aircraft.getModelYear());
        aircraftDTO.setFlightRange(aircraft.getFlightRange());

        return aircraftDTO;
    }
}
