package app.services.interfaces;

import app.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User user);

    void updateUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User getUserByEmail(String email);

    void deleteUserById(Long id);
}
