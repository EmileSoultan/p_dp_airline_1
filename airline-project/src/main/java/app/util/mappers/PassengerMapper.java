package app.util.mappers;


import app.dto.account.PassengerDTO;
import app.entities.account.Passenger;
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
        passengerDTO.setPassword(passenger.getPassword());
        passengerDTO.setSecurityQuestion(passenger.getSecurityQuestion());
        passengerDTO.setAnswerQuestion(passenger.getAnswerQuestion());
        passengerDTO.setRoles(passenger.getRoles());
        passengerDTO.setPassport(passenger.getPassport());

        return passengerDTO;
    }

    public Passenger convertToPassengerEntity(PassengerDTO pasengerDTO) {
        Passenger passenger = new Passenger();
        passenger.setId(pasengerDTO.getId());
        passenger.setFirstName(pasengerDTO.getFirstName());
        passenger.setLastName(pasengerDTO.getLastName());
        passenger.setBirthDate(pasengerDTO.getBirthDate());
        passenger.setPhoneNumber(pasengerDTO.getPhoneNumber());
        passenger.setEmail(pasengerDTO.getEmail());
        passenger.setPassword(pasengerDTO.getPassword());
        passenger.setSecurityQuestion(pasengerDTO.getSecurityQuestion());
        passenger.setAnswerQuestion(pasengerDTO.getAnswerQuestion());
        passenger.setRoles(pasengerDTO.getRoles());
        passenger.setPassword(pasengerDTO.getPassword());

        return passenger;
    }
}
