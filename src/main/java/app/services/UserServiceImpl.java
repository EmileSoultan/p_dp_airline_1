package app.services;

import app.entities.user.User;
import app.repositories.UserRepository;
import app.services.interfaces.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }
    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void updateUser(User user) {
        if (!user.getPassword().equals(userRepository.findByEmail(user.getEmail()).getPassword())) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        userRepository.saveAndFlush(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        var users = userRepository.findAll();
        users.sort(Comparator.comparingLong(User::getId));
        return users;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
