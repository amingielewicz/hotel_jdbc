package pl.hotel.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonRepository {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public CommonRepository(Connection connection) {
        this.connection = connection;
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
