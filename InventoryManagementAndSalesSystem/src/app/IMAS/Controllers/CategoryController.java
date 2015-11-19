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
import app.IMAS.DatabaseDaosImpl.CategoryDaoImpl;
import app.IMAS.Enitities.CategoryEntity;

public class CategoryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String,String> validate=new HashMap<>();
	private List<CategoryEntity> getCategories=new ArrayList<>();
	private CategoryDao categoryInsert=new CategoryDaoImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getServletPath();
		HttpSession session =req.getSession();
		int status=0;
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
		}else if(session.getAttribute("userName") != null && path.equals("/insertCategoryPage")){
			getCategories=categoryInsert.getCategories();
			req.setAttribute("categories", getCategories);
			req.getRequestDispatcher("Jsps/InventoryPages/Stock/InsertCategory.jsp").forward(req, resp);
			
		}else if(session.getAttribute("userName") != null && path.equals("/insertCategory")){
			String catName=req.getParameter("catName");
			String catUnit=req.getParameter("catUnit");
			
			validate=validateCategory(catName, catUnit);
			
			if(!validate.isEmpty()){
				getCategories=categoryInsert.getCategories();
				req.setAttribute("categories", getCategories);
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/InventoryPages/Stock/InsertCategory.jsp").forward(req, resp);
				
			}else{
				status=categoryInsert.addCategory(catName, catUnit);
				if(status>0){
					getCategories=categoryInsert.getCategories();
					req.setAttribute("categories", getCategories);
					req.getRequestDispatcher("Jsps/InventoryPages/Stock/InsertCategory.jsp").forward(req, resp);
				}else{
					req.setAttribute("message", "Somthing Went Wrong Try Again");
					req.getRequestDispatcher("Jsps/InventoryPages/Stock/InsertCategory.jsp").forward(req, resp);
				}
			}
			
			
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	public Map<String,String> validateCategory(String catName, String catUnit){
		Map<String,String> message=new HashMap<>();
			if(catName.equals("") || catName.isEmpty()){
				message.put("errorCategoryName", "Enter Category Name");
			
			}
			if(catUnit.equals("") || catUnit.isEmpty() || catUnit.equals("Select Unit")){
				message.put("errorCategoryUnit", "Select Category Unit");
			}
			
			if(message.containsKey("errorCategoryName") && message.containsKey("errorCategoryUnit")){
			}else if(message.containsKey("errorCategoryName") && !message.containsKey("errorCategoryUnit")){
				message.put("catUnit", catUnit);
			}else if(!message.containsKey("errorCategoryName") && message.containsKey("errorCategoryUnit")){
				message.put("catName", catName);
			}
			
			
		return message;
	}

}
