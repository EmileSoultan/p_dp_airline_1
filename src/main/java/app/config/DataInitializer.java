package app.config;

import app.entities.Role;
import app.entities.User;
import app.services.RoleService;
import app.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import app.entities.Aircraft;
import app.entities.Destination;
import app.enums.Airport;
import app.services.AircraftService;
import app.services.DestinationService;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */
@Component
public class DataInitializer {

    private final UserService userService;
    private final RoleService roleService;
    private final DestinationService destinationService;
    private final PasswordEncoder encoder;
    private final AircraftService aircraftService;


    public DataInitializer(UserService userService, RoleService roleService, DestinationService destinationService, AircraftService aircraftService, PasswordEncoder encoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.destinationService = destinationService;
        this.aircraftService = aircraftService;
        this.encoder = encoder;
    }

    @PostConstruct
    @Transactional
    public void init() {
        System.out.println("DataInitializer сработал!");
        initDbWithRolesAndUsers();
        initDbWithDestination();
        aircraftsInit();
    }

    private void initDbWithRolesAndUsers() {
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleService.saveRole(roleAdmin);

        Role rolePassenger = new Role();
        rolePassenger.setName("ROLE_PASSENGER");
        roleService.saveRole(rolePassenger);

        Role roleManager = new Role();
        roleManager.setName("ROLE_MANAGER");
        roleService.saveRole(roleManager);

        User admin = new User();
        admin.setEmail("admin@mail.ru");
        admin.setPassword(encoder.encode("admin"));
        admin.setRoles(Set.of(roleService.getRoleByName("ROLE_ADMIN")));
        userService.saveUser(admin);

        User passenger = new User();
        passenger.setEmail("passenger@mail.ru");
        passenger.setPassword(encoder.encode("passenger"));
        passenger.setRoles(Set.of(roleService.getRoleByName("ROLE_PASSENGER")));
        userService.saveUser(passenger);

        User manager = new User();
        manager.setEmail("manager@mail.ru");
        manager.setPassword(encoder.encode("manager"));
        manager.setRoles(Set.of(roleService.getRoleByName("ROLE_MANAGER")));
        userService.saveUser(manager);
    }

    public void initDbWithDestination() {
        Destination destination1 = new Destination(1L, Airport.VKO, "Внуково", "Москва", "GMT +3", "Россия");
        destinationService.saveDestination(destination1);

        Destination destination2 = new Destination(2L, Airport.VOG, "Гумрак", "Волгоград", "GMT +3", "Россия");
        destinationService.saveDestination(destination2);

        Destination destination3 = new Destination(3L, Airport.MQF, "Магнитогорск", "Магнитогорск", "GMT +5", "Россия");
        destinationService.saveDestination(destination3);

        Destination destination4 = new Destination(4L, Airport.OMS, "Омск", "Омск", "GMT +6", "Россия");
        destinationService.saveDestination(destination4);

        destinationService.deleteDestinationById(3L);

        destinationService.updateDestination(new Destination(4L, Airport.GDX, "Сокол", "Магадан", "GMT +11", "Россия"));

        System.out.println(destinationService.findDestinationByName("волг", ""));
    }

    private void aircraftsInit() {
//        List<Seat> seatList1;
//        List<Seat> seatList2;
//        List<Seat> seatList3;

        Aircraft aircraft1 = new Aircraft();
        aircraft1.setAircraftNumber("17000012");
        aircraft1.setModel("Embraer E170STD");
        aircraft1.setModelYear(2002);
        aircraft1.setFlightRange(3800);
//        aircraft1.setSeatList(seatList1);
        aircraftService.save(aircraft1);

        Aircraft aircraft2 = new Aircraft();
        aircraft2.setAircraftNumber("5134");
        aircraft2.setModel("Airbus A320-200");
        aircraft2.setModelYear(2011);
        aircraft2.setFlightRange(4300);
//        aircraft2.setSeatList(seatList2);
        aircraftService.save(aircraft2);

        Aircraft aircraft3 = new Aircraft();
        aircraft3.setAircraftNumber("35283");
        aircraft3.setModel("Boeing 737-800");
        aircraft3.setModelYear(2008);
        aircraft3.setFlightRange(5765);
//        aircraft3.setSeatList(seatList3);
        aircraftService.save(aircraft3);
    }

}
