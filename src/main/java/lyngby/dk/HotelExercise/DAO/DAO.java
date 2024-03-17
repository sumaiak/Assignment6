package lyngby.dk.HotelExercise.DAO;

import jakarta.persistence.EntityManagerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class DAO<T> implements IDAO<T>{
    private EntityManagerFactory emf;


    public DAO(EntityManagerFactory emf) {
        this.emf = emf;

    }

    @Override
    public T create(T t) {
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        }
        return t;
    }

    @Override
    public void delete(T t) {
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();
        }
    }
}