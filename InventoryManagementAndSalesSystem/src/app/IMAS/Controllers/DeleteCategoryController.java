package app.IMAS.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.IMAS.DatabaseDaos.CategoryDao;
import app.IMAS.DatabaseDaosImpl.CategoryDaoImpl;
import app.IMAS.Enitities.CategoryEntity;

public class DeleteCategoryController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private CategoryDao category=new CategoryDaoImpl();
	private List<CategoryEntity> getCategories=new ArrayList<>();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session=req.getSession();
		int status=0;
		
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
		}else if(path.equals("/deleteCategory") ){
			String catName=req.getParameter("Cat_Name");
			status=category.deleteCategory(catName);
			if(status>0){
				getCategories=category.getCategories();
				req.setAttribute("categories", getCategories);
				req.getRequestDispatcher("Jsps/InventoryPages/Stock/InsertCategory.jsp").forward(req, resp);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
	
}
