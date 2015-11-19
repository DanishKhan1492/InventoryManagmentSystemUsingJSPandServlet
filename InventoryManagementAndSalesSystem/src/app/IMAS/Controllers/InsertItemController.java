package app.IMAS.Controllers;

import java.io.IOException;
import java.util.ArrayList;
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
import app.IMAS.Enitities.ProductEntity;

public class InsertItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<ProductEntity> getProducts=new ArrayList<>();
	private List<CategoryEntity> getCategories=new ArrayList<>();
	ProductEntity productEntity=new ProductEntity();
	private ProductDao products=new ProductDaoImpl();
	private CategoryDao categories=new CategoryDaoImpl();
	private Map<String,String> validate=new HashMap<>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session=req.getSession();
		int status=0;
		
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
			
		}else if(path.equals("/insertItemPage")){
			addProductAndCategories(req);
			req.getRequestDispatcher("Jsps/InventoryPages/Stock/InsertItem.jsp").include(req, resp);
			
			
		}else if(path.equals("/insertItem") ){
			String itemName=req.getParameter("itemName");
			String itemQuantity=req.getParameter("itemQuantity");
			String itemPrice=req.getParameter("itemPrice");
			String itemCategory=req.getParameter("itemCategory");
			
			validate=validateItem(itemName, itemQuantity, itemPrice, itemCategory);
			
			if(!validate.isEmpty()){
				addProductAndCategories(req);
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/InventoryPages/Stock/InsertItem.jsp").include(req, resp);
			}else{
				productEntity.setItemName(itemName);
				productEntity.setItemQuantity(Double.parseDouble(itemQuantity));
				productEntity.setItemPrice(Double.parseDouble(itemPrice));
				productEntity.setItemCategory(itemCategory);
				
				status=products.addItem(productEntity);
				
				if(status>0){
					addProductAndCategories(req);
					req.setAttribute("msg", "Successfully Added");
					req.getRequestDispatcher("Jsps/InventoryPages/Stock/InsertItem.jsp").forward(req, resp);
				}
			}
			
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {	
		doGet(req, resp);
	}
	
	public Map<String,String> validateItem(String itemName, String itemQuantity, String itemPrice,String itemCategory){
		Map<String,String> message=new HashMap<>();
			if(itemName.equals("") || itemName.isEmpty()){
				message.put("errorItemName", "Enter Item Name");
			}
			if(itemQuantity.equals("") || itemQuantity.isEmpty()){
				message.put("errorItemQuantity", "Enter Item Quantity");
			}
			if(itemPrice.equals("") || itemPrice.isEmpty()){
				message.put("errorItemPrice", "Enter Item Price");
			}
			if(itemCategory.equals("") || itemCategory.isEmpty() || itemCategory.equals("Select Category")){
				message.put("errorItemCategory", "Select Item Category");
			}
			
			if(message.containsKey("errorItemName") && message.containsKey("errorItemQuantity") && message.containsKey("errorItemPrice") && message.containsKey("itemCategory")){
			}else if(message.containsKey("errorItemName") ){
				message.put("itemQuantity", itemQuantity);
				message.put("itemPrice", itemPrice);
				message.put("itemCategory", itemCategory);
			}else if(message.containsKey("errorItemQuantity")){
				message.put("itemName", itemName);
				message.put("itemPrice", itemPrice);
				message.put("itemCategory", itemCategory);
			}else if(message.containsKey("errorItemPrice")){
				message.put("itemName", itemName);
				message.put("itemQuantity", itemQuantity);
				message.put("itemCategory", itemCategory);
			}else if(message.containsKey("errorItemCategory")){
				message.put("itemName", itemName);
				message.put("itemQuantity", itemQuantity);
				message.put("itemPrice", itemPrice);
			}
			
			
		return message;
	}
	
	public void addProductAndCategories(HttpServletRequest req){
		getProducts=products.getAllItems();
		getCategories=categories.getCategories();
		req.setAttribute("items", getProducts);
		req.setAttribute("categories", getCategories);
	}
}
