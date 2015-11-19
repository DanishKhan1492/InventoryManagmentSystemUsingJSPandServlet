
package app.IMAS.DatabaseDaosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.IMAS.DatabaseConnection.DatabaseConnection;
import app.IMAS.DatabaseDaos.PriceChangeDao;
import app.IMAS.Enitities.PriceChangeEntity;

public class PriceChangeDaoImpl implements PriceChangeDao{

    private Connection connection = null;
    private PreparedStatement prepareStatement = null;
    private ResultSet result = null;
    
    @Override
    public ArrayList<PriceChangeEntity> getSpecificPrice(String itemName) {
        ArrayList<PriceChangeEntity> priceSpecific=new ArrayList<>();
        try {

            String getPriceQuery = "Select * from pricechange WHERE Prod_Name LIKE ?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getPriceQuery);
            prepareStatement.setString(1, "%"+itemName+"%");
            result = prepareStatement.executeQuery();

            while (result.next()) {
                PriceChangeEntity priceChange=new PriceChangeEntity();
                priceChange.setPriceChangeId(result.getInt("PC_Id"));
                priceChange.setItemName(result.getString("Prod_Name"));
                priceChange.setOldPrice(result.getDouble("OldPrice"));
                priceChange.setOldPrice(result.getDouble("NewPrice"));
                priceChange.setDate(result.getDate("Date"));
                
                priceSpecific.add(priceChange);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            /*Closing ResultSet*/
            try {
                if (result != null && prepareStatement != null && connection != null) {
                    result.close();
                    prepareStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return priceSpecific;
    }

    @Override
    public ArrayList<PriceChangeEntity> getAllPrice() {
        ArrayList<PriceChangeEntity> priceSpecific=new ArrayList<>();
        try {

            String getPriceQuery = "Select * from pricechange";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getPriceQuery);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                PriceChangeEntity priceChange=new PriceChangeEntity();
                priceChange.setPriceChangeId(result.getInt("PC_Id"));
                priceChange.setItemName(result.getString("Prod_Name"));
                priceChange.setOldPrice(result.getDouble("OldPrice"));
                priceChange.setNewPrice(result.getDouble("NewPrice"));
                priceChange.setDate(result.getDate("Date"));
                
                priceSpecific.add(priceChange);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            /*Closing ResultSet*/
            try {
                if (result != null && prepareStatement != null && connection != null) {
                    result.close();
                    prepareStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return priceSpecific;
    }
    
}
