package app.services;

import app.entities.account.Passenger;
import app.repositories.PassengerRepository;
import app.repositories.RoleRepository;
import app.services.interfaces.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final int maxVolumePartsOfName = 3;

    private final PassengerRepository passengerRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public Passenger save(Passenger passenger) {
        passenger.setPassword(encoder.encode(passenger.getPassword()));
        passenger.setAnswerQuestion(encoder.encode(passenger.getAnswerQuestion()));
        passenger.setRoles(Set.of(roleRepository.findByName("ROLE_PASSENGER")));
        return passengerRepository.save(passenger);
    }

    @Override
    @Transactional
    public Passenger update(Long id, Passenger passenger) {
        passenger.setId(id);
        if (!passenger.getPassword()
                .equals(passengerRepository.findById(passenger.getId()).orElse(null).getPassword())) {
            passenger.setPassword(encoder.encode(passenger.getPassword()));
        }
        if (!passenger.getAnswerQuestion()
                .equals(passengerRepository.findById(passenger.getId()).orElse(null).getAnswerQuestion())) {
            passenger.setAnswerQuestion(encoder.encode(passenger.getAnswerQuestion()));
        }
        return passengerRepository.saveAndFlush(passenger);
    }

    @Override
    public Page<Passenger> findAll(Pageable pageable) {
        return passengerRepository.findAll(pageable);
    }

    @Override
    public Passenger findById(Long id) {
        return passengerRepository.findById(id).orElse(null);
    }

    @Override
    public Passenger findByEmail(String email) {
        return passengerRepository.findByEmail(email);
    }

    @Override
    public Passenger findBySerialNumberPassport(String serialNumberPassport) {

        Passenger passenger = passengerRepository.findByPassport_serialNumberPassport(serialNumberPassport).orElse(null);
        if (passenger == null) {
            return null;
        }
        return passenger;
    }

    @Override
    public List<Passenger> findByFullName(String fullName) {
        String[] separatedName = separationNameOfPassenger(fullName);
        if (separatedName.length > maxVolumePartsOfName || separatedName.length == 0) {
            return null;
        }
        return filterListPassenger(passengerRepository.findByLastName(separatedName[0]), separatedName);
    }

    @Override
    public List<Passenger> findByLastName(String lastName) {
        return passengerRepository.findByLastName(lastName);
    }

    @Override
    public List<Passenger> findByAnyName(String anyName) {
        String[] partsOfNameName = separationNameOfPassenger(anyName);
        if (partsOfNameName.length > maxVolumePartsOfName || partsOfNameName.length == 0) {
            return null;
        }
        return findPassengersByAnyName(partsOfNameName);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!passengerRepository.findById(id).isPresent()) {
            return;
        }
        passengerRepository.deleteById(id);
    }

    private List<Passenger> findPassengersByAnyName(String[] partsOfName) {
        List<Passenger> passengerListByLastName = passengerRepository.findByLastName(partsOfName[0]);
        List<Passenger> passengerListByFirstName = passengerRepository.findByFirstName(partsOfName[0]);
        List<Passenger> passengerListByMiddleName = passengerRepository.findByMiddleName(partsOfName[0]);

        if (passengerListByLastName == null && passengerListByFirstName == null && passengerListByMiddleName == null) {
            return null;
        } else if (passengerListByLastName != null && passengerListByFirstName == null && passengerListByMiddleName == null) {
            return filterListPassenger(passengerListByLastName, partsOfName);
        } else if (passengerListByFirstName != null && passengerListByLastName == null && passengerListByMiddleName == null) {
            return filterListPassenger(passengerListByFirstName, partsOfName);
        } else if (passengerListByMiddleName != null && passengerListByLastName == null && passengerListByFirstName == null) {
            return filterListPassenger(passengerListByMiddleName, partsOfName);
        } else {
            Set<Passenger> passengersSet = new HashSet<>();
            passengersSet.addAll(passengerListByLastName);
            passengersSet.addAll(passengerListByFirstName);
            passengersSet.addAll(passengerListByMiddleName);
            return filterListPassenger(passengersSet.stream().collect(Collectors.toList()), partsOfName);
        }
    }

    private String[] separationNameOfPassenger(String name) {
        return name.split("\\s+");
    }

    private List<Passenger> filterListPassenger(List<Passenger> passengerList, String[] partsOfName) {
        for (int i = 0; i < partsOfName.length; i++) {
            for (int j = 0; j < passengerList.size(); j++) {
                if (hasAllNames(passengerList.get(j))) {
                    if (!passengerList.get(j).getLastName().equals(partsOfName[i]) &&
                            !passengerList.get(j).getFirstName().equals(partsOfName[i]) &&
                            !passengerList.get(j).getPassport().getMiddleName().equals(partsOfName[i])) {
                        passengerList.remove(j);
                    }
                }
            }
        }
        return passengerList;
    }

    private boolean hasAllNames(Passenger passenger) {
        if (passenger.getLastName() != null &&
                passenger.getFirstName() != null && passenger.getPassport().getMiddleName() != null) {
            return true;
        }
        return false;
    }

}
