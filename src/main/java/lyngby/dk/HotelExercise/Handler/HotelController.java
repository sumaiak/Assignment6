package lyngby.dk.HotelExercise.Handler;

import io.javalin.http.Handler;
import jakarta.transaction.Transactional;
import lyngby.dk.HotelExercise.DAO.HotelDAO;
import lyngby.dk.HotelExercise.DTOS.HotelDTO;
import lyngby.dk.HotelExercise.DTOS.RoomDTO;
import lyngby.dk.HotelExercise.models.Hotel;
import lyngby.dk.HotelExercise.models.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HotelController {

    public static Handler getAll(HotelDAO dao) {
        return ctx -> {
            List<Hotel> hotels = dao.getAll();
            if (hotels.isEmpty()) {
                ctx.status(404).result("No Hotels available");
            } else {
                List<HotelDTO> hotelDTOs = new ArrayList<>();
                for (Hotel hotel : hotels) {
                    HotelDTO hotelDTO = new HotelDTO();
                    hotelDTO.setId(hotel.getId());
                    hotelDTO.setName(hotel.getName());
                    hotelDTO.setAddress(hotel.getAddress());

                    List<RoomDTO> roomDTOs = new ArrayList<>();
                    for (Room room : hotel.getRooms()) {
                        RoomDTO roomDTO = new RoomDTO();
                        roomDTO.setId(room.getId());
                        roomDTO.setHotelId(room.getHotel().getId());
                        roomDTO.setNumber(room.getNumber());
                        roomDTO.setPrice((float) room.getPrice());
                        roomDTOs.add(roomDTO);
                    }
                    hotelDTO.setRooms(roomDTOs);

                    hotelDTOs.add(hotelDTO);
                }
                ctx.json(hotelDTOs);
            }
        };
    }


    public static Handler getHotel(HotelDAO dao) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Hotel hotel = dao.getById(id);
            if (hotel != null) {
                HotelDTO dto = new HotelDTO();
                dto.setId(hotel.getId());
                dto.setName(hotel.getName());
                dto.setAddress(hotel.getAddress());
                ctx.json(dto);
            } else {
                ctx.status(404).result("The id you are looking for does not exist");
            }
        };
    }


    public static Handler create(HotelDAO dao) {
        return ctx -> {
            Hotel hotel = ctx.bodyAsClass(Hotel.class);
            if (hotel != null) {
                dao.create(hotel);
                ctx.json(hotel);
            } else {
                ctx.status(500).result("we couldnt create hotel");
            }
        };
    }

    public static Handler update(HotelDAO dao) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));

            HotelDTO dto = ctx.bodyAsClass(HotelDTO.class);
            dto.setId(id);
            Hotel h = dao.getById(id);
            h.setName(dto.getName());
            h.setAddress(dto.getAddress());
            dao.update(h);
            ctx.json(dto);
        };
    }


    public static Handler getHotelRooms(HotelDAO hDAO) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Hotel hotel = hDAO.getById(id);
            if (hotel != null) {
                List<Room> rooms = hotel.getRooms();
                if (rooms.isEmpty()) {
                    ctx.status(404).result("No rooms available for this hotel");
                } else {
                    List<RoomDTO> roomDtos = rooms.stream().map(room -> RoomDTO.builder()
                            .id(room.getId())
                            .number(room.getNumber())
                            .price((float) room.getPrice())
                            .hotelId(room.getHotel().getId())
                            .build()).toList();
                    ctx.json(roomDtos);
                }
            } else {
                ctx.status(404).result("Hotel not found");
            }
        };
    }
}