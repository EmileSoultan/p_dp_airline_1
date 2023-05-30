package app.util.mappers;

import app.dto.account.PassengerDTO;
import app.entities.account.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {


    public Passenger convertToPassengerEntity(PassengerDTO passengerDTO) {
        Passenger passenger = new Passenger();

        passenger.setId(passengerDTO.getId());
        passenger.setEmail(passengerDTO.getEmail());
        passenger.setPassword(passengerDTO.getPassword());
        passenger.setSecurityQuestion(passengerDTO.getSecurityQuestion());
        passenger.setAnswerQuestion(passengerDTO.getAnswerQuestion());
        passenger.setRoles(passengerDTO.getRoles());

        passenger.setFirstName(passengerDTO.getFirstName());
        passenger.setLastName(passengerDTO.getLastName());
        passenger.setBirthDate(passengerDTO.getBirthDate());
        passenger.setPhoneNumber(passengerDTO.getPhoneNumber());
        passenger.setPassport(passengerDTO.getPassport());

        return passenger;
    }
}
