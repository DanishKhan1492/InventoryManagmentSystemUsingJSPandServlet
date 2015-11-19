package app.IMAS.DatabaseDaosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import app.IMAS.DatabaseConnection.DatabaseConnection;
import app.IMAS.DatabaseDaos.BorrowerBillDao;
import app.IMAS.Enitities.AmountBorrowedEntity;
import app.IMAS.Enitities.AmountPaidEntity;
import app.IMAS.Enitities.BillItemsEntity;
import app.IMAS.Enitities.BorrowerBillDetailsEntity;

public class BorrowerBillDaoImpl implements BorrowerBillDao {

    private Connection connection = null;
    private PreparedStatement prepareStatement = null;
    private ResultSet result = null;

    @Override
    public int insertBill(BorrowerBillDetailsEntity billMain, ArrayList<BillItemsEntity> billItems) {
        int status = 0;

    /////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////
            /*Inserting bill to database First Part*/
        /////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////    
        try {
            String insertBillQuery = "Insert Into borrowerbills (CNIC,SubTotal,TotalAmount,Discount,AmountPaid,DueAmount,Date) Values(?,?,?,?,?,?,?)";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(insertBillQuery);
            prepareStatement.setString(1, billMain.getCnic());
            prepareStatement.setDouble(2, billMain.getSubTotal());
            prepareStatement.setDouble(3, billMain.getTotalAmount());
            prepareStatement.setDouble(4, billMain.getDiscount());
            prepareStatement.setDouble(5, billMain.getAmountPaid());
            prepareStatement.setDouble(6, billMain.getDueAmount());
            prepareStatement.setDate(7, new java.sql.Date(billMain.getDate().getTime()));

            status = prepareStatement.executeUpdate();

            /*///////////////////////////////////////////////////////////////////////////*/
            /*//////////////////////////////////////////////////////////////////////////*/
            //   Inserting Bill Details into database 
            //:i.e. items and releated information 2nd Part
            /////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////
            String bill_Id = billMain.getBillId();
            String[] id = bill_Id.split("-");
            int billId = Integer.parseInt(id[1]);

            for (BillItemsEntity billItem : billItems) {
                String insertBillItemsQuery = "Insert Into borrowerbillsdetails(Bill_Id,ItemName,ItemQuantity,ItemPrice,WholePrice,Date) Values(?,?,?,?,?,?)";
                prepareStatement = connection.prepareStatement(insertBillItemsQuery);
                prepareStatement.setInt(1, billId);
                prepareStatement.setString(2, billItem.getItemName());
                prepareStatement.setDouble(3, billItem.getItemQuantity());
                prepareStatement.setDouble(4, billItem.getItemPrice());
                prepareStatement.setDouble(5, billItem.getTotalItemPrice());
                prepareStatement.setDate(6, new java.sql.Date(billMain.getDate().getTime()));

                status = prepareStatement.executeUpdate();
            }

            /////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////
            //3rd Part
            // Updating Product Quantities after selling items
            /////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////
            Map<String, Double> getQuantity = new HashMap<>();
            Iterator<BillItemsEntity> iter = billItems.iterator();

            while (iter.hasNext()) {
                BillItemsEntity billEntity = iter.next();
                String getQuantityQuerry = "Select Prod_Quantity from product where Prod_Name=?";
                prepareStatement = connection.prepareStatement(getQuantityQuerry);
                prepareStatement.setString(1, billEntity.getItemName());
                result = prepareStatement.executeQuery();

                while (result.next()) {
                    getQuantity.put(billEntity.getItemName(), result.getDouble("Prod_Quantity"));
                }
            }

            if (getQuantity != null) {

                double oldQuantity = 0, newQuantity = 0, quantityDeducted = 0;
                Iterator<BillItemsEntity> itert = billItems.iterator();
                while (itert.hasNext()) {
                    BillItemsEntity entity = itert.next();
                    quantityDeducted = entity.getItemQuantity();
                    oldQuantity = getQuantity.get(entity.getItemName());
                    newQuantity = oldQuantity - quantityDeducted;

                    String updateQuerry = "Update product SET Prod_Quantity=? where Prod_Name=?";
                    prepareStatement = connection.prepareStatement(updateQuerry);
                    prepareStatement.setDouble(1, newQuantity);
                    prepareStatement.setString(2, entity.getItemName());
                    status = prepareStatement.executeUpdate();

                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return status = 0;
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

    @Override
    public ArrayList<BorrowerBillDetailsEntity> getAllBills() {
        ArrayList<BorrowerBillDetailsEntity> billDetails = new ArrayList<>();

        try {
            String getBillQuerry = "Select * from borrowerbills";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getBillQuerry);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                BorrowerBillDetailsEntity billEntity = new BorrowerBillDetailsEntity();
                int id = 0;
                id = result.getInt("Bill_Id");
                billEntity.setBillId("" + id);
                billEntity.setCnic(result.getString("CNIC"));
                billEntity.setSubTotal(result.getDouble("SubTotal"));
                billEntity.setTotalAmount(result.getDouble("TotalAmount"));
                billEntity.setDiscount(result.getDouble("Discount"));
                billEntity.setAmountPaid(result.getDouble("AmountPaid"));
                billEntity.setDueAmount(result.getDouble("DueAmount"));
                billEntity.setDate(result.getDate("Date"));

                billDetails.add(billEntity);
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

        return billDetails;
    }

    @Override
    public BorrowerBillDetailsEntity getSpecificBill(String Cnic) {
        BorrowerBillDetailsEntity billMain = new BorrowerBillDetailsEntity();
        try {
            String getBillQuerry = "Select * from borrowerbills WHERE CNIC=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getBillQuerry);
            prepareStatement.setString(1, Cnic);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                int id = 0;
                id = result.getInt("Bill_Id");
                billMain.setBillId("" + id);
                billMain.setCnic(result.getString("CNIC"));
                billMain.setSubTotal(result.getDouble("SubTotal"));
                billMain.setTotalAmount(result.getDouble("TotalAmount"));
                billMain.setDiscount(result.getDouble("Discount"));
                billMain.setAmountPaid(result.getDouble("AmountPaid"));
                billMain.setDueAmount(result.getDouble("DueAmount"));
                billMain.setDate(result.getDate("Date"));
            }
        } catch (SQLException ex) {
            ex.getMessage();
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
        return billMain;
    }

    @Override
    public String checkBorrower(String cnic) {
        String name = "";
        try {
            String getBillQuerry = "Select Name from borrower WHERE CNIC=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getBillQuerry);
            prepareStatement.setString(1, cnic);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                name = result.getString("Name");
            }
        } catch (SQLException ex) {
            ex.getMessage();
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
        return name;
    }

    @Override
    public int updateDebt(String Cnic, double dueAmount) {
        int status = 0;
        try {
            String getBillQuerry = "Update borrower SET AmountRemains=? WHERE CNIC=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getBillQuerry);
            prepareStatement.setDouble(1, dueAmount);
            prepareStatement.setString(2, Cnic);
            status = prepareStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.getMessage();
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

    @Override
    public double getRemaingingAmount(String cnic) {
        double remainingAmount = 0;
        try {
            String getBillQuerry = "Select AmountRemains from borrower WHERE CNIC=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getBillQuerry);
            prepareStatement.setString(1, cnic);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                remainingAmount = result.getDouble("AmountRemains");
            }

        } catch (SQLException ex) {
            ex.getMessage();
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
        return remainingAmount;
    }

    @Override
    public int insertAmountBorrowed(AmountBorrowedEntity amountBorrowed) {
        int status = 0;
        try {
            String getBillQuerry = "INSERT into amountborrowed(CNIC,Bill_Id,LastAmount,NewAmount,TotalAmount,Date) VALUES(?,?,?,?,?,?)";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getBillQuerry);
            prepareStatement.setString(1, amountBorrowed.getCnic());
            prepareStatement.setInt(2, amountBorrowed.getBillId());
            prepareStatement.setDouble(3, amountBorrowed.getLastAmount());
            prepareStatement.setDouble(4, amountBorrowed.getNewAmount());
            prepareStatement.setDouble(5, amountBorrowed.getTotalAmount());
            prepareStatement.setDate(6, new java.sql.Date(amountBorrowed.getDate().getTime()));
            status = prepareStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
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

    @Override
    public int amountPaid(AmountPaidEntity amountPaid) {
        int status = 0;
        try {
            String getBillQuerry = "INSERT into amountpaid(CNIC,LastAmount,Paid,RemainingAmount,Date) VALUES(?,?,?,?,?)";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getBillQuerry);
            prepareStatement.setString(1, amountPaid.getCnic());
            prepareStatement.setDouble(2, amountPaid.getLastAmount());
            prepareStatement.setDouble(3, amountPaid.getPaid());
            prepareStatement.setDouble(4, amountPaid.getTotalAmount());
            prepareStatement.setDate(5, new java.sql.Date(amountPaid.getDate().getTime()));
            status = prepareStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
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

}
