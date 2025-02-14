package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> getAllRoles();
    Role getRoleByName(String roleName);
    Role getById(Long id);
    Set<Role> getSetOfRoles(String[] roleNames);
    void addRole(Role role);
    void deleteRole(Role role);
}
