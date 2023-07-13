package app.util.mappers;


import app.dto.account.PassengerDTO;
import app.entities.account.Passenger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
        passengerDTO.setPassword(passenger.getPassword());
        passengerDTO.setSecurityQuestion(passenger.getSecurityQuestion());
        passengerDTO.setAnswerQuestion(passenger.getAnswerQuestion());
        passengerDTO.setRoles(passenger.getRoles());
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
        passenger.setEmail(passengerDTO.getEmail());
        passenger.setPassword(passengerDTO.getPassword());
        passenger.setSecurityQuestion(passengerDTO.getSecurityQuestion());
        passenger.setAnswerQuestion(passengerDTO.getAnswerQuestion());
        passenger.setRoles(passengerDTO.getRoles());
        passenger.setPassport(passengerDTO.getPassport());

        return passenger;
    }
}
