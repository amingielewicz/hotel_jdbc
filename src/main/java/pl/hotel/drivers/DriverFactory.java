package pl.hotel.drivers;

import pl.hotel.customer.CustomerRepository;
import pl.hotel.customer.CustomerService;
import pl.hotel.customer.CustomerServiceInterface;
import pl.hotel.employee.*;
import pl.hotel.reservation.Reservation;
import pl.hotel.reservation.ReservationRepository;
import pl.hotel.reservation.ReservationService;
import pl.hotel.reservation.ReservationServiceInterface;
import pl.hotel.room.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;


public class DriverFactory {

    private static final String URL = ConfigLoader.getProperty("url");
    private static final String USER = "root";
    private static final String PASSWORD = ConfigLoader.getProperty("password");
    private static Connection connection = null;


    public static void installJdbcDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Sterowniki bazy danych zainicjalizowane.");
        } catch (Exception e) {
            System.err.println("Problem z inicjalizacją sterowników bazy danych" + e);
        }
    }

    public static void DbConnection() {
        try {
            System.out.println("Połączono z Bazą Danych.");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            CustomerRepository customerRepository = new CustomerRepository(connection);
            CustomerServiceInterface customerService = new CustomerService(connection);
            EmployeeRepository employeeRepository = new EmployeeRepository(connection);
            EmployeeServiceInterface employeeService = new EmployeeService(connection);
            RoomRepository roomRepository = new RoomRepository(connection);
            RoomServiceInterface roomService = new RoomService(connection);
            ReservationRepository reservationRepository = new ReservationRepository(connection);
            ReservationServiceInterface reservationService = new ReservationService(connection);

            // ## CUSTOMER GET
            //Customer customer = customerRepository.get(new CustomerFilter(null, null, null));
//            System.out.println(customer.getId() + " "
//                    + customer.getName() + " " + customer.getSurname() + " " + customer.getPesel());

            // ## CUSTOMER CREATE
//            Customer customer = customerService.create(new Customer("Karolina", "Kowalik", "80121205950", LocalDate.of(2025, 04, 15)));
//            System.out.println("Dane klienta: " + customer.getId());

            // ## CUSTOMER UPDATE
            //customerRepository.update(new Customer("Joanna", "Majer", "60121505698"),4);

            // ## CUSTOMER DELETE
//            customerRepository.delete(3);

            // ## CUSTOMER FIND ALL
//            customerService.findAll().forEach(customer -> System.out.println(customer.getId() + " "
//                    + customer.getName() + " " + customer.getSurname() + " " + customer.getPesel()));

            // ## EMPLOYEE GET
//            Employee employee = employeeRepository.get(new EmployeeFilter(null, null, null, null));
//            System.out.println(employee.getId() + " " + employee.getName() + " " + employee.getSurname() + " " + employee.getRole() + " " + employee.getPersonalSkill());

            // ## EMPLOYEE CREATE
//            Employee employee = employeeService.create(new Employee("Nowaczewska", "Aneta", "księgowa", "j.ang", LocalDate.of(2025,11, 11)));
//            System.out.println("Dane pracownika: " + employee.getId());

            // ## EMPLOYEE UPDATE
//            employeeService.update(new Employee(2,"PIłsudzka", "Jolanta", "robol", "łopata", LocalDate.of(2025, 9, 15)));

            // ## EMPLOYEE DELETE
//            employeeService.delete(2);

            // ## CUSTOMER FIND ALL
//            employeeService.findAll().forEach(employee -> System.out.println(employee.getId() + " "
//                    + employee.getName() + " " + employee.getSurname() + " " + " " + employee.getRole() + " "
//                    + employee.getPersonalSkill() + " " + employee.getCreateDate()));

            // ## ROOM GET
//            Room room = roomService.get(new RoomFilter(1, 100, null, null));
//           System.out.println(room.getId() + " "
//                   + room.getRoomNumber() + " " + room.getRoomSize() + " " + room.getEquipment());

            //TODO: potrzebował daty w created_date

            // ## ROOM CREATE
//            Room room = roomService.create(new Room(102, "S", "czajnik, TV, klimatyzacja"
//                                    , BigDecimal.valueOf(125) ,LocalDate.of(2025, 04, 15)));

            // ## ROOM UPDATE
//            roomRepository.update(new Room(2, 101, "M", "czajnik, TV, playstation"
//                            , BigDecimal.valueOf(220), LocalDate.of(2025, 11, 11)));

//            // ## ROOM DELETE
//            roomRepository.delete(2);

//             ## ROOM FIND ALL
//            roomService.findAll().forEach(room -> System.out.println("Id: " + room.getId() + " Numer pokoju: "
//                    + room.getRoomNumber() + " Rozmiar pokoju: " + room.getRoomSize() + " Wyposażenie: " + room.getEquipment() + " Cena: " + room.getPrice()
//                    + " Data utworzenia: " + room.getCreateDate() + " Data aktualizacji: " + room.getUpdateDate() + " Data usunięcia: " + room.getDeleteDate()));
            // ## ROOM GET
//            Room room = roomService.get(new RoomFilter(1, 100, null, null));
//           System.out.println(room.getId() + " "
//                   + room.getRoomNumber() + " " + room.getRoomSize() + " " + room.getEquipment());

            //TODO: potrzebował daty w created_date

            // ## RESERVATION CREATE
//            Reservation reservation = reservationService.create(new Reservation(4, LocalDate.of(2025, 1 , 15),
//                                     LocalDate.of(2025, 2, 10), 103, new BigDecimal("200.00"), new BigDecimal("200.00"), true, 12));

            // ## RESERVATION UPDATE
//            reservationRepository.update(new Reservation(4, 2, LocalDate.of(2025, 12, 15), LocalDate.of(2025, 12, 17)
//                            , 104, new BigDecimal("110.00"), new BigDecimal("100"), false, 1));

//            // ## RESERVATION DELETE
//            reservationRepository.delete(6);

//             ## RESERVATION FIND ALL
//            reservationService.findAll().forEach(reservations -> System.out.println("Numer rezerwacji: " + reservations.getId() + " Numer klienta: " + reservations.getCustomerId()
//                    + " Okres rezerwacji (od):" + reservations.getStartReservationDate() + " (do): "
//                    + reservations.getEndReservationDate() + " Numer pokoju: " + reservations.getRoomNumber() + " Cena pokoju: " + reservations.getTotalAmount()
//                    + " Wpłacona kwota: " + reservations.getDeposit() + " Opłacono: " + (reservations.isFullPaid() ? "TAK" : "NIE") + " Id pracownika: "
//                    + reservations.getEmployerId()));

            //TODO: potrzebował daty w created_date


        } catch (SQLException e) {
            System.err.println("Nie połączono z Bazą Danych" + e);
        } finally {
            closeConnection(connection);
        }
    }

    private static void closeConnection(Connection connection) {
        try{
            connection.close();
        }catch(SQLException e){
            System.err.println("Rozłączono Bazę Danych.");
        }
    }
}
