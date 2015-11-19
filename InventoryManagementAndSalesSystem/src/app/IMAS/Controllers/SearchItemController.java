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

import app.IMAS.DatabaseDaos.ProductDao;
import app.IMAS.DatabaseDaosImpl.ProductDaoImpl;
import app.IMAS.Enitities.ProductEntity;

public class SearchItemController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ProductDao products=new ProductDaoImpl();
	private List<ProductEntity> getProducts=new ArrayList<>();
	private Map<String,String> validate=new HashMap<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session=req.getSession();
		
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
			
		}else if(path.equals("/searchItemPage")){
			req.getRequestDispatcher("Jsps/InventoryPages/Stock/SearchItem.jsp").include(req, resp);
			
		}else if(path.equals("/searchItem")){
			String itemName=req.getParameter("searchItem");
			validate=validateSearch(itemName);
			
			if(!validate.isEmpty()){
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/InventoryPages/Stock/SearchItem.jsp").include(req, resp);
			}else{
				getProducts=products.searchItems(itemName);
				if(getProducts.isEmpty()){
					req.setAttribute("validate","Items Not Found");
				}else{
					req.setAttribute("items",getProducts);
				}
				
				req.getRequestDispatcher("Jsps/InventoryPages/Stock/SearchItem.jsp").include(req, resp);
			}
			
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
	
	public Map<String,String> validateSearch(String itemName){
		 Map<String,String> message=new HashMap<>();
		if(itemName.equals("")|| itemName.isEmpty() ){
			message.put("msg", "Please Enter Item Name or Any Letter");
		}
		return message;
	}
}
