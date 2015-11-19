package app.IMAS.Controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import app.IMAS.DatabaseDaosImpl.BorrowerBillDaoImpl;
import app.IMAS.Enitities.AmountPaidEntity;


public class PayAmountController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, String> validate = new HashMap<>();
	private final BorrowerBillDaoImpl borrowerAmount = new BorrowerBillDaoImpl();
	private final AmountPaidEntity amount_Paid = new AmountPaidEntity();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session = req.getSession();
		int status = 0;
		if (session.getAttribute("userName") == null) {
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");

		} else if (path.equals("/payAmountPage")) {
			req.getRequestDispatcher("Jsps/InventoryPages/Borrower/PayAmount.jsp").include(req,resp);

		} else if (path.equals("/payAmount")) {

			String cnic = req.getParameter("borrowerCnic");
			String amount = req.getParameter("payAmount");
			validate = validateSearch(cnic, amount);

			if (!validate.isEmpty()) {
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/InventoryPages/Borrower/PayAmount.jsp").include(req, resp);
			} else {

				double amountPaid = Double.parseDouble(amount);
				double amountRemains = 0;
				double lastAmount = 0;
				double paidAmount = 0;

				Date date = new Date();
				DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
				String dateNow = dateFormat.format(date.getTime());
				try {
					date = dateFormat.parse(dateNow);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				amountRemains = borrowerAmount.getRemaingingAmount(cnic);
				lastAmount = amountRemains;
				paidAmount = amountPaid;
				amountRemains -= amountPaid;

				status = borrowerAmount.updateDebt(cnic, amountRemains);

				// Inserting Paid Amount to Database

				amount_Paid.setCnic(cnic);
				amount_Paid.setLastAmount(lastAmount);
				amount_Paid.setPaid(paidAmount);
				amount_Paid.setTotalAmount(amountRemains);
				amount_Paid.setDate(date);
				
				status=borrowerAmount.amountPaid(amount_Paid);

				if (status > 0) {
					req.setAttribute("msg", "Amount Paid");
				}else{
					req.setAttribute("msg", "Wrong CNIC Entered");
				} 
				req.getRequestDispatcher("Jsps/InventoryPages/Borrower/PayAmount.jsp").include(req, resp);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	public Map<String, String> validateSearch(String borrowerCnic, String amount) {
		Map<String, String> message = new HashMap<>();
		if (borrowerCnic.equals("") || borrowerCnic.isEmpty()) {
			message.put("errorBorrowerCnic", "Please Enter Borrower Cnic");
		}
		if (amount.equals("") || amount.isEmpty()) {
			message.put("errorPayAmount", "Please Enter Payment Amount");
		}

		if (message.containsKey("errorBorrowerCnic")&& message.containsKey("errorPayAmount")) {
		} else if (message.containsKey("errorBorrowerCnic")&& !message.containsKey("errorPayAmount")) {
			message.put("borrowerCnic", borrowerCnic);
		} else if (!message.containsKey("errorBorrowerCnic")&& message.containsKey("errorPayAmount")) {
			message.put("payAmount", amount);
		}

		return message;
	}

}
