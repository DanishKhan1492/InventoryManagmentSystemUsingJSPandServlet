package app.IMAS.Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.IMAS.ExtraClasses.GenerateBillId;

import com.google.gson.Gson;

public class PopulateBillIdController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String tableName=req.getParameter("tableName");
		String billId=GenerateBillId.Generate_Bill_Id(tableName);
		
		String json = null;
		json = new Gson().toJson(billId);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	

}
