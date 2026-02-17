package pl.hotel.reservation;

import pl.hotel.utils.CommonRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    private Connection connection;

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private CommonRepository commonRepository;

    public ReservationRepository(Connection connection) {
        this.connection = connection;
        this.commonRepository = new CommonRepository(connection);
    }

    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation WHERE 1=1 AND delete_date IS NULL";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                reservations.add(new Reservation(
                        resultSet.getInt("id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getDate("start_reservation_date").toLocalDate(),
                        resultSet.getDate("end_reservation_date").toLocalDate(),
                        resultSet.getInt("room_number"),
                        resultSet.getBigDecimal("total_amount"),
                        resultSet.getBigDecimal("deposit"),
                        resultSet.getBoolean("is_full_paid"),
                        resultSet.getInt("employer_id"),
                        resultSet.getTimestamp("create_reservation_date").toLocalDateTime()
                ));
            }
            System.out.println("Pobrano " + reservations.size() + " rezerwacji.");
            return reservations;

        } catch (SQLException e) {
            System.err.println("Błąd pobierania danych: " + e.getMessage()); {}
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
        return null;
    }

    public Reservation create(Reservation reservation) {
        String query = "INSERT INTO reservation(customer_id, start_reservation_date, end_reservation_date, room_number, total_amount, deposit, is_full_paid, employer_id, create_reservation_date) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try{
            LocalDateTime createReservationDate = LocalDateTime.now();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, reservation.getCustomerId());
            preparedStatement.setDate(2, Date.valueOf(reservation.getStartReservationDate()));
            preparedStatement.setDate(3, Date.valueOf(reservation.getEndReservationDate()));
            preparedStatement.setInt (4,reservation.getRoomNumber());
            preparedStatement.setBigDecimal(5, reservation.getTotalAmount());
            preparedStatement.setBigDecimal(6, reservation.getDeposit());
            preparedStatement.setBoolean(7, reservation.isFullPaid());
            preparedStatement.setInt(8, reservation.getEmployerId());
            preparedStatement.setTimestamp(9, Timestamp.valueOf(createReservationDate));
            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    reservation.setId(generatedId);
                    reservation.setCreateReservationDate(createReservationDate);
                    System.out.println("Utworzono rezerwację o numerze: " + generatedId);
                    return reservation;
                }
            }
        } catch(SQLException e) {
            System.err.println("Błąd przy dodawaniu rezerwacji: " + e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
        }
        return reservation;
    }

    public Reservation update(Reservation reservation){
        try{
            LocalDateTime updateDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            String query = "UPDATE reservation SET customer_id = ?, start_reservation_date = ?, end_reservation_date = ?, room_number = ?, total_amount = ?, deposit = ?, is_full_paid = ?, " +
                    "employer_id = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reservation.getCustomerId());
            preparedStatement.setDate(2, Date.valueOf(reservation.getStartReservationDate()));
            preparedStatement.setDate(3, Date.valueOf(reservation.getEndReservationDate()));
            preparedStatement.setInt(4, reservation.getRoomNumber());
            preparedStatement.setBigDecimal(5, reservation.getTotalAmount());
            preparedStatement.setBigDecimal(6, reservation.getDeposit());
            preparedStatement.setBoolean(7, reservation.isFullPaid());
            preparedStatement.setInt(8, reservation.getEmployerId());
            preparedStatement.setInt(9, reservation.getId());
            preparedStatement.executeUpdate();
            reservation.setUpdateDate(updateDate);
            System.out.println("Dane rezerwacji zostały zaktualizowane.");
            return reservation;
        } catch(SQLException e) {
            System.err.println("Błąd przy aktualizacji rezerwacji: " + e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
        }
        return null;
    }

    public void delete(int id){
        try{
            String query = "UPDATE reservation SET delete_date = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Rezerwacja została usunięta.");
        } catch(SQLException e) {
            System.err.println("Nie usunięto wartości z tablicy reservation.");
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    public Reservation get(ReservationFilter reservationFilter) {

        try {
            String query = getString(reservationFilter);

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            Reservation reservation = null;
            if(resultSet.next()) {

                reservation = new Reservation(
                        resultSet.getInt("id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getDate("start_reservation_date").toLocalDate(),
                        resultSet.getDate("end_reservation_date").toLocalDate(),
                        resultSet.getInt("room_number"),
                        resultSet.getBigDecimal("total_amount"),
                        resultSet.getBigDecimal("deposit"),
                        resultSet.getBoolean("is_full_paid"),
                        resultSet.getInt("employer_id"),
                        resultSet.getTimestamp("create_reservation_date").toLocalDateTime()
                );
            }

            return reservation;
        } catch (SQLException e) {
            System.err.println("Błąd pobierania danych: " + e.getMessage());
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
        return null;
    }

    private static String getString(ReservationFilter reservationFilter) {
        String query = "SELECT * FROM reservation ";
        if(reservationFilter != null) {
            query += "WHERE 1=1 AND delete_date IS NULL ";
            if(reservationFilter.getId() != 0) {
                query += "AND id = '" + reservationFilter.getId() + "' ";
            }
            if(reservationFilter.getCustomerId() != 0) {
                query += "AND customer_id = '" + reservationFilter.getCustomerId() + "' ";
            }
            if(reservationFilter.getStartReservationDate() != null) {
                query += "AND start_reservation_date = '" + reservationFilter.getStartReservationDate() + "' ";
            }
            if(reservationFilter.getEndReservationDate() != null) {
                query += "AND end_reservation_date = '" + reservationFilter.getEndReservationDate() + "' ";
            }
            if(reservationFilter.getTotalAmount() != null) {
                query += "AND total_amount = '" + reservationFilter.getTotalAmount() + "' ";
            }
            if(reservationFilter.getDeposit() != null) {
                query += "AND deposit = '" + reservationFilter.getDeposit() + "' ";
            }
            if(reservationFilter.isFullPaid()) {
                query += "AND is_full_paid = '" + reservationFilter.isFullPaid() + "' ";
            }
            if(reservationFilter.getEmployerId() != 0) {
                query += "AND employer_id = '" + reservationFilter.getEmployerId() + "' ";
            }
            if(reservationFilter.getCreatedDate() != null) {
                query += "AND create_reservation_date = '" + reservationFilter.getCreatedDate() + "' ";
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