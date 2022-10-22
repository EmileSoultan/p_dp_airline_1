package app.config;

import app.entities.Role;
import app.entities.Ticket;
import app.entities.User;
import app.services.RoleService;
import app.services.TicketService;
import app.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import app.entities.Aircraft;
import app.entities.Destination;
import app.entities.Seat;
import app.enums.Airport;
import app.services.AircraftService;
import app.services.SeatService;
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

    private final TicketService ticketService;
    private final UserService userService;
    private final RoleService roleService;
    private final DestinationService destinationService;
    private final PasswordEncoder encoder;
    private final AircraftService aircraftService;
    private final SeatService seatService;


    public DataInitializer(UserService userService,
                           RoleService roleService,
                           DestinationService destinationService,
                           AircraftService aircraftService,
                           TicketService ticketService,
                           SeatService seatService,
                           PasswordEncoder encoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.destinationService = destinationService;
        this.aircraftService = aircraftService;
        this.ticketService = ticketService;
        this.seatService = seatService;
        this.encoder = encoder;
    }

    @PostConstruct
    @Transactional
    public void init() {
        System.out.println("DataInitializer сработал!");
        initDbWithRolesAndUsers();
        initDbWithDestination();
        aircraftsInit();
        initSeat();
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

        Ticket ticket2 = new Ticket(2L, "SD-2222", "Иван", "Боинг-747", "9A");
        ticketService.saveTicket(ticket2);

        Ticket ticket3 = new Ticket(3L, "ZX-3333", "Андрей", "Боинг-747", "6D");
        ticketService.saveTicket(ticket3);
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

    public void initSeat() {

        Seat seat1 = new Seat();
        seat1.setSeatNumber("1B");
        seat1.setIsNearEmergencyExit(false);
        seat1.setFare(500);
        seat1.setIsRegistered(true);
        seat1.setIsRegistered(true);
        seatService.save(seat1);

        Seat seat2 = new Seat();
        seat2.setSeatNumber("11A");
        seat2.setIsNearEmergencyExit(true);
        seat2.setFare(300);
        seat2.setIsRegistered(true);
        seat2.setIsRegistered(false);
        seatService.save(seat2);

        Seat seat3 = new Seat();
        seat3.setSeatNumber("21F");
        seat3.setIsNearEmergencyExit(false);
        seat3.setFare(100);
        seat3.setIsRegistered(false);
        seat3.setIsRegistered(false);
        seatService.save(seat2);
    }

}