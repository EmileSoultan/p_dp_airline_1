package app.services;

import app.entities.Role;
import app.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
