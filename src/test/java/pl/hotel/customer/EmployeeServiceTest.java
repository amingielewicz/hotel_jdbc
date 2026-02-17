package pl.hotel.customer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.hotel.employee.Employee;
import pl.hotel.employee.EmployeeFilter;
import pl.hotel.employee.EmployeeService;
import pl.hotel.employee.EmployeeServiceInterface;

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

public class EmployeeServiceTest {

    private static Connection connection = null;
    private static Statement statement = null;
    private static EmployeeServiceInterface employeeService;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/test", "root", "root");
            statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS employee");

            String sql = "CREATE TABLE employee" +
                    "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "surname VARCHAR(50), " +
                    "role VARCHAR(20)," +
                    "personal_skill VARCHAR(20)," +
                    "create_date DATE NOT NULL, " +
                    "update_date DATETIME, " +
                    "delete_date DATETIME" +
                    ") ";
            statement.execute(sql);
            employeeService = new EmployeeService(connection);
            System.out.println("Połączono z Bazą Danych.");

        } catch (SQLException e) {
            System.err.println("Nie połączono z Bazą Danych " + e);
        }
    }

    @Test
    void create() {
        //When
        Employee employee = new Employee("Karol", "Nowacki", "kiero", "angielski", LocalDate.of(2025, 10, 8));
        Employee secondEmployee = new Employee("Krystian", "Sadowski", "sprzątacz", "niemiecki", LocalDate.of(2025, 10, 4));
        Employee createEmployee = employeeService.create(employee);
        Employee createSecondEmployee = employeeService.create(secondEmployee);

        Map<String, Employee> createEmployees = new HashMap<>();
        createEmployees.put(createEmployee.getSurname(), createEmployee);
        createEmployees.put(createSecondEmployee.getSurname(), createSecondEmployee);

        //Given
        Map<String, Employee> sqlEmployees = new HashMap<>();
        List<Employee> results = employeeService.findAll();
        for (Employee result : results) {
            sqlEmployees.put(result.getSurname(), result);
        }

        //Then
        for (Map.Entry<String, Employee> resultEmployee : createEmployees.entrySet()) {
            Employee actual = sqlEmployees.get(resultEmployee.getKey());
            Employee expected = resultEmployee.getValue();
            assertEquals(expected.getId(), actual.getId(), "Id eployee pobranego z sql jest inny niż stworzony employee.");
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getSurname(), actual.getSurname());
        }
    }

    @Test
    void findAll() {
        //When
        Employee employee = new Employee("Tomasz", "Kowalczyk", "kiero", "niemiecki", LocalDate.of(2025, 04, 24));
        Employee secondEmployee = new Employee("Beata", "Nowak", "sprzątacz", "angielski",  LocalDate.of(1972, 12, 15));
        Employee createEmployee = employeeService.create(employee);
        Employee createSecondEmployee = employeeService.create(secondEmployee);

        Map<String, Employee> createEmployees = new HashMap<>();
        createEmployees.put(createEmployee.getSurname(), createEmployee);
        createEmployees.put(createSecondEmployee.getSurname(), createSecondEmployee);

        //Given
        Map<String, Employee> sqlEmployees = new HashMap<>();
        List<Employee> results = employeeService.findAll();
        for(Employee result : results) {
            sqlEmployees.put(result.getSurname(), result);
        }

        //Then
        for(Map.Entry<String, Employee> resultEmployee : createEmployees.entrySet()) {
            Employee actual = sqlEmployees.get(resultEmployee.getKey());
            Employee expected = resultEmployee.getValue();
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getSurname(), actual.getSurname());
           assertNotEquals(10, actual.getId(), "Id employee pobranego z sql jest inny niż stworzony employee.");
        }
    }

    @Test
    void getEmployee() {
        //When
        Employee employee = new Employee("Tomasz", "Kowalczyk", "kiero", "duński", LocalDate.of(2025, 4, 24));
        Employee createEmployee = employeeService.create(employee);

        //Given
        Employee result = employeeService.getEmployee(new EmployeeFilter("Tomasz", "Kowalczyk", null, null));

        //Then
        assertEquals(employee.getName(), result.getName());
        assertEquals(employee.getSurname(), result.getSurname());
        assertEquals(employee.getRole(), result.getRole());
        assertEquals(employee.getCreateDate(), result.getCreateDate());
    }

    @Test
    void update() {
        //When
        Employee employee = new Employee("Tomasz", "Kowalczyk", "kierownik", "angielski", LocalDate.of(2025, 4, 24));
        Employee secondEmployee = new Employee("Beata", "Marek", "sprzątacz", "niemiecki",  LocalDate.of(2024, 12, 11));
        Employee createEmployee = employeeService.create(employee);
        Employee createSecondEmployee = employeeService.create(secondEmployee);
        Employee updateEmployee = employeeService.update(new Employee(createEmployee.getId(), "Tomasz", "Nowy", "informatyk", "duński", LocalDate.of(2025, 4, 24)));

        //Given
        Employee result = employeeService.getEmployee(new EmployeeFilter(updateEmployee.getId(), null, null, null, null));

        //Then
        assertEquals(updateEmployee.getId(), result.getId());
        assertEquals(updateEmployee.getName(), result.getName());
        assertEquals(updateEmployee.getSurname(), result.getSurname());
        assertEquals(updateEmployee.getRole(), result.getRole());
        assertEquals(updateEmployee.getPersonalSkill(), result.getPersonalSkill());
        assertEquals(updateEmployee.getCreateDate(), result.getCreateDate());
        assertEquals(updateEmployee.getUpdateDate(), result.getUpdateDate());
        assertNotEquals(createSecondEmployee.getSurname(), result.getSurname());
    }

    @Test()
    void delete() {
        //When
        Employee employee = new Employee("Tomasz", "Kowalczyk", "kiero", "angielski", LocalDate.of(2025, 4, 24));
        Employee secondEmployee = new Employee("Beata", "Marek", "sprzątacz", "niemiecki",  LocalDate.of(2024, 12, 11));
        Employee createEmployee = employeeService.create(employee);
        Employee createSecondEmployee = employeeService.create(secondEmployee);
        employeeService.delete(createEmployee.getId());

        //Given
        List <Employee> results = employeeService.findAll();

        //Then
        assertEquals(1, results.size());
        List<String> namesResults = results.stream().map(Employee::getName).toList();
        assertEquals(secondEmployee.getName(), createSecondEmployee.getName());
        List<String> surnameResults = results.stream().map(Employee::getSurname).toList();
        assertEquals(secondEmployee.getSurname(), createSecondEmployee.getSurname());
        List<String> roleResults = results.stream().map(Employee::getSurname).toList();
        assertEquals(secondEmployee.getSurname(), createSecondEmployee.getSurname());
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
