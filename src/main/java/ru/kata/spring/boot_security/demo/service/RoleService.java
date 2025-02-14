package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getAllRoles();
    Role getRoleByName(String roleName);
    Role getById(Long id);
    Set<Role> getSetOfRoles(String[] roleNames);
    void addRole(Role role);
    void deleteRole(Role role);

}
