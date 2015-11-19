package app.IMAS.Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.IMAS.DatabaseDaos.ProductDao;
import app.IMAS.DatabaseDaosImpl.ProductDaoImpl;

public class DeleteItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao products=new ProductDaoImpl();
	private InsertItemController insertControl=new InsertItemController();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session=req.getSession();
		int status=0;
		
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
			
		}else if(path.equals("/deleteItem")){
			String itemId=req.getParameter("Item_ID");
			int id=Integer.parseInt(itemId);
			
			status=products.deleteItems(id);
			if(status>0){
				insertControl.addProductAndCategories(req);
				req.setAttribute("msg", "Deleted Successfully");
				req.getRequestDispatcher("Jsps/InventoryPages/Stock/InsertItem.jsp").include(req, resp);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
	

}
