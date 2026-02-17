package pl.hotel.customer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.hotel.room.Room;
import pl.hotel.room.RoomFilter;
import pl.hotel.room.RoomService;
import pl.hotel.room.RoomServiceInterface;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RoomServiceTest {

    private static Connection connection = null;
    private static Statement statement = null;
    private static RoomServiceInterface roomService;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/test", "root", "root");
            statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS room");

            String sql = "CREATE TABLE room" +
                    "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "room_number INTEGER NOT NULL, " +
                    "room_size VARCHAR(20), " +
                    "equipment VARCHAR(50)," +
                    "price DECIMAL(8,2)," +
                    "create_date DATE NOT NULL, " +
                    "update_date DATETIME, " +
                    "delete_date DATETIME" +
                    ") ";
            statement.execute(sql);
            roomService = new RoomService(connection);
            System.out.println("Połączono z Bazą Danych.");

        } catch (SQLException e) {
            System.err.println("Nie połączono z Bazą Danych " + e);
        }
    }

    @Test
    void create() {
        //When
        Room room = new Room(110, "M", "czajnik, TV, klimatyzacja", BigDecimal.valueOf(220.00), LocalDate.of(2025, 11, 11));
        Room secondRoom = new Room(111, "L", "czajnik, TV", BigDecimal.valueOf(120.00), LocalDate.of(2025, 11, 12));
        Room createRoom = roomService.create(room);
        Room createSecondRoom = roomService.create(secondRoom);

        Map<Integer, Room> createRooms = new HashMap<>();
        createRooms.put(createRoom.getRoomNumber(), createRoom);
        createRooms.put(createSecondRoom.getRoomNumber(), createSecondRoom);

        //Given
        Map<Integer, Room> sqlRooms = new HashMap<>();
        List<Room> results = roomService.findAll();
        for (Room result : results) {
            sqlRooms.put(result.getRoomNumber(), result);
        }

        //Then
        for (Map.Entry<Integer, Room> resultRoom : createRooms.entrySet()) {
            Room actual = sqlRooms.get(resultRoom.getKey());
            Room expected = resultRoom.getValue();
            assertEquals(expected.getId(), actual.getId(), "Id room pobranego z sql jest inny niż stworzony room.");
            assertEquals(expected.getRoomNumber(), actual.getRoomNumber());
            assertEquals(expected.getRoomSize(), actual.getRoomSize());
        }
    }

    @Test
    void findAll() {
        //When
        Room room = new Room(110, "M", "czajnik, TV, klimatyzacja", BigDecimal.valueOf(220.00), LocalDate.of(2025, 11, 11));
        Room secondRoom = new Room(111, "L", "czajnik, TV", BigDecimal.valueOf(120.00), LocalDate.of(2025, 11, 12));
        Room createRoom = roomService.create(room);
        Room createSecondRoom = roomService.create(secondRoom);

        Map<Integer, Room> createRooms = new HashMap<>();
        createRooms.put(createRoom.getRoomNumber(), createRoom);
        createRooms.put(createSecondRoom.getRoomNumber(), createSecondRoom);

        //Given
        Map<Integer, Room> sqlRooms = new HashMap<>();
        List<Room> results = roomService.findAll();
        for (Room result : results) {
            sqlRooms.put(result.getRoomNumber(), result);
        }

        //Then
        for (Map.Entry<Integer, Room> resultRoom : createRooms.entrySet()) {
            Room actual = sqlRooms.get(resultRoom.getKey());
            Room expected = resultRoom.getValue();
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getRoomNumber(), actual.getRoomNumber());
            assertEquals(expected.getRoomSize(), actual.getRoomSize());
            assertNotEquals(10, actual.getId(), "Id room pobranego z sql jest inny niż stworzony room.");
        }
    }

        @Test
        void getRoom() {
            //When
            Room room = new Room(110, "M", "czajnik, TV, klimatyzacja", BigDecimal.valueOf(220.00), LocalDate.of(2025, 11, 11));
            Room createRoom = roomService.create(room);

            //Given
            Room result = roomService.getRoom(new RoomFilter(110, "M", null));


            //Then
            assertEquals(room.getRoomNumber(), result.getRoomNumber());
            assertEquals(room.getRoomSize(), result.getRoomSize());
            assertEquals(room.getEquipment(), result.getEquipment());
            assertEquals(room.getCreateDate(), result.getCreateDate());
        }

    @Test
    void update() {
        //When
        Room room = new Room(110, "M", "czajnik, TV, klimatyzacja", BigDecimal.valueOf(200.00), LocalDate.of(2025, 11, 11));
        Room secondRoom = new Room(111, "L", "czajnik, TV", BigDecimal.valueOf(120.00), LocalDate.of(2025, 11, 12));
        Room createRoom = roomService.create(room);
        Room createSecondRoom = roomService.create(secondRoom);
        Room updateRoom = roomService.update(new Room(createRoom.getId(), 110, "L", "czajnik, TV, minibar", BigDecimal.valueOf(200.00), LocalDate.of(2025, 11, 11)));

        //Given
        Room result = roomService.getRoom(new RoomFilter(updateRoom.getId(), 0, null, null));

        //Then
        assertEquals(updateRoom.getId(), result.getId());
        assertEquals(updateRoom.getRoomNumber(), result.getRoomNumber());
        assertEquals(updateRoom.getRoomSize(), result.getRoomSize());
        assertEquals(updateRoom.getEquipment(), result.getEquipment());
        assertEquals(0, updateRoom.getPrice().compareTo(result.getPrice()));
        assertEquals(updateRoom.getCreateDate(), result.getCreateDate());
        assertEquals(updateRoom.getUpdateDate(), result.getUpdateDate());
        assertEquals(updateRoom.getDeleteDate(), result.getDeleteDate());
        assertNotEquals(createSecondRoom.getRoomNumber(), result.getRoomNumber());
    }

    @Test()
    void delete() {
        //When
        Room room = new Room(110, "M", "czajnik, TV, klimatyzacja", BigDecimal.valueOf(200.00), LocalDate.of(2025, 11, 11));
        Room secondRoom = new Room(111, "L", "czajnik, TV", BigDecimal.valueOf(120.00), LocalDate.of(2025, 11, 12));
        Room createRoom = roomService.create(room);
        Room createSecondRoom = roomService.create(secondRoom);
        roomService.delete(createRoom.getId());

        //Given
        List <Room> results = roomService.findAll();

        //Then
        assertEquals(1, results.size());
        List<Integer> roomNumberResults = results.stream().map(Room::getRoomNumber).toList();
        assertEquals(secondRoom.getRoomNumber(), createSecondRoom.getRoomNumber());
        List<String> roomSizeResults = results.stream().map(Room::getRoomSize).toList();
        assertEquals(secondRoom.getRoomSize(), createSecondRoom.getRoomSize());
        List<String> equipmentResults = results.stream().map(Room::getEquipment).toList();
        assertEquals(secondRoom.getEquipment(), createSecondRoom.getEquipment());
    }

    @AfterAll
    static void tearDown() {
        try {
            System.out.println("Rozłączanie Bazy Danych.");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Rozłączono Bazę Danych.");
        }
    }
}




