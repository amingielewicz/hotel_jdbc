package pl.hotel.room;

import pl.hotel.employee.Employee;
import pl.hotel.employee.EmployeeFilter;

import java.util.List;

public interface RoomServiceInterface {
    List<Room> findAll();
    Room create(Room room);
    Room update(Room room);
    void delete(int id);
    Room getRoom(RoomFilter roomFilter);

}
