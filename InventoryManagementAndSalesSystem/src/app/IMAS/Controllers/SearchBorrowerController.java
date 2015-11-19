package app.IMAS.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.IMAS.DatabaseDaos.BorrowerDao;
import app.IMAS.DatabaseDaosImpl.BorrowerDaoImpl;
import app.IMAS.Enitities.BorrowerEntity;

public class SearchBorrowerController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private Map<String,String> validate=new HashMap<>();
	private BorrowerEntity borrowerEntity=new BorrowerEntity();
	private BorrowerDao borrowers=new BorrowerDaoImpl();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session=req.getSession();
		
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
			
		}else if(path.equals("/searchBorrowerPage")){
			req.getRequestDispatcher("Jsps/InventoryPages/Borrower/SearchBorrower.jsp").include(req, resp);
			
		}else if(path.equals("/searchBorrower")){
			String cnic=req.getParameter("borrowerCnic");
			validate=validateSearch(cnic);
			
			if(!validate.isEmpty()){
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/InventoryPages/Borrower/SearchBorrower.jsp").include(req, resp);
			}else{
				borrowerEntity=borrowers.searchBorrowerByCnic(cnic);
				if(borrowerEntity.getId() == 0){
					req.setAttribute("msg","Borrower Not Found");
				}else{
					req.setAttribute("searchBorrowers",borrowerEntity);
				}
				
				req.getRequestDispatcher("Jsps/InventoryPages/Borrower/SearchBorrower.jsp").include(req, resp);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}

	public Map<String,String> validateSearch(String borrowerCnic){
		 Map<String,String> message=new HashMap<>();
		if(borrowerCnic.equals("")|| borrowerCnic.isEmpty() ){
			message.put("borrowerCnic", "Please Enter Borrower Cnic");
		}
		return message;
	}
}
