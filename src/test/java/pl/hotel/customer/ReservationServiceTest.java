package pl.hotel.customer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.hotel.employee.Employee;
import pl.hotel.employee.EmployeeService;
import pl.hotel.employee.EmployeeServiceInterface;
import pl.hotel.reservation.Reservation;
import pl.hotel.reservation.ReservationFilter;
import pl.hotel.reservation.ReservationService;
import pl.hotel.reservation.ReservationServiceInterface;
import pl.hotel.room.Room;
import pl.hotel.room.RoomService;
import pl.hotel.room.RoomServiceInterface;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationServiceTest {

    private static Connection connection = null;
    private static Statement statement = null;
    private static ReservationServiceInterface reservationService;
    private static CustomerServiceInterface customerService;
    private static RoomServiceInterface roomService;
    private static EmployeeServiceInterface employeeService;
    private Customer customer;
    private Room room;
    private Employee employee;
    private Reservation reservation;
    private Customer secondCustomer;
    private Room secondRoom;
    private Employee secondEmployee;
    private Reservation secondReservation;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/test", "root", "root");
            statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS reservation");

            String reservationSql = "CREATE TABLE reservation" +
                    "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "customer_id INTEGER, " +
                    "start_reservation_date TIMESTAMP, " +
                    "end_reservation_date DATE," +
                    "room_number INTEGER, " +
                    "total_amount DECIMAL, " +
                    "deposit DECIMAL, " +
                    "is_full_paid BOOLEAN, " +
                    "employer_id INTEGER, " +
                    "update_date DATETIME, " +
                    "delete_date DATETIME, " +
                    "create_reservation_date TIMESTAMP" +
                    ") ";
            statement.execute(reservationSql);
            reservationService = new ReservationService(connection);

            statement.execute("DROP TABLE IF EXISTS customer");

            String customerSql = "CREATE TABLE customer " +
                    "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "surname VARCHAR(50), " +
                    "pesel VARCHAR(20)," +
                    "create_date DATE NOT NULL, " +
                    "update_date DATETIME, " +
                    "delete_date DATETIME " +
                    ") ";
            statement.execute(customerSql);
            customerService = new CustomerService(connection);

            statement.execute("DROP TABLE IF EXISTS employee");

            String employeeSql = "CREATE TABLE employee" +
                    "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "surname VARCHAR(50), " +
                    "role VARCHAR(20)," +
                    "personal_skill VARCHAR(20)," +
                    "create_date DATE NOT NULL, " +
                    "update_date DATETIME, " +
                    "delete_date DATETIME " +
                    ") ";
            statement.execute(employeeSql);
            employeeService = new EmployeeService(connection);

            statement.execute("DROP TABLE IF EXISTS room");

            String roomSql = "CREATE TABLE room" +
                    "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "room_number INTEGER NOT NULL, " +
                    "room_size VARCHAR(20), " +
                    "equipment VARCHAR(50)," +
                    "price DECIMAL(8,2)," +
                    "create_date DATE NOT NULL, " +
                    "update_date DATETIME, " +
                    "delete_date DATETIME " +
                    ") ";
            statement.execute(roomSql);
            roomService = new RoomService(connection);

            System.out.println("Połączono z Bazą Danych.");

            Customer createCustomer = new Customer("Tomek", "Wichura", "821105954", LocalDate.of(2026, 1, 1));
            customer = customerService.create(createCustomer);

            Room createRoom = new Room(200, "wieloosobowy", "czajnik, TV", BigDecimal.valueOf(10000), LocalDate.of(2025,11,11));
            room = roomService.create(createRoom);

            Employee createEmployee = new Employee("Łukasz", "Wiertara","sprzątacz", "miotła, wiertarka", LocalDate.of(2024,11,1));
            employee = employeeService.create(createEmployee);

            Customer createSecondCustomer = new Customer("Adam", "Piotrkowiak", "790512225", LocalDate.of(2026, 2, 2));
            secondCustomer = customerService.create(createCustomer);

            Room createSecondRoom = new Room(300, "jednoosobowy", "czajnik, TV", BigDecimal.valueOf(2000), LocalDate.of(2025,2,2));
            secondRoom = roomService.create(createRoom);

            Employee createSecondEmployee = new Employee("Krzysztof", "Tarcza","dozorca", "miotła, kilof", LocalDate.of(2024,5,10));
            secondEmployee = employeeService.create(createEmployee);

        } catch (SQLException e) {
            System.err.println("Nie połączono z Bazą Danych " + e);
        }

    }

    @Test
    void create() {
        //When
        Reservation reservation = new Reservation(customer.getId(), LocalDate.of(2026, 01, 01), LocalDate.of(2026, 02, 02), room.getRoomNumber(),
                room.getPrice(), BigDecimal.valueOf(100000), true, employee.getId());
        Reservation createReservation = reservationService.create(reservation);

        //Given
        Reservation reservationResult = reservationService.getReservation(new ReservationFilter(createReservation.getId(), 0, null, null,
                0, null, null, false, 0, null));
        System.out.println(reservationResult);
        //Then
        assertEquals(createReservation.getId(), reservationResult.getId());
        assertEquals(createReservation.getDeposit(), reservationResult.getDeposit());
    }

    @Test
    void findAll() {
        //When
        Reservation reservation = new Reservation(customer.getId(), LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 2), room.getRoomNumber(),
                room.getPrice(), BigDecimal.valueOf(100000), true, employee.getId());
        Reservation createReservation = reservationService.create(reservation);

        Reservation secondReservation = new Reservation(customer.getId(), LocalDate.of(2026, 5, 5), LocalDate.of(2026, 5, 8), room.getRoomNumber(),
                room.getPrice(), BigDecimal.valueOf(120000), true, employee.getId());
        Reservation createSecondReservation = reservationService.create(secondReservation);

        //Given
        List<Reservation> reservationResultList = reservationService.findAll();

        //Then
        assertEquals(2,  reservationResultList.size());
        List<BigDecimal> depositResults = reservationResultList.stream().map(Reservation::getDeposit).toList();
        Assertions.assertTrue(depositResults.contains(secondReservation.getDeposit()));
        Assertions.assertTrue(depositResults.contains(reservation.getDeposit()));
        }

    @Test
    void getReservation() {
        //When
        Reservation reservation = new Reservation(customer.getId(), LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 2), room.getRoomNumber(),
                room.getPrice(), BigDecimal.valueOf(100000), true, employee.getId());
        Reservation createReservation = reservationService.create(reservation);

        //Given
        Reservation reservationResult = reservationService.getReservation(new ReservationFilter(0,0, null, null,
                200, null, null, false, 0, null));

        //Then
        assertEquals(reservation.getId(), reservationResult.getId());
        assertEquals(reservation.getRoomNumber(), reservationResult.getRoomNumber());
        assertEquals(reservation.getDeposit(), reservationResult.getDeposit());
        assertEquals(reservation.getStartReservationDate(), reservationResult.getStartReservationDate());
        assertEquals(reservation.getEndReservationDate(), reservationResult.getEndReservationDate());
    }

    @Test
    void update() {
        //When
        Reservation reservation = new Reservation(customer.getId(), LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 2), room.getRoomNumber(),
                room.getPrice(), BigDecimal.valueOf(100000), true, employee.getId());
        Reservation createReservation = reservationService.create(reservation);
        Reservation secondReservation = new Reservation(customer.getId(), LocalDate.of(2026, 5, 5), LocalDate.of(2026, 5, 8), room.getRoomNumber(),
                room.getPrice(), BigDecimal.valueOf(120000), true, employee.getId());
        Reservation createSecondReservation = reservationService.create(secondReservation);

        //Given
        Reservation result = reservationService.getReservation(new ReservationFilter(createReservation.getId(), 0, null, null, 0, null, null, false
                , 0, null));
        Reservation updateReservation = reservationService.update(new Reservation(createReservation.getId(), 0, LocalDate.of(2025, 12, 1), LocalDate.of(2026, 2, 2), room.getRoomNumber(),
                room.getPrice(), BigDecimal.valueOf(100000), true, employee.getId(), null));
        Reservation updateResult = reservationService.getReservation(new ReservationFilter(createReservation.getId(), 0, null, null, 0, null, null, false
                , 0, null));

        //Then
        assertEquals(updateReservation.getId(), updateResult.getId());
        assertEquals(updateReservation.getStartReservationDate(), updateResult.getStartReservationDate());
    }

    @Test()
    void delete() {
        //When
        Reservation reservation = reservationService.create(new Reservation(customer.getId(), LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 2), room.getRoomNumber(),
                room.getPrice(), BigDecimal.valueOf(100000), true, employee.getId()));
        Reservation secondReservation = reservationService.create(new Reservation(customer.getId(), LocalDate.of(2025, 2, 15), LocalDate.of(2026, 2, 2), room.getRoomNumber(),
                room.getPrice(), BigDecimal.valueOf(100000), true, employee.getId()));
        reservationService.delete(reservation.getId());

        //Given
        List <Reservation> results = reservationService.findAll();

        //Then
        assertEquals(1, results.size());
        List<Integer> idResults = results.stream().map(Reservation::getId).toList();
        assertEquals(secondReservation.getId(), secondReservation.getId());
        List<LocalDate> startDateResults = results.stream().map(Reservation::getStartReservationDate).toList();
        assertEquals(secondReservation.getStartReservationDate(), secondReservation.getStartReservationDate());
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
