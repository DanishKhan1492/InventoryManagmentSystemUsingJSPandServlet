/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.IMAS.ExtraClasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import app.IMAS.DatabaseConnection.DatabaseConnection;

public class ResetingAutoIncrement {

    private static Connection connection = null;
    private static Statement statement = null;

    public static void resetAutoIncrement(String tableName, String Id) {
        try {
            String first = "SET @num := 0;";
            String second = "UPDATE " + tableName + " SET " + Id + "= @num := (@num+1);";
            String querry = "ALTER TABLE " + tableName + " auto_increment = 1";
            connection = DatabaseConnection.getConnection();
            statement=connection.createStatement();
            connection.setAutoCommit(false);
            statement.addBatch(first);
            statement.addBatch(second);
            statement.addBatch(querry);
            statement.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            /*Closing ResultSet*/
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
