package app.IMAS.Controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.IMAS.DatabaseDaos.CategoryDao;
import app.IMAS.DatabaseDaos.ProductDao;
import app.IMAS.DatabaseDaosImpl.CategoryDaoImpl;
import app.IMAS.DatabaseDaosImpl.ProductDaoImpl;
import app.IMAS.Enitities.CategoryEntity;
import app.IMAS.Enitities.PriceEntity;
import app.IMAS.Enitities.ProductEntity;

import com.sun.media.jfxmedia.logging.Logger;

public class UpdateItemController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	ProductEntity productEntity=new ProductEntity();
	private Map<String,String> validate=new HashMap<>();
	private ProductDao products=new ProductDaoImpl();
	private InsertItemController insertControl=new InsertItemController();
	private CategoryDao categories=new CategoryDaoImpl();
	private List<CategoryEntity> getCategories=new ArrayList<>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session=req.getSession();
		int status=0;
		
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
			
			
		}else if(path.equals("/updateItemPage")){
			String itemId=req.getParameter("Item_ID");
			int id=Integer.parseInt(itemId);
			productEntity=products.getItemsforupdate(id);
			getCategories=categories.getCategories();
			req.setAttribute("items", productEntity);
			req.setAttribute("categories", getCategories);
			req.getRequestDispatcher("Jsps/InventoryPages/Stock/UpdateItem.jsp").include(req, resp);
			
			
		}else if(path.equals("/updateItem")){
			String itemName=req.getParameter("itemName");
			String itemQuantity=req.getParameter("itemQuantity");
			String itemPrice=req.getParameter("itemPrice");
			String itemCategory=req.getParameter("itemCategory");
			
			validate=insertControl.validateItem(itemName, itemQuantity, itemPrice, itemCategory);
			
			if(!validate.isEmpty()){
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/InventoryPages/Stock/UpdateItem.jsp").include(req, resp);
			}else{
				productEntity.setItemName(itemName);
				productEntity.setItemQuantity(Double.parseDouble(itemQuantity));
				productEntity.setItemPrice(Double.parseDouble(itemPrice));
				productEntity.setItemCategory(itemCategory);
				
				status=products.updateItems(productEntity);
				
				if(status>0){
					insertControl.addProductAndCategories(req);
					req.setAttribute("msg", "Successfully Updated");
					req.getRequestDispatcher("Jsps/InventoryPages/Stock/InsertItem.jsp").forward(req, resp);
				}
				
				String price=req.getParameter("oldPrice");
				double oldPrice=Double.parseDouble(price);
				double newPrice=Double.parseDouble(itemPrice);
				
				if (oldPrice != newPrice) {
	                
	                int insertStatus = 0;
	                Date date=new Date();
	                DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
	                String dateNow = dateFormat.format(date.getTime());
	                try {
						date=dateFormat.parse(dateNow);
					} catch (ParseException e) {
						e.printStackTrace();
					}
	                
	                PriceEntity changePrice = new PriceEntity();
	                changePrice.setItemName(itemName);
	                changePrice.setOldPrice(oldPrice);
	                changePrice.setNewPrice(newPrice);
	                changePrice.setDate(date);
	                insertStatus = products.changePrice(changePrice);
	                if (insertStatus > 0) {
	                	Logger.logMsg(10, "Price saved Successfully");
	                }
	            }
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
	

}
