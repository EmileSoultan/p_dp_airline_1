package app.config;

import app.entities.Role;
import app.entities.User;
import app.services.RoleService;
import app.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private final PasswordEncoder encoder;

    public DataInitializer(UserService userService, RoleService roleService, PasswordEncoder encoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @PostConstruct
    @Transactional
    public void init() {
        System.out.println("DataInitializer сработал!");

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
}
