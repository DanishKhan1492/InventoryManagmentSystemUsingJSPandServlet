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
import app.IMAS.DatabaseDaos.BorrowerDao;
import app.IMAS.Enitities.BorrowerEntity;

public class BorrowerDaoImpl implements BorrowerDao {

    private Connection connection;
    private PreparedStatement prepareStatement;
    private ResultSet result;

    @Override
    public int addBorrwer(BorrowerEntity borrowerEntity) {
        int status = 0;
        try {
            String querry = "Insert into borrower(Name,CNIC,Address,ContactNo,AmountRemains) VALUES(?,?,?,?,?)";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, borrowerEntity.getBorrowerName());
            prepareStatement.setString(2, borrowerEntity.getCnic());
            prepareStatement.setString(3, borrowerEntity.getAddress());
            prepareStatement.setString(4, borrowerEntity.getContactNumber());
            prepareStatement.setDouble(5, borrowerEntity.getDebtAmount());

            status = prepareStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
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
        return status;
    }

    @Override
    public BorrowerEntity searchBorrowerByCnic(String cnic) {
        BorrowerEntity getBorrower = new BorrowerEntity();
        try {
            String querry = "Select * from borrower WHERE CNIC=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, cnic);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                getBorrower.setId(result.getInt("BorrowerId"));
                getBorrower.setBorrowerName(result.getString("Name"));
                getBorrower.setCnic(result.getString("CNIC"));
                getBorrower.setAddress(result.getString("Address"));
                getBorrower.setContactNumber(result.getString("ContactNo"));
                getBorrower.setDebtAmount(result.getDouble("AmountRemains"));
            }

        } catch (SQLException ex) {
            ex.getMessage();
        } finally {
            try {
                if (prepareStatement != null && connection != null) {
                    prepareStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return getBorrower;
    }

    @Override
    public ArrayList<BorrowerEntity> searchBorrowerByName(String borrowerName) {
        ArrayList<BorrowerEntity> getBorrowers = new ArrayList<>();
        try {
            String querry = "Select * from borrower WHERE Name=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, borrowerName);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                BorrowerEntity getBorrower = new BorrowerEntity();
                getBorrower.setId(result.getInt("BorrowerId"));
                getBorrower.setBorrowerName(result.getString("Name"));
                getBorrower.setCnic(result.getString("CNIC"));
                getBorrower.setAddress(result.getString("Address"));
                getBorrower.setContactNumber(result.getString("ContactNo"));
                getBorrower.setDebtAmount(result.getDouble("AmountRemains"));

                getBorrowers.add(getBorrower);
            }

        } catch (SQLException ex) {
            ex.getMessage();
        } finally {
            try {
                if (prepareStatement != null && connection != null) {
                    prepareStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }

        return getBorrowers;
    }

    @Override
    public int updateBorrower(BorrowerEntity borrowerEntity) {
        int status = 0;
        try {
            String querry = "Update Borrower SET Name=?, CNIC=?, Address=?, ContactNo=? Where BorrowerId=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, borrowerEntity.getBorrowerName());
            prepareStatement.setString(2, borrowerEntity.getCnic());
            prepareStatement.setString(3, borrowerEntity.getAddress());
            prepareStatement.setString(4, borrowerEntity.getContactNumber());
            prepareStatement.setInt(5, borrowerEntity.getId());

            status = prepareStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.getMessage();
        } finally {
            try {
                if (prepareStatement != null && connection != null) {
                    prepareStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return status;
    }

    @Override
    public int deleteBorrower(int id) {
        int status = 0;
        try {
            String querry = "DELETE from borrower WHERE borrowerId=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setInt(1, id);
            
            status = prepareStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.getMessage();
        } finally {
            try {
                if (prepareStatement != null && connection != null) {
                    prepareStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return status;
    }

    @Override
    public ArrayList<BorrowerEntity> getAllBorrowers() {
         ArrayList<BorrowerEntity> getBorrowers = new ArrayList<>();
        try {
            String querry = "SELECT * FROM borrower;";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            
            result = prepareStatement.executeQuery();

            while (result.next()) {
                BorrowerEntity getBorrower = new BorrowerEntity();
                getBorrower.setId(result.getInt("BorrowerId"));
                getBorrower.setBorrowerName(result.getString("Name"));
                getBorrower.setCnic(result.getString("CNIC"));
                getBorrower.setAddress(result.getString("Address"));
                getBorrower.setContactNumber(result.getString("ContactNo"));
                getBorrower.setDebtAmount(result.getDouble("AmountRemains"));

                getBorrowers.add(getBorrower);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (prepareStatement != null && connection != null) {
                    prepareStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }

        return getBorrowers;
    }

}
