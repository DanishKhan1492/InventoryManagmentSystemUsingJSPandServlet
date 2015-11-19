
package app.IMAS.DatabaseDaos;
	
import java.util.ArrayList;

import app.IMAS.Enitities.PriceChangeEntity;

public interface PriceChangeDao {
    public ArrayList<PriceChangeEntity> getSpecificPrice(String itemName);
    public ArrayList<PriceChangeEntity> getAllPrice();
}
