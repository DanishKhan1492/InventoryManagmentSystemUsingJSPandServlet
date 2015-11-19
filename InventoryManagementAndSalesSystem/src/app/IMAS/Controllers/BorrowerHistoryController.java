package app.IMAS.Controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.IMAS.DatabaseDaosImpl.FullDebtDetailsDaoImpl;
import app.IMAS.Enitities.AmountBorrowedEntity;
import app.IMAS.Enitities.AmountPaidEntity;
import app.IMAS.Enitities.BillItemsEntity;
import app.IMAS.Enitities.BorrowerBillDetailsEntity;
import app.IMAS.Enitities.BorrowerEntity;

public class BorrowerHistoryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, String> validate = new HashMap<>();
	private final FullDebtDetailsDaoImpl borrowerDetails = new FullDebtDetailsDaoImpl();
    private BorrowerEntity borrower = new BorrowerEntity();
    private ArrayList<BorrowerBillDetailsEntity> getBorrowerBills = new ArrayList<>();
    private ArrayList<ArrayList<BillItemsEntity>> getBillItems = new ArrayList<>();
    private ArrayList<AmountBorrowedEntity> getAllBorrowedAmounts = new ArrayList<>();
    private ArrayList<AmountPaidEntity> getAllPaidAmounts = new ArrayList<>();
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String path = req.getServletPath();
		HttpSession session=req.getSession();
		Date date=null;
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
			
		}else if(path.equals("/showHistoryPage")){
			req.getRequestDispatcher("Jsps/InventoryPages/Borrower/ShowBorrowerHistory.jsp").include(req, resp);
		}else if(path.equals("/showHistory")){
			
			String cnic=req.getParameter("borrowerCnic");
			String dateStr=req.getParameter("date");
			
			validate=validateHistory(cnic, dateStr);
			
			if(!validate.isEmpty()){
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/InventoryPages/Borrower/ShowBorrowerHistory.jsp").include(req, resp);
			}else{
				if(!dateStr.equals("")){
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                try {
						date=dateFormat.parse(dateStr);
					} catch (ParseException e) {
						e.printStackTrace();
					}

				}
				
				
				if(!cnic.equals("") && !dateStr.equals("")){
					borrower = borrowerDetails.getSpecificBorrower(cnic);
		            getBorrowerBills = borrowerDetails.getBorrowerBills(cnic, date);
		            for (BorrowerBillDetailsEntity borrowerbill : getBorrowerBills) {
		                String id = borrowerbill.getBillId();
		                int billid = Integer.parseInt(id);
		                ArrayList<BillItemsEntity> nestedBillItems=new ArrayList<>();
		                nestedBillItems = borrowerDetails.getBillItems(billid);
		                getBillItems.add(nestedBillItems);
		            }
		            getAllBorrowedAmounts = borrowerDetails.getAllBorrowedAmounts(cnic, date);
		            getAllPaidAmounts = borrowerDetails.getAllPaidAmounts(cnic, date);
		            
		            req.setAttribute("borrower", borrower);
		            req.setAttribute("borrowerBills", getBorrowerBills);
		            req.setAttribute("borrowerBillItems", getBillItems);
		            req.setAttribute("borrowerAmountBorrowed", getAllBorrowedAmounts);
		            req.setAttribute("borrowerAmountPaid", getAllPaidAmounts);
		            req.getRequestDispatcher("Jsps/InventoryPages/Borrower/ShowBorrowerHistory.jsp").include(req, resp);
		            
				}else if(!cnic.equals("")){
					
					borrower = borrowerDetails.getSpecificBorrower(cnic);
		            getBorrowerBills = borrowerDetails.getBorrowerBills(cnic);

		            for (BorrowerBillDetailsEntity borrowerbill : getBorrowerBills) {
		                
		                String id = borrowerbill.getBillId();
		                int billid = Integer.parseInt(id);
		                ArrayList<BillItemsEntity> nestedBillItems=new ArrayList<>();
		                nestedBillItems = borrowerDetails.getBillItems(billid);
		                getBillItems.add(nestedBillItems);
		            }

		            getAllBorrowedAmounts = borrowerDetails.getAllBorrowedAmounts(cnic);
		            getAllPaidAmounts = borrowerDetails.getAllPaidAmounts(cnic);
		            req.setAttribute("borrower", borrower);
		            
		            req.setAttribute("borrowerBills", getBorrowerBills);
		            req.setAttribute("borrowerBillItems", getBillItems);
		            req.setAttribute("borrowerAmountBorrowed", getAllBorrowedAmounts);
		            req.setAttribute("borrowerAmountPaid", getAllPaidAmounts);
		            req.getRequestDispatcher("Jsps/InventoryPages/Borrower/ShowBorrowerHistory.jsp").include(req, resp);
				}
			}
			
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	public Map<String, String> validateHistory(String borrowerCnic, String date) {
		Map<String, String> message = new HashMap<>();
		if ((borrowerCnic.equals("") || borrowerCnic.isEmpty()) && (date.equals("") || date.isEmpty()) ) {
			message.put("errorBorrowerCnic", "Please Enter <b>Cnic</b> or  <b>Cnic and Date Both</b>");
		}else if(borrowerCnic.equals("") && !date.equals("")){
			message.put("errorBorrowerCnic", "Please Enter <b>Cnic</b> Also");
		}
		return message;
	}
	
}
