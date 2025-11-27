package pl.hotel.room;

import java.math.BigDecimal;

public class RoomFilter {
    private int id;
    private int roomNumber;
    private String roomSize;
    private String equipment;
    private BigDecimal price;

    public RoomFilter(int id, int roomNumber, String roomSize, String equipment) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.equipment = equipment;
    }

    public RoomFilter(int roomNumber, String roomSize, String equipment) {
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.equipment = equipment;
    }

    public RoomFilter(int id, int roomNumber, String roomSize, String equipment, BigDecimal price) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.equipment = equipment;
        this.price = price;
    }



    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
