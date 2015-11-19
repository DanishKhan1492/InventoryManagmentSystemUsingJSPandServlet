/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.IMAS.DatabaseDaosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.IMAS.DatabaseConnection.DatabaseConnection;
import app.IMAS.DatabaseDaos.ProductDao;
import app.IMAS.Enitities.PriceEntity;
import app.IMAS.Enitities.ProductEntity;

public class ProductDaoImpl implements ProductDao {

    private Connection con = null; // declaring Connection object
    private PreparedStatement prepstatement = null; // declaring Prepared												// Statement object.
    private ResultSet result; // declaring result set object to store result
    // getting from database

    @Override
    public int addItem(ProductEntity additem) {
        int status = 0;

        try {

            String querry = "Insert Into product (Prod_Name,Prod_Quantity,Prod_Price,Cat_Name) VALUES(?,?,?,?)";
            con = DatabaseConnection.getConnection();
            prepstatement = con.prepareStatement(querry);
            prepstatement.setString(1, additem.getItemName());
            prepstatement.setDouble(2, additem.getItemQuantity());
            prepstatement.setDouble(3, additem.getItemPrice());
            prepstatement.setString(4, additem.getItemCategory());

            status = prepstatement.executeUpdate();

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                if (prepstatement != null && con != null) {
                    prepstatement.close();
                    con.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }

        }

        return status;
    }

    /////////////////////////////////////////////////////////////////////////////////////
                // Getting All items
    /////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public ArrayList<ProductEntity> getAllItems() {
        ArrayList<ProductEntity> getitems = new ArrayList<>();

        try {

            String querry = "Select * From Product";
            con = DatabaseConnection.getConnection();
            prepstatement = con.prepareStatement(querry);
            result = prepstatement.executeQuery();

            while (result.next()) {

                ProductEntity items = new ProductEntity();
                items.setItemId(result.getInt("Prod_Id"));
                items.setItemName(result.getString("Prod_Name"));
                items.setItemQuantity(result.getDouble("Prod_Quantity"));
                items.setItemPrice(result.getDouble("Prod_Price"));
                items.setItemCategory(result.getString("Cat_Name"));

                getitems.add(items);
            }

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                if (prepstatement != null && con != null) {
                    prepstatement.close();
                    con.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }

        }

        return getitems;
    }

    /////////////////////////////////////////////////////////////////////////////////////
                // Searching Items on Name
    /////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public ArrayList<ProductEntity> searchItems(String itemName) {
        ArrayList<ProductEntity> getitems = new ArrayList<>();

        try {

            String querry = "Select * From Product Where Prod_Name LIKE ?";
            con = DatabaseConnection.getConnection();
            prepstatement = con.prepareStatement(querry);
            prepstatement.setString(1, "%" + itemName + "%");
            result = prepstatement.executeQuery();

            while (result.next()) {

                ProductEntity items = new ProductEntity();
                items.setItemId(result.getInt("Prod_Id"));
                items.setItemName(result.getString("Prod_Name"));
                items.setItemQuantity(result.getDouble("Prod_Quantity"));
                items.setItemPrice(result.getDouble("Prod_Price"));
                items.setItemCategory(result.getString("Cat_Name"));

                getitems.add(items);
            }

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                if (prepstatement != null && con != null) {
                    prepstatement.close();
                    con.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }

        }

        return getitems;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////
                // Updating Product Items
    /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public int updateItems(ProductEntity updateitems) {
        int status = 0;

        try {

            String querry = "UPDATE Product SET Prod_Name=?,Prod_Quantity=?,Prod_Price=?,Cat_Name=? where Prod_Id=?";
            con = DatabaseConnection.getConnection();
            prepstatement = con.prepareStatement(querry);
            prepstatement.setString(1, updateitems.getItemName());
            prepstatement.setDouble(2, updateitems.getItemQuantity());
            prepstatement.setDouble(3, updateitems.getItemPrice());
            prepstatement.setString(4, updateitems.getItemCategory());
            prepstatement.setInt(5, updateitems.getItemId());

            status = prepstatement.executeUpdate();

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                if (prepstatement != null && con != null) {
                    prepstatement.close();
                    con.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }

        }

        return status;
    }

    /////////////////////////////////////////////////////////////////////////////////////
                // Getting  Items to update it
    /////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public ProductEntity getItemsforupdate(int updateitems) {
        
    	ProductEntity items = new ProductEntity();

        try {

            String querry = "Select * From Product where Prod_Id=?";
            con = DatabaseConnection.getConnection();
            prepstatement = con.prepareStatement(querry);
            prepstatement.setInt(1, updateitems);
            result = prepstatement.executeQuery();

            while (result.next()) {     
                items.setItemId(result.getInt("Prod_Id"));
                items.setItemName(result.getString("Prod_Name"));
                items.setItemQuantity(result.getDouble("Prod_Quantity"));
                items.setItemPrice(result.getDouble("Prod_Price"));
                items.setItemCategory(result.getString("Cat_Name"));
            }

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                if (prepstatement != null && con != null) {
                    prepstatement.close();
                    con.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }

        }

        return items;
    }

    /////////////////////////////////////////////////////////////////////////////////////
                // Deleting Product Items From Database
    /////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public int deleteItems(int delitems) {
        int status = 0;

        try {

            String querry = "DELETE From product where Prod_Id=?";
            con = DatabaseConnection.getConnection();
            prepstatement = con.prepareStatement(querry);
            prepstatement.setInt(1, delitems);
            status = prepstatement.executeUpdate();

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                if (prepstatement != null && con != null) {
                    prepstatement.close();
                    con.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }

        }

        return status;
    }

    /////////////////////////////////////////////////////////////////////////////////////
                // Getting Price of Item from database
    /////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public ProductEntity getPrice(int itemid) {
        ProductEntity changePrice = new ProductEntity();

        try {
            String querry = "Select Prod_Name,Prod_Price from Product where Prod_Id=?";
            con = DatabaseConnection.getConnection();
            prepstatement = con.prepareStatement(querry);
            prepstatement.setInt(1, itemid);

            result = prepstatement.executeQuery();

            while (result.next()) {
                changePrice.setItemName(result.getString("Prod_Name"));
                changePrice.setItemPrice(result.getDouble("Prod_Price"));
            }

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            if (prepstatement != null && con != null) {
                try {
                    prepstatement.close();
                    con.close();
                } catch (SQLException ex) {
                   ex.getMessage();
                }
                }
        }
        return changePrice;
    }

    /////////////////////////////////////////////////////////////////////////////////////
                // Getting Item Names from Database
    /////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public ArrayList<String> getItemNames(String categoryName) {
        ArrayList<String> getItems=new ArrayList<>();
        
        String querry="Select Prod_Name from product where Cat_Name=?";
        try{
            con=DatabaseConnection.getConnection();
            prepstatement=con.prepareStatement(querry);
            prepstatement.setString(1, categoryName);
            result=prepstatement.executeQuery();
            
            while(result.next()){
                String itemName=result.getString("Prod_Name");
                getItems.add(itemName);
            }
            
        }catch(SQLException e){
            e.getMessage();
        } finally {
            if (prepstatement != null && con != null) {
                try {
                    prepstatement.close();
                    con.close();
                } catch (SQLException ex) {
                   ex.getMessage();
                }
                }
        }
       
        return getItems;
    }

    /////////////////////////////////////////////////////////////////////////////////////
                // Saving Price Changed
    /////////////////////////////////////////////////////////////////////////////////////
    @Override
    public int changePrice(PriceEntity price) {
        int status=0;
        
            try{
               String querry="Insert into pricechange(Prod_Name,OldPrice,NewPrice,Date) VALUES(?,?,?,?)";
               con=DatabaseConnection.getConnection();
               prepstatement=con.prepareStatement(querry);
               prepstatement.setString(1, price.getItemName());
               prepstatement.setDouble(2, price.getOldPrice());
               prepstatement.setDouble(3, price.getNewPrice());
               prepstatement.setDate(4, new java.sql.Date(price.getDate().getTime()));
               status=prepstatement.executeUpdate();
               
            }catch(SQLException e){
                e.printStackTrace();
            } finally {
            if (prepstatement != null && con != null) {
                try {
                    prepstatement.close();
                    con.close();
                } catch (SQLException ex) {
                   ex.getMessage();
                }
                }
        }
        
        return status;
    }
    
    
    
}
