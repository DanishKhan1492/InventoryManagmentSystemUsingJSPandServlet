/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.IMAS.DatabaseDaos;

import java.util.ArrayList;

import app.IMAS.Enitities.PriceEntity;
import app.IMAS.Enitities.ProductEntity;

public interface ProductDao {
        public int addItem(ProductEntity additem);
	public ArrayList<ProductEntity> getAllItems();
	public ArrayList<ProductEntity> searchItems(String itemName);
	public int updateItems(ProductEntity updateitems);
	public ProductEntity getItemsforupdate(int updateitems);
	public int deleteItems(int delitems);
	public ProductEntity getPrice(int itemid);
        public ArrayList<String> getItemNames(String categoryName);
        public int changePrice(PriceEntity price);
}
