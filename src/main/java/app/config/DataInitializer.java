package app.config;

import app.entities.user.Admin;
import app.entities.Aircraft;
import app.entities.Category;
import app.entities.user.AirlineManager;
import app.entities.Destination;
import app.entities.Flight;
import app.entities.user.Passenger;
import app.entities.Passport;
import app.entities.user.Role;
import app.entities.Route;
import app.entities.Seat;
import app.entities.Ticket;
import app.enums.Airport;
import app.enums.CategoryType;
import app.enums.FlightStatus;
import app.enums.Gender;
import app.services.AircraftService;
import app.services.CategoryService;
import app.services.DestinationService;
import app.services.FlightService;
import app.services.RoleService;
import app.services.RouteService;
import app.services.SeatService;
import app.services.TicketService;
import app.services.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */
@Component
@Profile("dev")
public class DataInitializer {
    private final TicketService ticketService;
    private final UserService userService;
    private final RoleService roleService;
    private final DestinationService destinationService;
    private final PasswordEncoder encoder;
    private final AircraftService aircraftService;
    private final CategoryService categoryService;
    private final SeatService seatService;
    private final RouteService routeService;

    private final FlightService flightService;


    public DataInitializer(UserService userService,
                           RoleService roleService,
                           DestinationService destinationService,
                           AircraftService aircraftService,
                           TicketService ticketService,
                           CategoryService categoryService,
                           SeatService seatService,
                           FlightService flightService,
                           RouteService routeService,
                           PasswordEncoder encoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.destinationService = destinationService;
        this.aircraftService = aircraftService;
        this.ticketService = ticketService;
        this.categoryService = categoryService;
        this.seatService = seatService;
        this.flightService = flightService;
        this.routeService = routeService;
        this.encoder = encoder;
    }

    @PostConstruct
    @Transactional
    public void init() {
        System.out.println("DataInitializer сработал!");
        initDbWithRolesAndUsers();
        initDbWithDestination();
        initCategory();
        initSeat();
        aircraftsInit();
        initFlight();
        initDbByPassengerAndPassport();
        routesInit();
    }

    private void initCategory() {

        Category firstClass = new Category();
        firstClass.setCategoryType(CategoryType.FIRST);
        categoryService.save(firstClass);

        Category businessClass = new Category();
        businessClass.setCategoryType(CategoryType.BUSINESS);
        categoryService.save(businessClass);

        Category premiumEconomyClass = new Category();
        premiumEconomyClass.setCategoryType(CategoryType.PREMIUM_ECONOMY);
        categoryService.save(premiumEconomyClass);

        Category economyClass = new Category();
        economyClass.setCategoryType(CategoryType.ECONOMY);
        categoryService.save(economyClass);
    }

    private void initFlight() {
        Flight flight1 = new Flight(1L, "MSKOMSK",
                destinationService.findDestinationByName("Москва", "").get(0),
                destinationService.findDestinationByName("Омск", "").get(0),
                LocalDateTime.of(2022,Month.NOVEMBER,11,2,15),
                LocalDateTime.now(),
                aircraftService.findById(1L), FlightStatus.ON_TIME);
        flightService.save(flight1);

        Flight flight2 = new Flight(2L, "MSKVLG",
                destinationService.findDestinationByName("Москва", "").get(0),
                destinationService.findDestinationByName("Волгоград", "").get(0),
                LocalDateTime.of(2022,Month.NOVEMBER,11,8,15),
                LocalDateTime.now(),
                aircraftService.findById(2L), FlightStatus.DELAYED);
        flightService.save(flight2);

        Flight flight3 = new Flight(3L, "MSKOMSK",
                destinationService.findDestinationByName("Омск", "").get(0),
                destinationService.findDestinationByName("Москва", "").get(0),
                LocalDateTime.of(2022,Month.NOVEMBER,15,3,00),
                LocalDateTime.now(),
                aircraftService.findById(1L), FlightStatus.ON_TIME);
        flightService.save(flight3);

        Flight flight4 = new Flight(4L, "MSKVLG",
                destinationService.findDestinationByName("Москва", "").get(0),
                destinationService.findDestinationByName("Волгоград", "").get(0),
                LocalDateTime.of(2022,Month.NOVEMBER,11,9,30),
                LocalDateTime.now(),
                aircraftService.findById(2L), FlightStatus.DELAYED);
        flightService.save(flight4);
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

        Admin admin = new Admin();
        admin.setEmail("admin@mail.ru");
        admin.setPassword(encoder.encode("admin"));
        admin.setRoles(Set.of(roleService.getRoleByName("ROLE_ADMIN")));
        userService.saveUser(admin);

        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Simons");
        passenger.setBirthDate(new Date(103, Calendar.NOVEMBER, 10));
        passenger.setEmail("passenger@mail.ru");
        passenger.setPassword(encoder.encode("passenger"));
        passenger.setRoles(Set.of(roleService.getRoleByName("ROLE_PASSENGER")));
        userService.saveUser(passenger);

        AirlineManager manager = new AirlineManager();
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
        List<Seat> seatListFor17000012 = List.of(
                seatService.findById(1),
                seatService.findById(2),
                seatService.findById(7),
                seatService.findById(8)
        );
        List<Seat> seatListFor5134 = List.of(
                seatService.findById(1),
                seatService.findById(2),
                seatService.findById(3),
                seatService.findById(4),
                seatService.findById(7),
                seatService.findById(8),
                seatService.findById(9),
                seatService.findById(10)
        );
        List<Seat> seatListFor35283= List.of(
                seatService.findById(1),
                seatService.findById(2),
                seatService.findById(3),
                seatService.findById(4),
                seatService.findById(5),
                seatService.findById(6),
                seatService.findById(7),
                seatService.findById(8),
                seatService.findById(9),
                seatService.findById(10),
                seatService.findById(11),
                seatService.findById(12)
        );

        Aircraft aircraft1 = new Aircraft();
        aircraft1.setAircraftNumber("17000012");
        aircraft1.setModel("Embraer E170STD");
        aircraft1.setModelYear(2002);
        aircraft1.setFlightRange(3800);
        aircraft1.setSeatList(seatListFor17000012);
        aircraftService.save(aircraft1);

        Aircraft aircraft2 = new Aircraft();
        aircraft2.setAircraftNumber("5134");
        aircraft2.setModel("Airbus A320-200");
        aircraft2.setModelYear(2011);
        aircraft2.setFlightRange(4300);
        aircraft2.setSeatList(seatListFor5134);
        aircraftService.save(aircraft2);

        Aircraft aircraft3 = new Aircraft();
        aircraft3.setAircraftNumber("35283");
        aircraft3.setModel("Boeing 737-800");
        aircraft3.setModelYear(2008);
        aircraft3.setFlightRange(5765);
        aircraft3.setSeatList(seatListFor35283);
        aircraftService.save(aircraft3);
    }

    private void initDbByPassengerAndPassport() {
        Passenger passenger1 = new Passenger();
        passenger1.setFirstName("Пётр");
        passenger1.setLastName("Петров");
        passenger1.setMiddleName("Петрович");
        passenger1.setBirthDate(new Date(86, Calendar.JANUARY, 11));
        passenger1.setGender(Gender.MALE);
        passenger1.setEmail("petrov@mail.ru");
        passenger1.setPhoneNumber("79111111111");
        passenger1.setPassword(encoder.encode("passenger1"));
        passenger1.setPassport(new Passport("1111 111111",
                LocalDate.of(2006, 1, 11), "Россия"));
        passenger1.setRoles(Set.of(roleService.getRoleByName("ROLE_PASSENGER")));
        userService.saveUser(passenger1);

        Passenger passenger2 = new Passenger();
        passenger2.setFirstName("Иван");
        passenger2.setLastName("Иванов");
        passenger2.setMiddleName("Иванович");
        passenger2.setBirthDate(new Date(86, Calendar.FEBRUARY, 22));
        passenger2.setGender(Gender.MALE);
        passenger2.setEmail("ivanov@mail.ru");
        passenger2.setPhoneNumber("79222222222");
        passenger2.setPassword(encoder.encode("passenger2"));
        passenger2.setPassport(new Passport("2222 222222",
                LocalDate.of(2006, 2, 22), "Россия"));
        passenger2.setRoles(Set.of(roleService.getRoleByName("ROLE_PASSENGER")));
        userService.saveUser(passenger2);

        Passenger passenger3 = new Passenger();
        passenger3.setFirstName("Екатерина");
        passenger3.setLastName("Сидоровна");
        passenger3.setMiddleName("Сидорова");
        passenger3.setBirthDate(new Date(86, Calendar.MARCH, 30));
        passenger3.setGender(Gender.FEMALE);
        passenger3.setEmail("sidorova@mail.ru");
        passenger3.setPhoneNumber("79333333333");
        passenger3.setPassword(encoder.encode("passenger2"));
        passenger3.setPassport(new Passport("3333 333333",
                LocalDate.of(2006, 3, 30), "Россия"));
        passenger3.setRoles(Set.of(roleService.getRoleByName("ROLE_PASSENGER")));
        userService.saveUser(passenger3);
    }

    public void initSeat() {

        Seat seat1 = new Seat();
        seat1.setSeatNumber("1A");
        seat1.setIsNearEmergencyExit(false);
        seat1.setFare(500);
        seat1.setIsRegistered(true);
        seat1.setIsRegistered(true);
        seatService.save(seat1);

        Seat seat2 = new Seat("1B", false, 500, true, true);
        Seat seat3 = new Seat("1C", false, 500, true, true);
        Seat seat4 = new Seat("1D", false, 500, true, true);
        Seat seat5 = new Seat("1E", false, 500, true, true);
        Seat seat6 = new Seat("1F", false, 500, true, true);
        Seat seat7 = new Seat("2A", true, 700, true, true);
        Seat seat8 = new Seat("2B", true, 700, true, true);
        Seat seat9 = new Seat("2C", true, 700, true, true);
        Seat seat10 = new Seat("2D", true, 700, true, true);
        Seat seat11 = new Seat("2E", true, 700, true, true);
        Seat seat12 = new Seat("2F", true, 700, true, true);
        seatService.save(seat2);
        seatService.save(seat3);
        seatService.save(seat4);
        seatService.save(seat5);
        seatService.save(seat6);
        seatService.save(seat7);
        seatService.save(seat8);
        seatService.save(seat9);
        seatService.save(seat10);
        seatService.save(seat11);
        seatService.save(seat12);

        Seat seat100 = new Seat();
        seat100.setSeatNumber("11A");
        seat100.setIsNearEmergencyExit(true);
        seat100.setFare(300);
        seat100.setIsRegistered(true);
        seat100.setIsRegistered(false);
        seatService.save(seat100);

        Seat seat101 = new Seat();
        seat101.setSeatNumber("21F");
        seat101.setIsNearEmergencyExit(false);
        seat101.setFare(100);
        seat101.setIsRegistered(false);
        seat101.setIsRegistered(false);
        seatService.save(seat101);
    }

    private void routesInit(){
        Route route1 = new Route(
                destinationService.findDestinationByName("Москва", "").get(0),
                destinationService.findDestinationByName("Омск", "").get(0),
                LocalDate.of(2022, Month.NOVEMBER, 30),
                1);
        Route route2 = new Route(
                destinationService.findDestinationByName("омск", "").get(0),
                destinationService.findDestinationByName("Волгоград", "").get(0),
                LocalDate.of(2022, Month.NOVEMBER, 30),
                2);
        Route route3 = new Route(
                destinationService.findDestinationByName("Волгоград", "").get(0),
                destinationService.findDestinationByName("Москва", "").get(0),
                LocalDate.of(2022, Month.NOVEMBER, 30),
                1);

        routeService.saveRoute(route1);
        routeService.saveRoute(route2);
        routeService.saveRoute(route3);
    }
}
