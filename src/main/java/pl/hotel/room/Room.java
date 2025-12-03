package pl.hotel.room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Room {

    private int id;
    private int roomNumber;
    private String roomSize;
    private String equipment;
    private BigDecimal price;
    private LocalDate createDate;
    private LocalDateTime updateDate;
    private LocalDateTime deleteDate;

    public Room(int id, int roomNumber, String roomSize, String equipment, BigDecimal price, LocalDate createDate, LocalDateTime updateDate, LocalDateTime deleteDate) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.equipment = equipment;
        this.price = price;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.deleteDate = deleteDate;
    }

    public Room(int id, int roomNumber, String roomSize, String equipment, BigDecimal price, LocalDate createDate) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.equipment = equipment;
        this.price = price;
        this.createDate = createDate;
    }

    public Room(int roomNumber, String roomSize, String equipment, BigDecimal price, LocalDate createDate) {
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.equipment = equipment;
        this.price = price;
        this.createDate = createDate;
    }

    public Room(int roomNumber, String roomSize, String equipment, BigDecimal price, LocalDateTime updateDate, int id) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.equipment = equipment;
        this.price = price;
        this.updateDate = updateDate;
    }

    public Room(int roomNumber, String roomSize, String equipment, BigDecimal price) {
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.equipment = equipment;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDateTime getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }
}
