package app.services;

import app.entities.user.User;
import app.repositories.UserRepository;
import app.services.interfaces.RoleService;
import app.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder encoder;
    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(roleService.saveRolesToUser(user));
        if (user.getAnswerQuestion() != null) {
            user.setAnswerQuestion(encoder.encode(user.getAnswerQuestion()));
        }
        userRepository.saveAndFlush(user);
    }

    @Override
    public void updateUser(Long id, User user) {
        var editUser = userRepository.getUserById(id);
        if (!user.getPassword().equals(editUser.getPassword())) {
            editUser.setPassword(encoder.encode(user.getPassword()));
        }
        if (!user.getAnswerQuestion()
                .equals(userRepository.findById(id).orElse(null).getAnswerQuestion())) {
            editUser.setAnswerQuestion(encoder.encode(user.getAnswerQuestion()));
        }
        editUser.setRoles(roleService.saveRolesToUser(user));
        editUser.setEmail(user.getEmail());
        userRepository.saveAndFlush(editUser);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
