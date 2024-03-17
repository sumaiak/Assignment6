import jakarta.persistence.EntityManagerFactory;
import lyngby.dk.HotelExercise.DAO.HotelDAO;
import lyngby.dk.HotelExercise.DAO.RoomDAO;
import lyngby.dk.HotelExercise.HibernateConfig.HibernateConfig;
import lyngby.dk.HotelExercise.models.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomTest {
    EntityManagerFactory emf;
    RoomDAO roomDAO;
    @BeforeEach
    void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryConfig();
        roomDAO = new RoomDAO(emf);
    }
    @Test
    void testcreateRoom() {
        Room room = new Room();
        roomDAO.create(room);

        Room room1 = roomDAO.getById(room.getId());
        assertEquals(room, room1);

    }
    @Test
    void testUpdateRoom() {
        Room room = new Room();
        room.setNumber(101);
        room.setPrice(150.00);

        roomDAO.create(room);

        room.setPrice(200.00);

        Room updatedRoom = roomDAO.update(room);


        Room retrievedRoom =roomDAO.getById(room.getId());

        assertEquals(updatedRoom, retrievedRoom);
        assertEquals(200.00, retrievedRoom.getPrice());
    }
}


