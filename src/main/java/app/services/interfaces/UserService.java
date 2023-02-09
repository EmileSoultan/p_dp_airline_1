package app.services.interfaces;

import app.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    void saveUser(User user);

    void updateUser(Long id, User user);

    Page<User> getAllUsers(Pageable pageable);

    Optional<User> getUserById(Long id);

    User getUserByEmail(String email);

    void deleteUserById(Long id);

}
