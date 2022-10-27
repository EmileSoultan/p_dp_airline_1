package app.services;

import app.entities.Role;

public interface RoleService {
    Role getRoleByName(String name);

    void saveRole(Role role);
}
