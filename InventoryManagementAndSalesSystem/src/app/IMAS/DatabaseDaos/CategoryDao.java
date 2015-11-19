
package app.IMAS.DatabaseDaos;


import java.util.ArrayList;

import app.IMAS.Enitities.CategoryEntity;

public interface CategoryDao {
    public int addCategory(String category,String unit);
    public ArrayList<CategoryEntity> getCategories();
    public int updateCategory(CategoryEntity catEntity);
    public ArrayList<CategoryEntity> searchCategory(String catName);
    public int deleteCategory(String catName);
    public String getCategory(String itemName);
}
