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

public class AddBorrowerController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private List<BorrowerEntity> borrowersList=new ArrayList<>();
	private BorrowerEntity borrowerEntity=new BorrowerEntity();
	private BorrowerDao borrowers=new BorrowerDaoImpl();
	private Map<String,String> validate=new HashMap<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session=req.getSession();
		int status=0;
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
			
		}else if(path.equals("/addBorrowerPage")){
			borrowersList=borrowers.getAllBorrowers();
			req.setAttribute("borrowers", borrowersList);
			req.getRequestDispatcher("Jsps/InventoryPages/Borrower/AddBorrower.jsp").include(req, resp);
			
			
		}else if(path.equals("/addBorrower") ){
			
			String borrowerName=req.getParameter("borrowerName");
			String borrowerCnic=req.getParameter("borrowerCnic");
			String borrowerAddress=req.getParameter("borrowerAddress");
			String borrowerPhone=req.getParameter("borrowerPhone");
			String borrowerDebt=req.getParameter("borrowerDebt");
			
			validate=validateBorrower(borrowerName, borrowerCnic, borrowerAddress, borrowerPhone,borrowerDebt);
			
			if(!validate.isEmpty()){
				
				borrowersList=borrowers.getAllBorrowers();
				req.setAttribute("borrowers", borrowersList);
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/InventoryPages/Borrower/AddBorrower.jsp").include(req, resp);
			}else{
				borrowerEntity.setBorrowerName(borrowerName);
				borrowerEntity.setCnic(borrowerCnic);
				borrowerEntity.setAddress(borrowerAddress);
				borrowerEntity.setContactNumber(borrowerPhone);
				borrowerEntity.setDebtAmount(Double.parseDouble(borrowerDebt));
				
				status=borrowers.addBorrwer(borrowerEntity);
				
				if(status>0){
					borrowersList=borrowers.getAllBorrowers();
					req.setAttribute("borrowers", borrowersList);
					req.setAttribute("msg", "Added Successfully");
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
	
	public Map<String,String> validateBorrower(String borrowerName, String borrowerCnic, String borrowerAddress,String borrowerPhone,String borrowerDebt){
		Map<String,String> message=new HashMap<>();
			if(borrowerName.equals("") || borrowerName.isEmpty()){
				message.put("errorBorrowerName", "Enter Borrower Name");
			}
			if(borrowerCnic.equals("") || borrowerCnic.isEmpty()){
				message.put("errorBorrowerCnic", "Enter  Borrower Cnic");
			}
			if(borrowerAddress.equals("") || borrowerAddress.isEmpty()){
				message.put("errorBorrowerAddress", "Enter Borrower Address");
			}
			if(borrowerPhone.equals("") || borrowerPhone.isEmpty()){
				message.put("errorBorrowerPhone", "Select Borrower Phone");
			}
			if(borrowerDebt.equals("") || borrowerDebt.isEmpty()){
				message.put("errorBorrowerDebt", "Select Borrower Debt");
			}
			
			if(message.containsKey("errorBorrowerName") && message.containsKey("errorBorrowerCnic") && message.containsKey("errorBorrowerAddress") && message.containsKey("errorBorrowerPhone") && message.containsKey("errorBorrowerDebt")){
			}else if(message.containsKey("errorBorrowerName") ){
				message.put("borrowerCnic", borrowerCnic);
				message.put("borrowerAddress", borrowerAddress);
				message.put("borrowerPhone", borrowerPhone);
				message.put("borrowerDebt", borrowerDebt);
			}else if(message.containsKey("errorBorrowerCnic")){
				message.put("borrowerName", borrowerName);
				message.put("borrowerAddress", borrowerAddress);
				message.put("borrowerPhone", borrowerPhone);
				message.put("borrowerDebt", borrowerDebt);
			}else if(message.containsKey("errorBorrowerAddress")){
				message.put("borrowerName", borrowerName);
				message.put("borrowerCnic", borrowerCnic);
				message.put("borrowerPhone", borrowerPhone);
				message.put("borrowerDebt", borrowerDebt);
			}else if(message.containsKey("errorBorrowerPhone")){
				message.put("borrowerName", borrowerName);
				message.put("borrowerCnic", borrowerCnic);
				message.put("borrowerAddress", borrowerAddress);
				message.put("borrowerDebt", borrowerDebt);
			}else if(message.containsKey("errorBorrowerDebt")){
				message.put("borrowerName", borrowerName);
				message.put("borrowerCnic", borrowerCnic);
				message.put("borrowerAddress", borrowerAddress);
				message.put("borrowerPhone", borrowerPhone);
			}
			
			
		return message;
	}
}
