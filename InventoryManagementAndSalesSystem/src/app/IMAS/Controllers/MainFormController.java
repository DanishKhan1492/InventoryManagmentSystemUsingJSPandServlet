package app.IMAS.Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MainFormController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getServletPath();
		HttpSession session=req.getSession();
		
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
		}else if(session.getAttribute("userName")!= null && path.equals("/mainForm")){
			req.getRequestDispatcher("Jsps/InventoryPages/MainForm.jsp").forward(req, resp);
		}else if(session.getAttribute("userName")!= null && path.equals("/stockPage")){
			req.getRequestDispatcher("Jsps/InventoryPages/Stock/StockMenu.jsp").forward(req, resp);
		}else if(session.getAttribute("userName")!= null && path.equals("/borrowerPage")){
			req.getRequestDispatcher("Jsps/InventoryPages/Borrower/BorrowerMenu.jsp").forward(req, resp);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
	
	
}
