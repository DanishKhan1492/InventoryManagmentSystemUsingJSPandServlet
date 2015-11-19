
package app.IMAS.Enitities;

import java.util.Date;

public class PriceChangeEntity {
	private int priceChangeId;
    private String itemName;
    private double oldPrice;
    private double newPrice;
    private Date date;

    public int getPriceChangeId() {
		return priceChangeId;
	}

	public void setPriceChangeId(int priceChangeId) {
		this.priceChangeId = priceChangeId;
	}

	public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
