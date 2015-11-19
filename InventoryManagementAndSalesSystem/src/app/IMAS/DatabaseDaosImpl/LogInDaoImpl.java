package app.IMAS.DatabaseDaosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import app.IMAS.DatabaseConnection.DatabaseConnection;
import app.IMAS.DatabaseDaos.LogInDao;
import app.IMAS.Enitities.LoginEntity;

public class LogInDaoImpl implements LogInDao {

    private Connection connection = null;
    private PreparedStatement prepareStatement = null;
    private ResultSet result = null;

    @Override
    public LoginEntity getAdmin() {

        LoginEntity adminEntity = new LoginEntity();

        String querry = "Select * from admin";

        try {
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);

            result = prepareStatement.executeQuery();

            while (result.next()) {
                adminEntity.setUserName(result.getString("AdminName"));
                adminEntity.setPassword(result.getString("AdminPassword"));

            }

        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            /*Closing ResultSet*/
            try {
                if (result != null || prepareStatement != null || connection != null) {
                    result.close();
                    prepareStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }

        return adminEntity;
    }

    @Override
    public String getPassword() {
        String password = "";

        String querry = "Select AdminPassword from admin WHERE AdminName='admin'";

        try {
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                password = result.getString("AdminPassword");
            }

        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            /*Closing ResultSet*/
            try {
                if (result != null || prepareStatement != null || connection != null) {
                    result.close();
                    prepareStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return password;
    }

    @Override
    public int changePassword(String password) {

        int status = 0;
        String querry = "Update admin set AdminPassword=? Where AdminName='admin'";

        try {
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, password);
            status = prepareStatement.executeUpdate();

        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            /*Closing ResultSet*/
            try {
                if (result != null || prepareStatement != null || connection != null) {
                    result.close();
                    prepareStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }
        return status;
    }

}