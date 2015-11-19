
package app.IMAS.DatabaseDaos;


import java.util.ArrayList;

import app.IMAS.Enitities.AmountBorrowedEntity;
import app.IMAS.Enitities.AmountPaidEntity;
import app.IMAS.Enitities.BillItemsEntity;
import app.IMAS.Enitities.BorrowerBillDetailsEntity;


public interface BorrowerBillDao {
    public int insertBill(BorrowerBillDetailsEntity billMain, ArrayList<BillItemsEntity> billItems);
    public ArrayList<BorrowerBillDetailsEntity> getAllBills();
    public BorrowerBillDetailsEntity getSpecificBill(String cnicMobile);
    public String checkBorrower(String cnic);
    public int updateDebt(String Cnic,double amount);
    public double getRemaingingAmount(String cnic);
    public int insertAmountBorrowed(AmountBorrowedEntity amountBorrowed);
    public int amountPaid(AmountPaidEntity amountPaid);
}
