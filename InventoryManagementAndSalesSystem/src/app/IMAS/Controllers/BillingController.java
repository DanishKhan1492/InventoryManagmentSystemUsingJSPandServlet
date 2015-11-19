package app.IMAS.Controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
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
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import app.IMAS.DatabaseConnection.DatabaseConnection;
import app.IMAS.DatabaseDaos.BillDao;
import app.IMAS.DatabaseDaos.BorrowerBillDao;
import app.IMAS.DatabaseDaosImpl.BillDaoImpl;
import app.IMAS.DatabaseDaosImpl.BorrowerBillDaoImpl;
import app.IMAS.DatabaseDaosImpl.CategoryDaoImpl;
import app.IMAS.Enitities.AmountBorrowedEntity;
import app.IMAS.Enitities.BillItemsEntity;
import app.IMAS.Enitities.BillMainDetailsEntity;
import app.IMAS.Enitities.BorrowerBillDetailsEntity;
import app.IMAS.Enitities.CategoryEntity;
import app.IMAS.ExtraClasses.GenerateBillId;

public class BillingController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ArrayList<CategoryEntity> getCategories = new ArrayList<>();
	private final CategoryDaoImpl cat = new CategoryDaoImpl();
	private final BillDao bill = new BillDaoImpl();
	private final BorrowerBillDao borrower_Bill = new BorrowerBillDaoImpl();
	private Map<String,String> validate=new HashMap<>();
	
	

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session = req.getSession();
		BillMainDetailsEntity billMain = new BillMainDetailsEntity();
		ArrayList<BillItemsEntity> billItems = (ArrayList<BillItemsEntity>) session.getAttribute("billItems");
		ArrayList<BillItemsEntity> secondBillItems = new ArrayList<>();
		BorrowerBillDetailsEntity borrowerBill = new BorrowerBillDetailsEntity();
		AmountBorrowedEntity amountBorrowed = new AmountBorrowedEntity();

		Date date = new Date();
		int status = 0;

		if (session.getAttribute("userName") == null) {
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");

		} else if (path.equals("/createBillPage")) {
			String billId = GenerateBillId.Generate_Bill_Id("customerbills");
			getCategories = cat.getCategories();
			req.setAttribute("categories", getCategories);
			req.setAttribute("BillId", billId);
			req.getRequestDispatcher("Jsps/InventoryPages/Billing/CreateBill.jsp").forward(req,resp);
		} else if (path.equals("/createBill")) {

			String custType = req.getParameter("billCategory");

			if (custType.equals("Customer")) {

				String billId = req.getParameter("billId");
				String customerName = req.getParameter("customerName");
				String cnic = req.getParameter("customerCnic");
				String discount = req.getParameter("discount");
				String amountPaid = req.getParameter("amountPaid");
				String dateStr = req.getParameter("date");
				
				validate=customerBill(customerName, cnic, discount, amountPaid);
				
				if(!validate.isEmpty()){
					req.setAttribute("validate", validate);
					req.getRequestDispatcher("Jsps/InventoryPages/Billing/CreateBill.jsp").forward(req,resp);
				}else{
					double subTotal = 0;
					double totalAmount = 0;
					double discountAmount = Double.parseDouble(discount);
					double paidAmount = Double.parseDouble(amountPaid);
					if (session.getAttribute("totalAmount") != null) {
						totalAmount = (Double) session.getAttribute("totalAmount");
					}

					subTotal = totalAmount - discountAmount;

					try {
						DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
						date = dateFormat.parse(dateStr);
					} catch (ParseException ex) {
						ex.printStackTrace();
					}

					billMain.setBillId(billId);
					billMain.setCustomerName(customerName);
					billMain.setCnicOrMobile(cnic);
					billMain.setSubTotal(subTotal);
					billMain.setTotalAmount(totalAmount);
					billMain.setDate(date);
					billMain.setDiscount(discountAmount);
					billMain.setAmountPaid(paidAmount);

					if (billItems == null) {
					} else {

						for (BillItemsEntity billItem : billItems) {
							BillItemsEntity itemsBill = new BillItemsEntity();
							itemsBill.setItemName(billItem.getItemName());
							itemsBill.setItemQuantity(billItem.getItemQuantity());
							itemsBill.setItemPrice(billItem.getItemPrice());
							itemsBill.setTotalItemPrice(billItem
									.getTotalItemPrice());

							secondBillItems.add(itemsBill);
						}
					}

					status = bill.insertBill(billMain, secondBillItems);
					generateBill(billMain.getBillId(), "Customer");

					session.removeAttribute("billItems");
					session.removeAttribute("totalAmount");
					req.getRequestDispatcher("Jsps/InventoryPages/Billing/CreateBill.jsp").forward(req, resp);
				}
				
				

			} else if (custType.equals("Borrower")) {
				String name = "";

				String billId = req.getParameter("billId");
				String cnic = req.getParameter("customerCnic");
				String discount = req.getParameter("discount");
				String amountPaid = req.getParameter("amountPaid");
				String dateStr = req.getParameter("date");
				
				validate=borrowerBill(cnic,discount,amountPaid);
				
				if(!validate.isEmpty()){
					req.setAttribute("validate", validate);
					req.getRequestDispatcher("Jsps/InventoryPages/Billing/CreateBill.jsp").forward(req,resp);
				}else{
					
					double subTotal = 0;
					double totalAmount = 0;
					double discountAmount = Double.parseDouble(discount);
					double paidAmount = Double.parseDouble(amountPaid);
					double dueAmount = 0;

					if (session.getAttribute("totalAmount") != null) {
						totalAmount = (Double) session.getAttribute("totalAmount");
					}

					subTotal = totalAmount - discountAmount;
					dueAmount = totalAmount - paidAmount;

					try {
						DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
						date = dateFormat.parse(dateStr);
					} catch (ParseException ex) {
						ex.printStackTrace();
					}

					name = borrower_Bill.checkBorrower(cnic);

					if (!name.equals("")) {
						borrowerBill.setBillId(billId);
						borrowerBill.setCnic(cnic);
						borrowerBill.setSubTotal(subTotal);
						borrowerBill.setTotalAmount(totalAmount);
						borrowerBill.setDiscount(discountAmount);
						borrowerBill.setAmountPaid(paidAmount);
						borrowerBill.setDueAmount(dueAmount);
						borrowerBill.setDate(date);

						if (billItems == null) {
						} else {

							for (BillItemsEntity billItem : billItems) {
								BillItemsEntity itemsBill = new BillItemsEntity();
								itemsBill.setItemName(billItem.getItemName());
								itemsBill.setItemQuantity(billItem
										.getItemQuantity());
								itemsBill.setItemPrice(billItem.getItemPrice());
								itemsBill.setTotalItemPrice(billItem
										.getTotalItemPrice());

								secondBillItems.add(itemsBill);
							}
						}

						status = borrower_Bill.insertBill(borrowerBill, billItems);
						generateBill(borrowerBill.getBillId(), "Borrower");

						// //////////////////////////////////////////////////////////
						// updating Amount of Borrower
						// /////////////////////////////////////////////////////////
						double amountRemains = 0;
						double lastAmount = 0;
						double newAmount = 0;
						amountRemains = borrower_Bill.getRemaingingAmount(cnic);
						lastAmount = amountRemains;
						newAmount = dueAmount;
						amountRemains += dueAmount;

						status = borrower_Bill.updateDebt(cnic, amountRemains);

						// //////////////////////////////////////////////////////////
						// Inserting Amount of Borrower being Borrowed by buying
						// products.
						// /////////////////////////////////////////////////////////

						String[] id = billId.split("-");
						int bill_Id = Integer.parseInt(id[1]);
						amountBorrowed.setCnic(cnic);
						amountBorrowed.setBillId(bill_Id);
						amountBorrowed.setLastAmount(lastAmount);
						amountBorrowed.setNewAmount(newAmount);
						amountBorrowed.setTotalAmount(amountRemains);
						amountBorrowed.setDate(date);

						status = borrower_Bill.insertAmountBorrowed(amountBorrowed);
						if(status>0){
							session.removeAttribute("billItems");
							session.removeAttribute("totalAmount");
							req.getRequestDispatcher("Jsps/InventoryPages/Billing/CreateBill.jsp").forward(req, resp);
						}
						
					} else {
						req.getRequestDispatcher("Jsps/InventoryPages/Borrower/AddBorrower.jsp").forward(req, resp);
					}
				}
				
				
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void generateBill(String billId, String type) {

		String path = "";

		switch (type) {
		case "Customer":
			path = "F:\\InternshipWork\\InventoryManagementAndSalesSystem\\WebContent\\jrxml\\Bill.jrxml";
			break;
		case "Borrower":
			path = "F:\\InternshipWork\\InventoryManagementAndSalesSystem\\WebContent\\jrxml\\BorrowerBill.jrxml";
			break;
		}

		try {
			Connection con = DatabaseConnection.getConnection();
			// load report location
			FileInputStream fileInputStream = new FileInputStream(path);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					fileInputStream);

			String[] id = billId.split("0");
			int bId = Integer.parseInt(id[3]);
			// set parameters
			Map map = new HashMap();
			map.put("billId", bId);
			map.put("fullBillId", billId);
			// compile report
			JasperReport jasperReport = (JasperReport) JasperCompileManager
					.compileReport(bufferedInputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, map, con);
			// view report to UI
			OutputStream output = new FileOutputStream(new File("F:\\InternshipWork\\InventoryManagementAndSalesSystem\\WebContent\\PdfBills\\"+ type + bId + ".pdf"));
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			JasperViewer jasperViewer = new JasperViewer(jasperPrint, true);
			jasperViewer.setVisible(true);
		} catch (FileNotFoundException | NumberFormatException | JRException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		// /////////////////////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////////////////
	}
	
	public Map<String,String> customerBill(String customerName, String cnic,String discount,String amountPaid){
		Map<String,String> message=new HashMap<>();
			if(customerName.equals("") || customerName.isEmpty()){
				message.put("errorCustomerName", "border-color: red;");
			}
			if(cnic.equals("") || cnic.isEmpty()){
				message.put("msg1", "border-color: red;");
			}
			if(discount.equals("") || discount.isEmpty()){
				message.put("msg2", "border-color: red;");
			}
			if(amountPaid.equals("") || amountPaid.isEmpty()){
				message.put("msg3", "border-color: red;");
			}
			
			
		return message;
	}
	
	public Map<String,String> borrowerBill( String cnic,String discount,String amountPaid){
		Map<String,String> message=new HashMap<>();
		
			if(cnic.equals("") || cnic.isEmpty()){
				message.put("msg1", "border-color: red;");
			}
			if(discount.equals("") || discount.isEmpty()){
				message.put("msg2", "border-color: red;");
			}
			if(amountPaid.equals("") || amountPaid.isEmpty()){
				message.put("msg3", "border-color: red;");
			}
			
			
		return message;
	}
}
