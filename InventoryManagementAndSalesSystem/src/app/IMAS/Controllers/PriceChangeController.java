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

import app.IMAS.DatabaseDaos.PriceChangeDao;
import app.IMAS.DatabaseDaosImpl.PriceChangeDaoImpl;
import app.IMAS.Enitities.PriceChangeEntity;

public class PriceChangeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PriceChangeDao priceChange=new PriceChangeDaoImpl();
	private List<PriceChangeEntity> getPriceChaneEntities=new ArrayList<>();
	private Map<String,String> validate=new HashMap<>();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		HttpSession session=req.getSession();
		
		if(session.getAttribute("userName") == null){
			resp.sendRedirect("/InventoryManagementAndSalesSystem/loginPage");
			
		}else if(path.equals("/itemPricePage")){
			getPriceChaneEntities=priceChange.getAllPrice();
			req.setAttribute("prices", getPriceChaneEntities);
			req.getRequestDispatcher("Jsps/InventoryPages/Stock/ShowPrices.jsp").include(req, resp);
		}else if(path.equals("/searchItemPrice")){
			String itemName=req.getParameter("searchItemPrice");
			validateSearch(itemName);
			
			if(!validate.isEmpty()){
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/InventoryPages/Stock/SearchItem.jsp").include(req, resp);
			}else{
				getPriceChaneEntities=priceChange.getSpecificPrice(itemName);
				req.setAttribute("prices",getPriceChaneEntities);
				req.getRequestDispatcher("Jsps/InventoryPages/Stock/ShowPrices.jsp").include(req, resp);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
	
	public void validateSearch(String itemName){
		if(itemName.equals("")|| itemName.isEmpty() ){
			validate.put("msg", "Please Enter Item Name or Any Letter");
		}
	}

}
