package lyngby.dk.HotelExercise.DTOS;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
public class HotelDTO {
    int id;
    String name;
    String address;
    List<RoomDTO> rooms;

    public int getId() {
        return id;
    }

    public HotelDTO(int id, String name, String address, List<RoomDTO> rooms) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rooms = rooms;
    }

    @Builder

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }
}
