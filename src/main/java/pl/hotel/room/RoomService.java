package pl.hotel.room;


import java.sql.Connection;
import java.util.List;

public class RoomService implements RoomServiceInterface {

    private Connection connection;
    private RoomRepository roomRepository;

    public RoomService(Connection connection) {
        this.connection = connection;
        roomRepository = new RoomRepository(connection);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room create(Room room){
        return roomRepository.create(room);
    }
    public Room update(Room room){
        return roomRepository.update(room);
    }

    public void delete(int id){
        roomRepository.delete(id);
    }

    public Room getRoom(RoomFilter roomFilter) {
        return roomRepository.get(roomFilter);
    }

}
