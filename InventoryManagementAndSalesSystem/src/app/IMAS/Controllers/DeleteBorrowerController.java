package app.IMAS.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.IMAS.DatabaseDaos.BorrowerDao;
import app.IMAS.DatabaseDaosImpl.BorrowerDaoImpl;
import app.IMAS.Enitities.BorrowerEntity;

public class DeleteBorrowerController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private BorrowerDao borrowers=new BorrowerDaoImpl();
	private List<BorrowerEntity> borrowersList=new ArrayList<>();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session=req.getSession();
		int status=0;
		
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
			
		}else if(path.equals("/deleteBorrower")){
			String id=req.getParameter("id");
		
			status=borrowers.deleteBorrower(Integer.parseInt(id));
			if(status>0){
				borrowersList=borrowers.getAllBorrowers();
				req.setAttribute("borrowers", borrowersList);
				req.setAttribute("msg", "Deleted Successfully");
				req.getRequestDispatcher("Jsps/InventoryPages/Borrower/AddBorrower.jsp").include(req, resp);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
	
}
