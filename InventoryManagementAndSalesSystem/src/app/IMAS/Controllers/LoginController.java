package app.IMAS.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.IMAS.DatabaseDaos.LogInDao;
import app.IMAS.DatabaseDaosImpl.LogInDaoImpl;
import app.IMAS.Enitities.LoginEntity;

public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private LoginEntity login=new LoginEntity();
	private LogInDao getAdmin=new LogInDaoImpl();
	private Map<String,String> validate=new HashMap<>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String servletPath = req.getServletPath();
		
		// Forwarding Access to Login Page 
	    if("/loginPage".equals(servletPath)){
	    	req.getRequestDispatcher("Jsps/AuthenticationPages/Login.jsp").include(req, resp);
	    	
	    	//// Authenticating User and If Authenticated Forwarding Request to Main Form of The Application 
	    }else if("/login".equals(servletPath)){
	    	
	    	String userName=req.getParameter("userName");
			String password=req.getParameter("password");
			
			//Doing Validation Here
			validate=validateUser(userName, password);
			
			if(!validate.isEmpty()){
				req.setAttribute("validate", validate);
				req.getRequestDispatcher("Jsps/AuthenticationPages/Login.jsp").include(req, resp);
			}else{

				login=getAdmin.getAdmin();
				
				String dbUserName=login.getUserName();
				String dbPassword=login.getPassword();
				
				if(userName.equals(dbUserName) && password.equals(dbPassword)){
					HttpSession session= req.getSession();
					session.setAttribute("userName", userName);
					resp.sendRedirect("/InventoryManagementAndSalesSystem/mainForm");
				}else{
					req.setAttribute("msg", "User Name or Password Do not Match ");
					req.getRequestDispatcher("Jsps/AuthenticationPages/Login.jsp").include(req, resp);
				}
			}
	    }else if("/logout".equals(servletPath)){
	    	HttpSession session=req.getSession();
	    	session.invalidate();
	    	req.getRequestDispatcher("Jsps/AuthenticationPages/Login.jsp").forward(req, resp);
	    }
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
	

	public Map<String,String> validateUser(String name,String password){
		Map<String,String> message=new HashMap<>();
			if(name.equals("") || name.isEmpty() || name== null){
				message.put("errorUserName", "Enter User Name");
			}
			if(password.equals("") || password.isEmpty() || password== null){
				message.put("errorPassword", "Enter password");
			}
			
			
			if(message.containsKey("errorUserName") && message.containsKey("errorPassword")){
			}else if(message.containsKey("errorUserName") && !message.containsKey("errorPassword")){
				message.put("password", password);
			}else if(!message.containsKey("errorUserName") && message.containsKey("errorPassword")){
				message.put("userName", name);
			}
			
		return message;
	}
	
}
