package app.IMAS.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.IMAS.DatabaseDaos.ProductDao;
import app.IMAS.DatabaseDaosImpl.ProductDaoImpl;

import com.google.gson.Gson;

public class PopulateBillItemController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private List<String> itemNames=new ArrayList<>();
	private final ProductDao product = new ProductDaoImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String catName=req.getParameter("catName");
		itemNames = product.getItemNames(catName);
		
		String json = null;
		json = new Gson().toJson(itemNames);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
