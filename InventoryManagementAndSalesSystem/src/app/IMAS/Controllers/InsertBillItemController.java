package app.IMAS.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.IMAS.DatabaseDaos.BillDao;
import app.IMAS.DatabaseDaosImpl.BillDaoImpl;
import app.IMAS.DatabaseDaosImpl.CategoryDaoImpl;
import app.IMAS.Enitities.BillItemsEntity;
import app.IMAS.Enitities.CategoryEntity;

public class InsertBillItemController extends HttpServlet {

	private static final long serialVersionUID = 1L;
    private final BillDao bill = new BillDaoImpl();
    private ArrayList<CategoryEntity> getCategories = new ArrayList<>();
	private final CategoryDaoImpl cat = new CategoryDaoImpl();
	private Map<String,String> validate=new HashMap<>();
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session =req.getSession();
	    double item_Quantity = 0, price = 0, totalPrice = 0;
	    BillItemsEntity billItems = new BillItemsEntity();
	    double totalAmount=0.0;
	    if(session.getAttribute("totalAmount") !=null){
	    	totalAmount = (Double)session.getAttribute("totalAmount");
	    }
	    
	    
		if(session.getAttribute("userName") ==null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
			
		}else {
			
			String categoryName=req.getParameter("itemCategory");			
			String itemName=req.getParameter("itemName");
			String itemQuantity=req.getParameter("itemQuantity");
			
			validate=validateBilling(categoryName, itemName, itemQuantity); 
			
			if(!validate.isEmpty()){
				getCategories=cat.getCategories();
				req.setAttribute("categories", getCategories);
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/InventoryPages/Billing/CreateBill.jsp").forward(req, resp);
			}else{
				item_Quantity = Double.parseDouble(itemQuantity);
				price=bill.getPrice(itemName);
				totalPrice=item_Quantity*price;
				
				 billItems.setItemName(itemName);
		         billItems.setItemQuantity(item_Quantity);
		         billItems.setItemPrice(price);
		         billItems.setTotalItemPrice(totalPrice);
		         
		         totalAmount += totalPrice;
		         
		         ArrayList<BillItemsEntity> itemsList = (ArrayList<BillItemsEntity>) session.getAttribute("billItems");
		         if (itemsList == null) {
		        	 itemsList = new ArrayList<BillItemsEntity>();
		         }
		         
		         itemsList.add(billItems);
		         
		         getCategories=cat.getCategories();
		         
		         session.setAttribute("billItems", itemsList);
		         session.setAttribute("totalAmount", totalAmount);
		         req.setAttribute("totalAmount", session.getAttribute("totalAmount"));
		         req.setAttribute("billItems", session.getAttribute("billItems"));
		         req.setAttribute("categories", getCategories);
		         req.getRequestDispatcher("Jsps/InventoryPages/Billing/CreateBill.jsp").forward(req, resp);
				
			}	
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	public Map<String,String> validateBilling(String catName, String itemName,String itemQuantity){
		Map<String,String> message=new HashMap<>();
		
			if(catName.equals("") || catName.isEmpty() || catName.equals("Select Category")){
				message.put("errorCategoryName", "Select Category Name");
			}
			
			if(itemName.equals("") || itemName.isEmpty() || itemName.equals("Select Items")){
				message.put("errorItemName", "Select Item Name");
			
			}
			
			if(itemQuantity.equals("") || itemQuantity.isEmpty() ){
				message.put("errorItemQuantity", "Enter Item Quantity");
			
			}
			
		return message;
	}
}
