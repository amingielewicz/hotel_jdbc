package pl.hotel.customer;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private static Connection connection = null;
    private static Statement statement = null;
    private static CustomerServiceInterface customerService;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/test", "root", "root");
            statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS customer");

            String sql = "CREATE TABLE customer " +
                    "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "surname VARCHAR(50), " +
                    "pesel VARCHAR(20)," +
                    "create_date DATE NOT NULL, " +
                    "update_date DATETIME, " +
                    "delete_date DATETIME" +
                    ") ";
            statement.execute(sql);
            customerService = new CustomerService(connection);
            System.out.println("Połączono z Bazą Danych.");

        } catch (SQLException e) {
            System.err.println("Nie połączono z Bazą Danych " + e);
        }
    }

    @Test
    void create() {
        //When
        Customer customer = new Customer("Tomasz", "Kowalczyk", "82121405945", LocalDate.of(2025, 04, 24));
        Customer secondCustomer = new Customer("Beata", "Nowak", "72120506594", LocalDate.of(1972, 12, 15));
        Customer createCustomer = customerService.create(customer);
        Customer createSecondCustomer = customerService.create(secondCustomer);

        Map<String, Customer> createCustomers = new HashMap<>();
        createCustomers.put(createCustomer.getPesel(), createCustomer);
        createCustomers.put(createSecondCustomer.getPesel(), createSecondCustomer);

        //Given
        Map<String, Customer> sqlCustomers = new HashMap<>();
        List<Customer> results = customerService.findAll();
        for(Customer result : results) {
            sqlCustomers.put(result.getPesel(), result);
        }

        //Then
        for(Map.Entry<String, Customer> resultCustomer : createCustomers.entrySet()) {
            Customer actual = sqlCustomers.get(resultCustomer.getKey());
            Customer expected = resultCustomer.getValue();
            assertEquals(expected.getId(), actual.getId(), "Id customera pobranego z sql jest inny niż stworzony customer.");
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getPesel(), actual.getPesel());
        }
    }

    @Test
    void findAll() {
        //When
        Customer customer = new Customer("Tomasz", "Kowalczyk", "82121405945", LocalDate.of(2025, 04, 24));
        Customer secondCustomer = new Customer("Beata", "Nowak", "72120506594", LocalDate.of(1972, 12, 15));
        Customer fakeCustomer = new Customer("XXXX", "YYYY", "12345678901", LocalDate.of(1111, 11, 11));
        Customer createCustomer = customerService.create(customer);
        Customer createSecondCustomer = customerService.create(secondCustomer);

        Map<String, Customer> createCustomers = new HashMap<>();
        createCustomers.put(createCustomer.getPesel(), createCustomer);
        createCustomers.put(createSecondCustomer.getPesel(), createSecondCustomer);

        //Given
        Map<String, Customer> sqlCustomers = new HashMap<>();
        List<Customer> results = customerService.findAll();
        for(Customer result : results) {
            sqlCustomers.put(result.getPesel(), result);
        }

        //Then
        for(Map.Entry<String, Customer> resultCustomer : createCustomers.entrySet()) {
            Customer actual = sqlCustomers.get(resultCustomer.getKey());
            Customer expected = resultCustomer.getValue();
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getPesel(), actual.getPesel());
            assertNotEquals(fakeCustomer.getId(), actual.getId(), "Id customera pobranego z sql jest inny niż stworzony customer.");
        }
    }

    @Test
    void getCustomer() {
        //When
        Customer customer = new Customer("Tomasz", "Kowalczyk", "82121405945", LocalDate.of(2025, 4, 24));
        Customer createCustomer = customerService.create(customer);

        //Given
        Customer result = customerService.getCustomer(new CustomerFilter("Tomasz", null, null));

        //Then
        assertEquals(customer.getName(), result.getName());
        assertEquals(customer.getPesel(), result.getPesel());
        assertEquals(customer.getSurname(), result.getSurname());
        assertEquals(customer.getCreateDate(), result.getCreateDate());
    }

    @Test
    void update() {
        //When
        Customer customer = new Customer("Tomasz", "Kowalczyk", "82121405945", LocalDate.of(2025, 4, 24));
        Customer secondCustomer = new Customer("Beata", "Marek", "8984541254", LocalDate.of(2024, 12, 11));
        Customer createCustomer = customerService.create(customer);
        Customer createSecondCustomer = customerService.create(secondCustomer);
        Customer updateCustomer = customerService.update(new Customer(createCustomer.getId(), "Tomasz", "Nowy", "0000000001"));

        //Given
        Customer result = customerService.getCustomer(new CustomerFilter(updateCustomer.getId(), null, null, null));

        //Then
        assertEquals(updateCustomer.getId(), result.getId());
        assertEquals(updateCustomer.getName(), result.getName());
        assertEquals(updateCustomer.getSurname(), result.getSurname());
        assertEquals(updateCustomer.getPesel(), result.getPesel());
        assertEquals(updateCustomer.getUpdateDate(), result.getUpdateDate());
        assertNotEquals(createSecondCustomer.getSurname(), result.getSurname());

    }

    @Test()
    void delete() {
        //When
        Customer customer = new Customer("Tomasz", "Kowalczyk", "82121405945", LocalDate.of(2025, 4, 24));
        Customer secondCustomer = new Customer("Beata", "Marek", "8984541254", LocalDate.of(2024, 12, 11));
        Customer createCustomer = customerService.create(customer);
        Customer createSecondCustomer = customerService.create(secondCustomer);
        customerService.delete(createCustomer.getId());

        //Given
        List <Customer> results = customerService.findAll();

        //Then
        assertEquals(1, results.size());
        List<String> namesResults = results.stream().map(Customer::getName).toList();
        assertEquals(secondCustomer.getName(), createSecondCustomer.getName());
        List<String> surnameResults = results.stream().map(Customer::getSurname).toList();
        assertEquals(secondCustomer.getSurname(), createSecondCustomer.getSurname());
        List<String> peselResults = results.stream().map(Customer::getPesel).toList();
        assertEquals(secondCustomer.getPesel(), createSecondCustomer.getPesel());
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