/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.IMAS.ExtraClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.IMAS.DatabaseConnection.DatabaseConnection;

public class GenerateBillId {

    private static Connection connection = null;
    private static PreparedStatement prepareStatement = null;
    private static ResultSet result = null;

    public static String Generate_Bill_Id(String tableName) {
        String billId = "";
        int key = 1;

        try {
            connection = DatabaseConnection.getConnection();
            String Querry = "Select MAX(Bill_Id) from "+tableName;
            prepareStatement = connection.prepareStatement(Querry);
            result = prepareStatement.executeQuery();

            if (result.next()) {
                key = result.getInt(1);
                key = key + 1;
            }
            
            if(tableName.equals("customerbills")){
               billId=generateId(key, "CB");
            }else if(tableName.equals("borrowerbills")){
               billId=generateId(key, "BB");
            }
            

        } catch (SQLException excep) {
            excep.printStackTrace();
        } finally {
            try {
                if (prepareStatement != null && connection != null) {
                    prepareStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return billId;
    }
    
    public static String generateId(int key,String type){
        String billId;
         if(key<10){
                billId=type+"-"+"000"+key;
            }else if(key<100 && key>9){
                billId=type+"-"+"00"+key;
            }else if(key<1000 && key >99){
                 billId=type+"-"+"0"+key;
            }else{
                 billId=type+key;
            }
        
        return billId;
    }
}
