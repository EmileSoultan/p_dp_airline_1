package app.services;

import app.entities.user.Role;
import app.entities.user.User;
import app.repositories.RoleRepository;
import app.services.interfaces.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public Set<Role> saveRolesToUser(User user) {
        Set<Role> userRoles = new HashSet<>();
        user.getRoles().stream().forEach(a -> {
            Role roleFromDb = getRoleByName(a.getName());
            if (roleFromDb == null) {
                throw new RuntimeException("role not found");
            }
            userRoles.add(roleFromDb);
        });
        return userRoles;
    }
    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
