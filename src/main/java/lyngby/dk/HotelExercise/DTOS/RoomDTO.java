package lyngby.dk.HotelExercise.DTOS;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
public class RoomDTO {
    public int getId() {
        return id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public int getNumber() {
        return number;
    }
    @Builder
    public RoomDTO(int id, int hotelId, int number, float price) {
        this.id = id;
        this.hotelId = hotelId;
        this.number = number;
        this.price = price;
    }


    public float getPrice() {
        return price;
    }

    int id;
    int hotelId;
    int number;
    float price;

    public void setId(int id) {
        this.id = id;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
