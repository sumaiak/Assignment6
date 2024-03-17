package lyngby.dk.Secuirty.Dao;


import io.javalin.validation.ValidationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lyngby.dk.Secuirty.Interfaces.ISecurityDAO;
import lyngby.dk.Secuirty.Models.Role;
import lyngby.dk.Secuirty.Models.User;

public class UserDAO implements ISecurityDAO {

    private EntityManagerFactory emf;

    @Override

    public User getVerifiedUser(String username, String password) throws ValidationException {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, username);

            if (user == null || !user.verifyPassword(password)) {
                throw new RuntimeException();

            }
            em.getTransaction().commit();
            return user;
        }
    }

    @Override
    public User createUser(String username, String password) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = new User(username, password);
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }

    }


    @Override
    public Role createRole(String role) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Role role1 = new Role();
            em.persist(role1);

            em.getTransaction().commit();
            return role1;
        }
    }

    @Override
    public User addUserRole(String username, String roles) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, username);
            Role role = em.find(Role.class, roles);
            user.addRole(role);
            em.getTransaction().commit();
            return user;
        }
    }
}
