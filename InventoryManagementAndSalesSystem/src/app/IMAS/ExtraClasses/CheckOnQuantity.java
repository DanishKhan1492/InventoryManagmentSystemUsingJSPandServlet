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
import java.util.HashMap;
import app.IMAS.DatabaseConnection.DatabaseConnection;

public class CheckOnQuantity {
    private static Connection connection = null;
    private static PreparedStatement prepareStatement = null;
    private static ResultSet result = null;
    
    public static HashMap<String,Double> checkKgQuantity(){
        HashMap<String,Double> checkQuantity=new HashMap<>();
        
        try{
            String checkKgQuerry="Select Prod_Name,Prod_Quantity from product  product INNER JOIN  category on product.Cat_Name=category.Cat_Name WHERE Prod_Quantity <= ? And category.Cat_Unit=?";
            connection=DatabaseConnection.getConnection();
            prepareStatement=connection.prepareStatement(checkKgQuerry);
            prepareStatement.setInt(1, 300);
            prepareStatement.setString(2, "Kg");
            result=prepareStatement.executeQuery();
            
            while(result.next()){
                checkQuantity.put(result.getString("Prod_Name"), result.getDouble("Prod_Quantity"));
            }
 
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return checkQuantity;
    }
    
    public static HashMap<String,Double> checkPacketProduct(){
        HashMap<String,Double> checkQuantity=new HashMap<>();
        
        try{
            String checkPacketQuerry="Select Prod_Name,Prod_Quantity from product  product INNER JOIN  category on product.Cat_Name=category.Cat_Name WHERE Prod_Quantity < ? And category.Cat_Unit=? "; 
            prepareStatement=connection.prepareStatement(checkPacketQuerry);
            prepareStatement.setInt(1, 20);
            prepareStatement.setString(2, "Packet");
            result=prepareStatement.executeQuery();
            
            while(result.next()){
                checkQuantity.put(result.getString("Prod_Name"), result.getDouble("Prod_Quantity"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return checkQuantity;
    }
}
