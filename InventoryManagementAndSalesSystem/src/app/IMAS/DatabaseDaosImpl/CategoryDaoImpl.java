package app.IMAS.DatabaseDaosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.IMAS.DatabaseConnection.DatabaseConnection;
import app.IMAS.DatabaseDaos.CategoryDao;
import app.IMAS.Enitities.CategoryEntity;

public class CategoryDaoImpl implements CategoryDao {

    private Connection connection = null;
    private PreparedStatement prepareStatement = null;
    private ResultSet result = null;

//*********************************************************************************//
    @Override
    public int addCategory(String category,String unit) {

        int status = 0;

        try {

            String querry = "Insert into Category(Cat_Name,Cat_Unit) VALUES(?,?)";

            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, category);
            prepareStatement.setString(2, unit);
            status = prepareStatement.executeUpdate();

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

        return status;
    }

//*********************************************************************************//
    @Override
    public ArrayList<CategoryEntity> getCategories() {

        ArrayList<CategoryEntity> storeCategory = new ArrayList<>();
        try {
            String querry = "Select * from category ORDER BY Cat_Id ASC";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                CategoryEntity catEntity = new CategoryEntity();
                catEntity.setId(result.getInt("Cat_Id"));
                catEntity.setCategoryName(result.getString("Cat_Name"));
                catEntity.setCategoryUnit(result.getString("Cat_Unit"));

                storeCategory.add(catEntity);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (prepareStatement != null) {
                    prepareStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return storeCategory;
    }

//*********************************************************************************//
    @Override
    public int updateCategory(CategoryEntity catEntity) {
        int status = 0;

        try {
            String querry = "UPDATE category SET Cat_Name=?,Cat_Unit=? where Cat_Id=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, catEntity.getCategoryName());
            prepareStatement.setString(2, catEntity.getCategoryUnit());
            prepareStatement.setInt(3,catEntity.getId());
            status = prepareStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (prepareStatement != null) {
                    prepareStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    //*********************************************************************************//
    @Override
    public ArrayList<CategoryEntity> searchCategory(String catName) {

        
        ArrayList<CategoryEntity> returnCategories=new ArrayList<>();
        
        String querry = "Select * from category WHERE Cat_Name LIKE ?";
        try {

            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, "%"+catName+"%");
            
            result=prepareStatement.executeQuery();
            
            while(result.next()){
                CategoryEntity searchCat = new CategoryEntity();
                searchCat.setId(result.getInt("Cat_Id"));
                searchCat.setCategoryName(result.getString("Cat_Name"));
                searchCat.setCategoryUnit(result.getString("Cat_Unit"));
                returnCategories.add(searchCat);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (prepareStatement != null) {
                    prepareStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return returnCategories;
    }

//*********************************************************************************//
  @Override
    public int deleteCategory(String catName) {
        int status=0;
        String querry = "DELETE from category WHERE Cat_Name=?";
        try {

            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, catName);
            
            status=prepareStatement.executeUpdate();
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (prepareStatement != null) {
                    prepareStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return status;
    }    
    
//*********************************************************************************//
  @Override
    public String getCategory(String itemName) {
        String retItemName="";
        String querry = "Select * from product WHERE Prod_Name=?";
        try {

            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, itemName);
            
            result=prepareStatement.executeQuery();
            
            while(result.next()){
            	retItemName=result.getString("Cat_Name");
            }
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (prepareStatement != null) {
                    prepareStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return retItemName;
    }    
  
}
