package pl.hotel.employee;

import pl.hotel.utils.CommonRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private Connection connection;

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private CommonRepository commonRepository;

    public EmployeeRepository(Connection connection) {
        this.connection = connection;
        this.commonRepository = new CommonRepository(connection);
    }

    public List<Employee> findAll(){
        List<Employee> employee = new ArrayList<>();

        try {
            String query = "SELECT * FROM employee WHERE 1=1 AND delete_date IS NULL";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                employee.add(new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role"),
                        resultSet.getString("personalSkill"),
                        resultSet.getDate("create_date").toLocalDate(),
                        resultSet.getTime("update_date") != null ? resultSet.getTimestamp("update_date").toLocalDateTime() : null,
                        resultSet.getTime("delete_date") != null ? resultSet.getTimestamp("delete_date").toLocalDateTime() : null
                ));
            }
            return employee;
        } catch (SQLException e) {
            System.err.println("Błąd pobierania danych: " + e.getMessage());
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
        return null;
        //TODO stworzyć metodę do while
    }

    public Employee create(Employee employee) {
        try{
            String query = "INSERT INTO employee(name, surname, role, personalSkill, create_date) values(?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setString(3, employee.getRole());
            preparedStatement.setString(4, employee.getPersonalSkill());
            preparedStatement.setDate(5, Date.valueOf(employee.getCreateDate()));
            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Employee(rs.getInt(1), employee.getName(), employee.getSurname(), employee.getRole(),
                                        employee.getPersonalSkill(), employee.getCreateDate());
                }
            }

        } catch(SQLException e) {
            System.err.println("Błąd przy dodawaniu klienta: " + e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
        }
        return employee;
    }

    public Employee update(Employee employee){
        try{
            LocalDateTime updateDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            String query = "UPDATE employee SET name = ?, surname = ?, role = ?, personalSkill = ?, update_date = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setString(3, employee.getRole());
            preparedStatement.setString(4, employee.getPersonalSkill());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(updateDate));
            preparedStatement.setInt(6, employee.getId());
            preparedStatement.executeUpdate();
            employee.setUpdateDate(updateDate);
            return employee;
        } catch(SQLException e) {
            System.err.println("Błąd przy aktualizacji klienta: " + e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
            System.out.println("Dane zaktualizowano");
        }
        return null;
    }

    public void delete(int id){
        try{
            String query = "UPDATE employee SET delete_date = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.err.println("Nie usunięto wartości z tablicy employee.");
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    public Employee get(EmployeeFilter employeeFilter){

        try {
            String query = getString(employeeFilter);

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            Employee employee = null;
            if(resultSet.next()) {

                employee = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role"),
                        resultSet.getString("personalSkill"),
                        resultSet.getDate("create_date").toLocalDate(),
                        resultSet.getTime("update_date") != null ? resultSet.getTimestamp("update_date").toLocalDateTime() : null,
                        resultSet.getTime("delete_date") != null ? resultSet.getTimestamp("delete_date").toLocalDateTime() : null
                );
            }

            return employee;
        } catch (SQLException e) {
            System.err.println("Błąd pobierania danych: " + e.getMessage());
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
        return null;
    }

    private static String getString(EmployeeFilter employeeFilter) {
        String query = "SELECT * FROM employee ";
        if(employeeFilter != null) {
            query += "WHERE 1=1 AND delete_date IS NULL ";
            if(employeeFilter.getId() != 0) {
                query += "AND id = '" + employeeFilter.getId() + "' ";
            }
            if(employeeFilter.getName() != null) {
                query += "AND name = '" + employeeFilter.getName() + "' ";
            }
            if(employeeFilter.getSurname() != null) {
                query += "AND surname = '" + employeeFilter.getSurname() + "' ";
            }
            if(employeeFilter.getRole() != null) {
                query += "AND role = '" + employeeFilter.getRole() + "' ";
            }
            if(employeeFilter.getPersonalSkill() != null) {
                query += "AND role = '" + employeeFilter.getPersonalSkill() + "' ";
            }
        }
        return query;
    }

    private void closePreparedStatement(PreparedStatement preparedStatement) {
        try{
            if(preparedStatement != null){
                preparedStatement.close();
            }
        } catch (SQLException e){
            e.getSQLState();
        }
    }

    private void closeResultSet(ResultSet resultSet) {
        try{
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e){
            e.getSQLState();
        }
    }
}
