package pl.hotel.customer;

import pl.hotel.utils.CommonRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private Connection connection;

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private CommonRepository commonRepository;

    public CustomerRepository(Connection connection) {
        this.connection = connection;
        this.commonRepository = new CommonRepository(connection);
    }

    public List<Customer> findAll(){
        List<Customer> customers = new ArrayList<>();

        try {
            String query = "SELECT * FROM customer WHERE 1=1 AND delete_date IS NULL";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("pesel"),
                        resultSet.getDate("create_date").toLocalDate(),
                        resultSet.getTime("update_date") != null ? resultSet.getTimestamp("update_date").toLocalDateTime() : null,
                        resultSet.getTime("delete_date") != null ? resultSet.getTimestamp("delete_date").toLocalDateTime() : null
                ));
            }
            return customers;
        } catch (SQLException e) {
            System.err.println("Błąd pobierania danych: " + e.getMessage());
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
        return null;
        //TODO stworzyć metodę do while
    }

    public Customer create(Customer customer) {
        try{
            String query = "INSERT INTO customer(name, surname, pesel, create_date) values(?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setString(3, customer.getPesel());
            preparedStatement.setDate(4, Date.valueOf(customer.getCreateDate()));
            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Customer(rs.getInt(1), customer.getName(), customer.getSurname(), customer.getPesel(), customer.getCreateDate()); // to jest Twoje "last insert id"
                }
            }

        } catch(SQLException e) {
            System.err.println("Błąd przy dodawaniu klienta: " + e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
        }
        return customer;
    }

    public Customer update(Customer customer){
        try{
            LocalDateTime updateDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            String query = "UPDATE customer SET name = ?, surname = ?, pesel = ?, update_date = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setString(3, customer.getPesel());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(updateDate));
            preparedStatement.setInt(5, customer.getId());
            preparedStatement.executeUpdate();
            customer.setUpdateDate(updateDate);
            System.out.println("Dane zaktualizowano");
            return customer;
        } catch(SQLException e) {
            System.err.println("Błąd przy aktualizacji klienta: " + e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
        }
        return null;
    }

    public void delete(int id){
        try{
            String query = "UPDATE customer SET delete_date = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Dane usunięto");
        } catch(SQLException e) {
            System.err.println("Nie usunięto wartości z tablicy customer.");
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    public Customer get(CustomerFilter customerFilter){

        try {
            String query = getString(customerFilter);

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            Customer customer = null;
            if(resultSet.next()) {

                customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("pesel"),
                        resultSet.getDate("create_date").toLocalDate(),
                        resultSet.getTime("update_date") != null ? resultSet.getTimestamp("update_date").toLocalDateTime() : null,
                        resultSet.getTime("delete_date") != null ? resultSet.getTimestamp("delete_date").toLocalDateTime() : null
                );
            }

            return customer;
        } catch (SQLException e) {
            System.err.println("Błąd pobierania danych: " + e.getMessage());
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
        return null;
    }

    private static String getString(CustomerFilter customerFilter) {
        String query = "SELECT * FROM customer ";
        if(customerFilter != null) {
            query += "WHERE 1=1 AND delete_date IS NULL ";
            if(customerFilter.getId() != 0) {
                query += "AND id = '" + customerFilter.getId() + "' ";
            }
            if(customerFilter.getName() != null) {
                query += "AND name = '" + customerFilter.getName() + "' ";
            }
            if(customerFilter.getSurname() != null) {
                query += "AND surname = '" + customerFilter.getSurname() + "' ";
            }
            if(customerFilter.getPesel() != null) {
                query += "AND pesel = '" + customerFilter.getPesel() + "' ";
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
