
package app.IMAS.DatabaseDaos;

import java.util.ArrayList;
import app.IMAS.Enitities.BillItemsEntity;
import app.IMAS.Enitities.BillMainDetailsEntity;

public interface BillDao {
    public double getPrice(String itemName);
    public int insertBill(BillMainDetailsEntity billMain, ArrayList<BillItemsEntity> billItems);
    public ArrayList<BillMainDetailsEntity> getAllBills();
    public BillMainDetailsEntity getSpecificBill(String cnicMobile);
}
