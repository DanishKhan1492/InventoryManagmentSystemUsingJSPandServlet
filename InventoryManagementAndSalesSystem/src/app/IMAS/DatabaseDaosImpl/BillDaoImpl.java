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
import app.IMAS.DatabaseDaos.BillDao;
import app.IMAS.Enitities.BillItemsEntity;
import app.IMAS.Enitities.BillMainDetailsEntity;

public class BillDaoImpl implements BillDao {

    private Connection connection = null;
    private PreparedStatement prepareStatement = null;
    private ResultSet result = null;

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    /*Getting Price For Billing*/
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    @Override
    public double getPrice(String itemName) {
        double itemPrice = 0.0;
        try {

            String getPriceQuery = "Select Prod_Price from product WHERE Prod_Name=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getPriceQuery);
            prepareStatement.setString(1, itemName);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                itemPrice = Double.parseDouble(result.getString("Prod_Price"));
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
        return itemPrice;
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    /*
     *
     Insertig Bills to Database
     *
     */
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    @Override
    public int insertBill(BillMainDetailsEntity billMain, ArrayList<BillItemsEntity> billItems) {
        int status = 0;

    /////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////
            /*Inserting bill to database First Part*/
    /////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////    
        try {
            String insertBillQuery = "Insert Into customerbills (CustomerName,CNICorMobile,SubTotal,TotalAmount,Discount,AmountPaid,DueAmount,Date) Values(?,?,?,?,?,?,?,?)";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(insertBillQuery);
            prepareStatement.setString(1, billMain.getCustomerName());
            prepareStatement.setString(2, billMain.getCnicOrMobile());
            prepareStatement.setDouble(3, billMain.getSubTotal());
            prepareStatement.setDouble(4, billMain.getTotalAmount());
            prepareStatement.setDouble(5, billMain.getDiscount());
            prepareStatement.setDouble(6, billMain.getAmountPaid());
            prepareStatement.setDouble(7, billMain.getDueAmount());
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
                String insertBillItemsQuery = "Insert Into customerbillsdetails(Bill_Id,ItemName,ItemQuantity,ItemPrice,WholePrice,Date) Values(?,?,?,?,?,?)";
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

     /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    // Getting All Bills
    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    @Override
    public ArrayList<BillMainDetailsEntity> getAllBills() {
        ArrayList<BillMainDetailsEntity> billDetails = new ArrayList<>();

        try {
            String getBillQuerry = "Select * from customerbills";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getBillQuerry);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                BillMainDetailsEntity billEntity = new BillMainDetailsEntity();
                int id = 0;
                id = result.getInt("Bill_Id");
                billEntity.setBillId("" + id);
                billEntity.setCustomerName(result.getString("CustomerName"));
                billEntity.setCnicOrMobile(result.getString("CNICorMobile"));
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
    public BillMainDetailsEntity getSpecificBill(String cnicMobile) {
        BillMainDetailsEntity billMain = new BillMainDetailsEntity();
        try {
            String getBillQuerry = "Select * from customerbills WHERE CNICorMobile=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(getBillQuerry);
            prepareStatement.setString(1, cnicMobile);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                int id = 0;
                id = result.getInt("Bill_Id");
                billMain.setBillId("" + id);
                billMain.setCustomerName(result.getString("CustomerName"));
                billMain.setCnicOrMobile(result.getString("CNICorMobile"));
                billMain.setTotalAmount(result.getDouble("TotalAmount"));
                billMain.setDiscount(result.getDouble("Discount"));
                billMain.setAmountPaid(result.getDouble("AmountPaid"));
                billMain.setDueAmount(result.getDouble("DueAmount"));
                billMain.setDate(result.getDate("Date"));
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
        return billMain;
    }

}
