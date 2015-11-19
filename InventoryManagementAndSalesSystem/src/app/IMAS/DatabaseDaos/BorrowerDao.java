
package app.IMAS.DatabaseDaos;

import java.util.ArrayList;
import app.IMAS.Enitities.BorrowerEntity;


public interface BorrowerDao {
    
    public ArrayList<BorrowerEntity> getAllBorrowers();
    public int addBorrwer(BorrowerEntity borrowerEntity);
    public BorrowerEntity searchBorrowerByCnic(String cnic);
    public ArrayList<BorrowerEntity> searchBorrowerByName(String borrowerName);
    public int updateBorrower(BorrowerEntity borrowerEntity);
    public int deleteBorrower(int id);
}
