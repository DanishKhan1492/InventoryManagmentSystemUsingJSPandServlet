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

import app.IMAS.DatabaseDaos.BorrowerDao;
import app.IMAS.DatabaseDaosImpl.BorrowerDaoImpl;
import app.IMAS.Enitities.BorrowerEntity;

public class UpdateBorrowerController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private List<BorrowerEntity> borrowersList=new ArrayList<>();
	private BorrowerEntity borrowerEntity=new BorrowerEntity();
	private BorrowerDao borrowers=new BorrowerDaoImpl();
	private AddBorrowerController addBorrowerController=new AddBorrowerController();
	private Map<String,String> validate=new HashMap<>();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session=req.getSession();
		int status=0;
		
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
			
		}else if(path.equals("/updateBorrowerPage")){
			String cnic=req.getParameter("cnic");
			borrowerEntity=borrowers.searchBorrowerByCnic(cnic);
			req.setAttribute("borrower", borrowerEntity);
			req.getRequestDispatcher("Jsps/InventoryPages/Borrower/UpdateBorrower.jsp").include(req, resp);
			
			
		}else if(path.equals("/updateBorrower") ){
			String borrowerName=req.getParameter("borrowerName");
			String borrowerCnic=req.getParameter("borrowerCnic");
			String borrowerAddress=req.getParameter("borrowerAddress");
			String borrowerPhone=req.getParameter("borrowerPhone");
			String borrowerDebt=req.getParameter("borrowerDebt");
			
			validate=addBorrowerController.validateBorrower(borrowerName, borrowerCnic, borrowerAddress, borrowerPhone,borrowerDebt);
			
			if(!validate.isEmpty()){
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/InventoryPages/Borrower/AddBorrower.jsp").include(req, resp);
			}else{
				borrowerEntity.setBorrowerName(borrowerName);
				borrowerEntity.setCnic(borrowerCnic);
				borrowerEntity.setAddress(borrowerAddress);
				borrowerEntity.setContactNumber(borrowerPhone);
				borrowerEntity.setDebtAmount(Double.parseDouble(borrowerDebt));
				
				status=borrowers.updateBorrower(borrowerEntity);
				
				if(status>0){
					borrowersList=borrowers.getAllBorrowers();
					req.setAttribute("borrowers", borrowersList);
					req.setAttribute("msg", "Updated Successfully");
					req.getRequestDispatcher("Jsps/InventoryPages/Borrower/AddBorrower.jsp").include(req, resp);
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
}
