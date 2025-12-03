package pl.hotel.room;

import pl.hotel.utils.CommonRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private Connection connection;

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private CommonRepository commonRepository;

    public RoomRepository(Connection connection) {
        this.connection = connection;
        this.commonRepository = new CommonRepository(connection);
    }

    public List<Room> findAll(){
        List<Room> rooms = new ArrayList<>();

        try {
            String query = "SELECT * FROM room WHERE 1=1 AND delete_date IS NULL";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                rooms.add(new Room(
                        resultSet.getInt("id"),
                        resultSet.getInt("roomNumber"),
                        resultSet.getString("roomSize"),
                        resultSet.getString("equipment"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("create_date").toLocalDate(),
                        resultSet.getTime("update_date") != null ? resultSet.getTimestamp("update_date").toLocalDateTime() : null,
                        resultSet.getTime("delete_date") != null ? resultSet.getTimestamp("delete_date").toLocalDateTime() : null
                ));
            }
            return rooms;
        } catch (SQLException e) {
            System.err.println("Błąd pobierania danych: " + e.getMessage());
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
        return null;
        //TODO stworzyć metodę do while
    }

    public Room create(Room room) {
        try{
            String query = "INSERT INTO room(roomNumber, roomSize, equipment, price, create_date) values(?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setString(2, room.getRoomSize());
            preparedStatement.setString(3, room.getEquipment());
            preparedStatement.setBigDecimal(4, room.getPrice());
            preparedStatement.setDate(5, Date.valueOf(room.getCreateDate()));
            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Room(rs.getInt(1), room.getRoomNumber(), room.getRoomSize(), room.getEquipment(), room.getPrice(), room.getCreateDate());
                }
            }

        } catch(SQLException e) {
            System.err.println("Błąd przy dodawaniu pokoju: " + e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
        }
        return room;
    }

    public Room update(Room room){
        try{
            LocalDateTime updateDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            String query = "UPDATE room SET roomNumber = ?, roomSize = ?, equipment = ?, price = ?, update_date = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setString(2, room.getRoomSize());
            preparedStatement.setString(3, room.getEquipment());
            preparedStatement.setBigDecimal(4, room.getPrice());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(updateDate));
            preparedStatement.setInt(6, room.getId());
            preparedStatement.executeUpdate();
            room.setUpdateDate(updateDate);
            return room;
        } catch(SQLException e) {
            System.err.println("Błąd przy aktualizacji pokoju: " + e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
            System.out.println("Dane zaktualizowano");
        }
        return null;
    }

    public void delete(int id){
        try{
            String query = "UPDATE room SET delete_date = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.err.println("Nie usunięto wartości z tablicy room.");
        } finally {
            closePreparedStatement(preparedStatement);
            System.out.println("Dane usunięto");
        }
    }

    public Room get(RoomFilter roomFilter){

        try {
            String query = getString(roomFilter);

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            Room room = null;
            if(resultSet.next()) {

                room = new Room(
                        resultSet.getInt("id"),
                        resultSet.getInt("roomNumber"),
                        resultSet.getString("roomSize"),
                        resultSet.getString("equipment"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("create_date").toLocalDate(),
                        resultSet.getTime("update_date") != null ? resultSet.getTimestamp("update_date").toLocalDateTime() : null,
                        resultSet.getTime("delete_date") != null ? resultSet.getTimestamp("delete_date").toLocalDateTime() : null
                );
            }

            return room;
        } catch (SQLException e) {
            System.err.println("Błąd pobierania danych: " + e.getMessage());
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
        return null;
    }

    private static String getString(RoomFilter roomFilter) {
        String query = "SELECT * FROM room ";
        if(roomFilter != null) {
            query += "WHERE 1=1 AND delete_date IS NULL";
            if(roomFilter.getId() != 0) {
                query += " AND id = '" + roomFilter.getId() + "' ";
            }
            if(roomFilter.getRoomNumber() != 0) {
                query += " AND roomNumber = '" + roomFilter.getRoomNumber() + "' ";
            }
            if(roomFilter.getRoomSize() != null) {
                query += " AND roomSize = '" + roomFilter.getRoomSize() + "' ";
            }
            if(roomFilter.getEquipment() != null) {
                query += " AND equipment = '" + roomFilter.getEquipment() + "' ";
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




