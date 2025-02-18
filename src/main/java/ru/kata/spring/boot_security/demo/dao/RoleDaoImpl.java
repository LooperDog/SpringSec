package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getAllRoles() {
        List<Role> roles = entityManager.createQuery("select r from Role r", Role.class).getResultList();
        return new HashSet<Role>(roles);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return entityManager.createQuery("select r from Role r where r.name=:roleName", Role.class)
                .setParameter("roleName", roleName)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Role getById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Set<Role> getSetOfRoles(String[] roleNames) {
        Set<Role> rolesSet = new HashSet<>();
        for (String roleName : roleNames) {
            rolesSet.add(getRoleByName(roleName));
        }
        return rolesSet;
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    @Transactional
    public void deleteRole(Role role) {
        entityManager.remove(entityManager.merge(role));
    }
}
