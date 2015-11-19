package app.IMAS.DatabaseDaosImpl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import app.IMAS.DatabaseConnection.DatabaseConnection;
import app.IMAS.DatabaseDaos.FullDebtDetailsDao;
import app.IMAS.Enitities.AmountBorrowedEntity;
import app.IMAS.Enitities.AmountPaidEntity;
import app.IMAS.Enitities.BillItemsEntity;
import app.IMAS.Enitities.BorrowerBillDetailsEntity;
import app.IMAS.Enitities.BorrowerEntity;

public class FullDebtDetailsDaoImpl implements FullDebtDetailsDao {

    private Connection connection = null;
    private PreparedStatement prepareStatement = null;
    private ResultSet result = null;

    @Override

    public BorrowerEntity getSpecificBorrower(String cnic) {
        BorrowerEntity borrower = new BorrowerEntity();
        try {
            String querry = "Select * from borrower where CNIC=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, cnic);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                borrower.setId(result.getInt("BorrowerId"));
                borrower.setBorrowerName(result.getString("Name"));
                borrower.setCnic(result.getString("CNIC"));
                borrower.setAddress(result.getString("Address"));
                borrower.setContactNumber(result.getString("ContactNo"));
                borrower.setDebtAmount(result.getDouble("AmountRemains"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
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
        return borrower;
    }

    @Override
    public ArrayList<BorrowerBillDetailsEntity> getBorrowerBills(String cnic) {
        ArrayList<BorrowerBillDetailsEntity> getBills = new ArrayList<>();
        try {
            String querry = "Select * from borrowerbills where CNIC=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, cnic);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                BorrowerBillDetailsEntity borrowerDetails = new BorrowerBillDetailsEntity();
                int id = result.getInt("Bill_Id");
                String billId = "" + id;
                borrowerDetails.setBillId(billId);
                borrowerDetails.setCnic(result.getString("CNIC"));
                borrowerDetails.setSubTotal(result.getDouble("SubTotal"));
                borrowerDetails.setTotalAmount(result.getDouble("TotalAmount"));
                borrowerDetails.setDiscount(result.getDouble("Discount"));
                borrowerDetails.setAmountPaid(result.getDouble("AmountPaid"));
                borrowerDetails.setDueAmount(result.getDouble("DueAmount"));
                borrowerDetails.setDate(result.getDate("Date"));

                getBills.add(borrowerDetails);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
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
        return getBills;
    }

    @Override
    public ArrayList<BillItemsEntity> getBillItems(int billId) {
        ArrayList<BillItemsEntity> getBillsItems = new ArrayList<>();
        try {
            String querry = "Select * from borrowerbillsdetails where Bill_Id=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setInt(1, billId);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                BillItemsEntity billItems = new BillItemsEntity();
                billItems.setBillId(result.getInt("Bill_Id"));
                billItems.setItemName(result.getString("ItemName"));
                billItems.setItemPrice(result.getDouble("ItemPrice"));
                billItems.setItemQuantity(result.getDouble("ItemQuantity"));
                billItems.setTotalItemPrice(result.getDouble("WholePrice"));
                billItems.setDate(result.getDate("Date"));

                getBillsItems.add(billItems);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
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
        return getBillsItems;
    }

    @Override
    public ArrayList<AmountBorrowedEntity> getAllBorrowedAmounts(String cnic) {
        ArrayList<AmountBorrowedEntity> amountBorrowed = new ArrayList<>();
        try {
            String querry = "Select * from amountborrowed where CNIC=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, cnic);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                AmountBorrowedEntity amount = new AmountBorrowedEntity();
                amount.setBillId(result.getInt("Bill_Id"));
                amount.setCnic(result.getString("CNIC"));
                amount.setLastAmount(result.getDouble("LastAmount"));
                amount.setNewAmount(result.getDouble("NewAmount"));
                amount.setTotalAmount(result.getDouble("TotalAmount"));
                amount.setDate(result.getDate("Date"));

                amountBorrowed.add(amount);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
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
        return amountBorrowed;
    }

    @Override
    public ArrayList<AmountPaidEntity> getAllPaidAmounts(String cnic) {
        ArrayList<AmountPaidEntity> amountPaid = new ArrayList<>();
        try {
            String querry = "Select * from amountpaid where CNIC=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, cnic);
            result = prepareStatement.executeQuery();

            while (result.next()) {
                AmountPaidEntity amount = new AmountPaidEntity();
                amount.setCnic(result.getString("CNIC"));
                amount.setLastAmount(result.getDouble("LastAmount"));
                amount.setPaid(result.getDouble("Paid"));
                amount.setTotalAmount(result.getDouble("RemainingAmount"));
                amount.setDate(result.getDate("Date"));

                amountPaid.add(amount);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
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
        return amountPaid;
    }

    @Override
    public ArrayList<BorrowerBillDetailsEntity> getBorrowerBills(String cnic, Date date) {
        ArrayList<BorrowerBillDetailsEntity> getBills = new ArrayList<>();
        try {
            String querry = "Select * from borrowerbills where CNIC=? AND Date=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, cnic);
            prepareStatement.setDate(2, new java.sql.Date(date.getTime()));
            result = prepareStatement.executeQuery();

            while (result.next()) {
                BorrowerBillDetailsEntity borrowerDetails = new BorrowerBillDetailsEntity();
                int id = result.getInt("Bill_Id");
                String billId = "" + id;
                borrowerDetails.setBillId(billId);
                borrowerDetails.setCnic(result.getString("CNIC"));
                borrowerDetails.setSubTotal(result.getDouble("SubTotal"));
                borrowerDetails.setTotalAmount(result.getDouble("TotalAmount"));
                borrowerDetails.setDiscount(result.getDouble("Discount"));
                borrowerDetails.setAmountPaid(result.getDouble("AmountPaid"));
                borrowerDetails.setDueAmount(result.getDouble("DueAmount"));
                borrowerDetails.setDate(result.getDate("Date"));

                getBills.add(borrowerDetails);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
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
        return getBills;
    }

    @Override
    public ArrayList<AmountBorrowedEntity> getAllBorrowedAmounts(String cnic, Date date) {
        ArrayList<AmountBorrowedEntity> amountBorrowed = new ArrayList<>();
        try {
            String querry = "Select * from amountborrowed where CNIC=? AND Date=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, cnic);
            prepareStatement.setDate(2, new java.sql.Date(date.getTime()));
            result = prepareStatement.executeQuery();

            while (result.next()) {
                AmountBorrowedEntity amount = new AmountBorrowedEntity();
                amount.setBillId(result.getInt("Bill_Id"));
                amount.setCnic(result.getString("CNIC"));
                amount.setLastAmount(result.getDouble("LastAmount"));
                amount.setNewAmount(result.getDouble("NewAmount"));
                amount.setTotalAmount(result.getDouble("TotalAmount"));
                amount.setDate(result.getDate("Date"));

                amountBorrowed.add(amount);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
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
        return amountBorrowed;
    }

    @Override
    public ArrayList<AmountPaidEntity> getAllPaidAmounts(String cnic, Date date) {
         ArrayList<AmountPaidEntity> amountPaid = new ArrayList<>();
        try {
            String querry = "Select * from amountpaid where CNIC=? AND Date=?";
            connection = DatabaseConnection.getConnection();
            prepareStatement = connection.prepareStatement(querry);
            prepareStatement.setString(1, cnic);
            prepareStatement.setDate(2, new java.sql.Date(date.getTime()));
            result = prepareStatement.executeQuery();

            while (result.next()) {
                AmountPaidEntity amount = new AmountPaidEntity();
                amount.setCnic(result.getString("CNIC"));
                amount.setLastAmount(result.getDouble("LastAmount"));
                amount.setPaid(result.getDouble("Paid"));
                amount.setTotalAmount(result.getDouble("RemainingAmount"));
                amount.setDate(result.getDate("Date"));

                amountPaid.add(amount);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
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
        return amountPaid;
    }

}
