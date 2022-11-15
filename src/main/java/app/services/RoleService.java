package app.services;

import app.entities.user.Role;

public interface RoleService {
    Role getRoleByName(String name);

    void saveRole(Role role);
}
