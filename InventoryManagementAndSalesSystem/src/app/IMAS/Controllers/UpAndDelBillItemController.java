package app.IMAS.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.IMAS.DatabaseDaos.CategoryDao;
import app.IMAS.DatabaseDaosImpl.CategoryDaoImpl;
import app.IMAS.Enitities.BillItemsEntity;
import app.IMAS.Enitities.CategoryEntity;


public class UpAndDelBillItemController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	 private ArrayList<CategoryEntity> getCategories = new ArrayList<>();
		private final CategoryDao cat = new CategoryDaoImpl();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		String path=req.getServletPath();
		
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
		}else if(path.equals("/updateBillItemsPage")){
			
			String itemName=req.getParameter("itemName");
			
			ArrayList<BillItemsEntity> itemsList = (ArrayList<BillItemsEntity>) session.getAttribute("billItems");

			if (itemsList == null) {
				itemsList = new ArrayList<BillItemsEntity>();
			}

			ArrayList<BillItemsEntity> editItem = new ArrayList<BillItemsEntity>();
			boolean check = false;
			int index;
			for (BillItemsEntity itemnam : itemsList) {
				if (itemnam.getItemName().equals(itemName)) {
					index = itemsList.indexOf(itemnam);
					editItem.add(itemsList.get(index));
					check = true;

				}
			}

			double totalAmount=(Double)session.getAttribute("totalAmount");
			double modifiedAmount=0.0;
			
			if (check == true) {
				Iterator<BillItemsEntity> iter = itemsList.iterator();
				while (iter.hasNext()) {
					BillItemsEntity getitem = iter.next();
					if (getitem.getItemName().equals(itemName)) {
						modifiedAmount=getitem.getTotalItemPrice();
						iter.remove();

					}
				}
			}
			
			totalAmount=totalAmount-modifiedAmount;
			session.removeAttribute("totalAmount");
			session.setAttribute("totalAmount", totalAmount);
			getCategories=cat.getCategories();
			
			String catName=cat.getCategory(itemName);
			
			req.setAttribute("categories", getCategories);
			req.setAttribute("categ", catName);
			req.setAttribute("items", editItem);
			req.getRequestDispatcher("Jsps/InventoryPages/Billing/EditBill.jsp").forward(req, resp);
			
		}else if(path.equals("/deleteBillItems")){
			
			String itemName=req.getParameter("itemName");
			
			ArrayList<BillItemsEntity> itemList=(ArrayList<BillItemsEntity>)session.getAttribute("billItems");		
			double totalAmount=0;
			double modifiedamount=0;
			
			if(session.getAttribute("totalAmount") != null){
				totalAmount=(Double)session.getAttribute("totalAmount");
			}
			
			if(itemList==null){
				itemList=new ArrayList<BillItemsEntity>();
			}
			Iterator<BillItemsEntity> iter = itemList.iterator();
			while(iter.hasNext()){
				BillItemsEntity getitem=iter.next();
				if(getitem.getItemName().equals(itemName)){
					modifiedamount=getitem.getTotalItemPrice();
					iter.remove();					
				}
			}
			totalAmount=totalAmount-modifiedamount;
			
			session.removeAttribute("totalAmount");
			session.setAttribute("totalAmount", totalAmount);
			
			session.setAttribute("billItems", itemList);
			req.getRequestDispatcher("Jsps/InventoryPages/Billing/CreateBill.jsp").forward(req, resp);
			
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doGet(req, resp);
	}
	
	
}
