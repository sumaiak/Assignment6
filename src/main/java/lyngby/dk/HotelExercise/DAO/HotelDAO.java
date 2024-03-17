package lyngby.dk.HotelExercise.DAO;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lyngby.dk.HotelExercise.models.Hotel;


import java.util.Collection;
import java.util.List;

public class HotelDAO extends DAO <Hotel> {
    EntityManagerFactory emf;

    public HotelDAO(EntityManagerFactory emf) {
        super(emf);
    }

    @Override
    //retrive all hotels thats y no need for a parameteer
    public List<Hotel> getAll() {
        try (var em = emf.createEntityManager()) {
            TypedQuery<Hotel> q = em.createQuery("select h FROM Hotel  h", Hotel.class);
            List<Hotel> hotels = q.getResultList();


            return hotels;
        }
    }

    @Override
    public Hotel getById(int id) {// parameter for selecting a sepcific hotel based on id
        try (var em = emf.createEntityManager()) {
            TypedQuery<Hotel> q = em.createQuery("FROM Hotel h WHERE h.id = :id", Hotel.class);
            q.setParameter("id", id);
            return q.getSingleResult();
        }
    }

    @Override
    public Hotel update(Hotel hotel) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(hotel);
            em.getTransaction().commit();
        }
        return hotel;
    }
    public Collection<Object> getHotelRooms(int id) {
        try(var em = emf.createEntityManager()){
            TypedQuery<Object> q = em.createQuery("SELECT r From Hotel h JOIN h.rooms r WHERE h.id = :id", Object.class);
            q.setParameter("id", id);
            return q.getResultList();
        }
    }
}


