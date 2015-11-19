/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.IMAS.DatabaseDaos;


import java.util.ArrayList;
import java.util.Date;

import app.IMAS.Enitities.AmountBorrowedEntity;
import app.IMAS.Enitities.AmountPaidEntity;
import app.IMAS.Enitities.BillItemsEntity;
import app.IMAS.Enitities.BorrowerBillDetailsEntity;
import app.IMAS.Enitities.BorrowerEntity;

public interface FullDebtDetailsDao {
    public BorrowerEntity getSpecificBorrower(String cnic);
    public ArrayList<BorrowerBillDetailsEntity> getBorrowerBills(String cnic);
    public ArrayList<BillItemsEntity> getBillItems(int billId);
    public ArrayList<AmountBorrowedEntity> getAllBorrowedAmounts(String cnic);
    public ArrayList<AmountPaidEntity> getAllPaidAmounts(String cnic);
    
    public ArrayList<BorrowerBillDetailsEntity> getBorrowerBills(String cnic, Date date);
    public ArrayList<AmountBorrowedEntity> getAllBorrowedAmounts(String cnic,Date date);
    public ArrayList<AmountPaidEntity> getAllPaidAmounts(String cnic,Date date);
}
