package app.util.mappers;


import app.dto.PassengerDTO;
import app.entities.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public PassengerDTO convertToPassengerDTO(Passenger passenger) {
        PassengerDTO passengerDTO = new PassengerDTO();

        passengerDTO.setId(passenger.getId());
        passengerDTO.setFirstName(passenger.getFirstName());
        passengerDTO.setLastName(passenger.getLastName());
        passengerDTO.setBirthDate(passenger.getBirthDate());
        passengerDTO.setPhoneNumber(passenger.getPhoneNumber());
        passengerDTO.setEmail(passenger.getEmail());
        passengerDTO.setPassport(passenger.getPassport());

        return passengerDTO;
    }

    public Passenger convertToPassengerEntity(PassengerDTO passengerDTO) {
        Passenger passenger = new Passenger();
        passenger.setId(passengerDTO.getId());
        passenger.setFirstName(passengerDTO.getFirstName());
        passenger.setLastName(passengerDTO.getLastName());
        passenger.setBirthDate(passengerDTO.getBirthDate());
        passenger.setPhoneNumber(passengerDTO.getPhoneNumber());
        passenger.setPassport(passengerDTO.getPassport());

        return passenger;
    }
}
