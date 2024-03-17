package lyngby.dk.HotelExercise.DAO;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lyngby.dk.HotelExercise.models.Room;

import java.util.List;

public class RoomDAO extends DAO<Room>{
    EntityManagerFactory emf;

    public RoomDAO(EntityManagerFactory emf) {
        super(emf);
    }

    public List<Room> getAll() {
        try (var em = emf.createEntityManager()) {
            TypedQuery<Room> q = em.createQuery("SELECT r FROM Room r", Room.class);
            return q.getResultList();
        }
    }

    @Override
    public Room getById(int id) {
        try (var em = emf.createEntityManager()) {
            TypedQuery<Room> q = em.createQuery("SELECT r FROM Room r WHERE r.id = :id", Room.class);
            q.setParameter("id", id);
            return q.getSingleResult();
        }
    }




    @Override
    public Room update(Room room) {
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(room);
            em.getTransaction().commit();
        }
        return room;
    }

}