package app.util.mappers;

import app.dto.DestinationDTO;
import app.entities.Destination;
import org.springframework.stereotype.Component;

@Component
public class DestinationMapper {

    public Destination convertToDestinationEntity(DestinationDTO dto) {
        Destination destination = new Destination();

        destination.setId(dto.getId());
        destination.setCountryName(dto.getCountryName());
        destination.setAirportName(dto.getAirportName());
        destination.setAirportCode(dto.getAirportCode());
        destination.setTimezone(dto.getTimezone());
        destination.setCityName(dto.getCityName());

        return destination;
    }

    public DestinationDTO convertToDestinationDTOEntity(Destination destination) {
        DestinationDTO destinationDTO = new DestinationDTO();

        destinationDTO.setId(destination.getId());
        destinationDTO.setCountryName(destination.getCountryName());
        destinationDTO.setAirportName(destination.getAirportName());
        destinationDTO.setAirportCode(destination.getAirportCode());
        destinationDTO.setTimezone(destination.getTimezone());
        destinationDTO.setCityName(destination.getCityName());

        return destinationDTO;
    }
}
