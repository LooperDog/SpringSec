package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Set;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;
    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public RoleServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String roleName) {
        return roleDao.getRoleByName(roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getById(Long id) {
        return roleDao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getSetOfRoles(String[] roleNames) {
        return roleDao.getSetOfRoles(roleNames);
    }

    @Override
    @Transactional(readOnly = true)
    public void addRole(Role role) {
    roleDao.addRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteRole(Role role) {
        roleDao.deleteRole(role);
    }
}
