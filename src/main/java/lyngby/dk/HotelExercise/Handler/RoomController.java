package lyngby.dk.HotelExercise.Handler;

import io.javalin.http.Handler;
import lyngby.dk.HotelExercise.DAO.HotelDAO;
import lyngby.dk.HotelExercise.DAO.RoomDAO;
import lyngby.dk.HotelExercise.DTOS.RoomDTO;
import lyngby.dk.HotelExercise.models.Hotel;
import lyngby.dk.HotelExercise.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomController {

    public static Handler getAll(RoomDAO dao) {
        return ctx -> {
            List<Room> rooms = dao.getAll();
            if (rooms.isEmpty()) {
                ctx.status(404).result("No Room available");
            } else {
                List<RoomDTO> roomDTOs = new ArrayList<>();

                for (Room room : rooms) {
                    RoomDTO roomDTO = new RoomDTO();
                    roomDTO.setId(room.getId());
                    roomDTO.setHotelId(room.getHotel().getId());
                    roomDTO.setNumber(room.getNumber());
                    roomDTO.setPrice((float) room.getPrice());
                    roomDTOs.add(roomDTO);
                }

                ctx.json(roomDTOs);
            }
        };
    }

    public static Handler getRoom(RoomDAO dao) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Room room = dao.getById(id);
            if (room != null) {
                RoomDTO roomDTO = new RoomDTO();
                roomDTO.setId(room.getId());
                roomDTO.setHotelId(room.getHotel().getId());
                roomDTO.setPrice((float) room.getPrice());
                roomDTO.setNumber(room.getNumber());
                ctx.json(roomDTO);
            } else {
                ctx.status(404).result("The id you are looking for does not exist");
            }
        };
    }


    public static Handler create(RoomDAO dao, HotelDAO hdao) {
        return ctx -> {
            int hotelId = Integer.parseInt(ctx.pathParam("hotelId"));
            Room room = ctx.bodyAsClass(Room.class);
            Hotel hotel = hdao.getById(hotelId);
            if (hotel != null) {
                hotel.addRoom(room);
                hdao.update(hotel);
                if (room != null) {

                    RoomDTO roomDTO = new RoomDTO();
                    roomDTO.setId(room.getId());
                    roomDTO.setHotelId(room.getHotel().getId());
                    roomDTO.setNumber(room.getNumber());
                    roomDTO.setPrice((float) room.getPrice());
                    ctx.status(201).json(roomDTO);
                } else {

                    ctx.status(404).result("Hotel not found");
                }
            }

        };
    }

    public static Handler update(RoomDAO dao) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            RoomDTO dto = ctx.bodyAsClass(RoomDTO.class);
            dto.setId(id);
            Room r = dao.getById(id);
            r.setPrice(dto.getPrice());
            r.setNumber(dto.getNumber());
            //updating database
            dao.update(r);
            ctx.json(dto);
        };
    }
}