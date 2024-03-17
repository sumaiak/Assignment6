import jakarta.persistence.EntityManagerFactory;
import lyngby.dk.HotelExercise.DAO.HotelDAO;
import lyngby.dk.HotelExercise.HibernateConfig.HibernateConfig;
import lyngby.dk.HotelExercise.models.Hotel;
import lyngby.dk.HotelExercise.models.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HotelDAOTest {
    EntityManagerFactory emf;
    HotelDAO hotelDAO;

    @BeforeEach
    void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryConfig();
        hotelDAO = new HotelDAO(emf);
    }
    @Test
    void testAddAndRetrieveHotel() {
        Hotel hotel = new Hotel();
        hotelDAO.create(hotel);

        Hotel hotel1 = hotelDAO.getById(hotel.getId());
        assertEquals(hotel, hotel1);
    }
    @Test
    void testUpdateHotel() {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel scandic");
        hotel.setAddress("sydhavn");
        hotelDAO.create(hotel);

        hotel.setName("Hotel bella");
        hotel.setAddress("slsueholmen");

        hotelDAO.update(hotel);
        Hotel retrievedHoteldb = hotelDAO.getById(hotel.getId());
        assertEquals(hotel, retrievedHoteldb);


    }
    @Test
    void testGetHotelRooms() {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel 1");
        hotel.setAddress("Street 1");
        hotelDAO.create(hotel);


        Room room1 = new Room();
        room1.setNumber(102);
        room1.setPrice(200.0f);
        hotel.addRoom(room1);
        Room room2 = new Room();
        room2.setNumber(104);
        room2.setPrice(200.0f);
        hotel.addRoom(room2);
        hotelDAO.update(hotel);

        assertEquals(2, hotelDAO.getHotelRooms(hotel.getId()).size());

    }


    @Test
    void testDeleteHotel() {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel 1");
        hotel.setAddress("Street 1");
        hotelDAO.create(hotel);


        hotelDAO.delete(hotel);
        assertNull(hotelDAO.getById(hotel.getId()));


    }



}
