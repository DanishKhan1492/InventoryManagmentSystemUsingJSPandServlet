package app.IMAS.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.IMAS.DatabaseDaos.LogInDao;
import app.IMAS.DatabaseDaosImpl.LogInDaoImpl;

public class ChangePasswordController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private LogInDao getPassword=new LogInDaoImpl();
	private Map<String,String> validatePass=new HashMap<>();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String url=req.getServletPath();
		
		// Forwarding Access to Password Page 
		if(url.equals("/changePasswordPage")){
			req.getRequestDispatcher("Jsps/AuthenticationPages/ChangePassword.jsp").include(req, resp);
			
		//// Checking Passwords and then Changing Password
		}else if(url.equals("/changePassword")){
			
			int status=0;
			String oldPassword=req.getParameter("oldPassword");
			String newPassword=req.getParameter("newPassword");
			String confirmPassword=req.getParameter("confirmPassword");
			
			validatePass=validate(oldPassword, newPassword, confirmPassword);
			
			if(!validatePass.isEmpty()){
				req.setAttribute("validate", validatePass);
				req.getRequestDispatcher("Jsps/AuthenticationPages/ChangePassword.jsp").include(req, resp);
			}else{
				String dbPassword=getPassword.getPassword();
				
				if(dbPassword.equals(oldPassword)){
					if(newPassword.equals(confirmPassword)){
						status=getPassword.changePassword(newPassword);
						if(status>0){
							req.setAttribute("msg", "Updated Successfully");
							req.getRequestDispatcher("Jsps/AuthenticationPages/Login.jsp").include(req, resp);
						}else{
							req.setAttribute("msg", "Something Went Wrong Please Try Again");
							req.getRequestDispatcher("Jsps/AuthenticationPages/ChangePassword.jsp").include(req, resp);
						}
					}else{
						req.setAttribute("msg", "Both Passwords are not Same");
						req.getRequestDispatcher("Jsps/AuthenticationPages/ChangePassword.jsp").include(req, resp);
					}
				}else{
					req.setAttribute("msg", "Old Password is Wrong");
					req.getRequestDispatcher("Authentication/ChangePassword.jsp").include(req, resp);
				}
			}
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}

	
	public Map<String,String> validate(String oldPass,String newPass, String confirmPass){
		Map<String,String> message=new HashMap<>();
		
		if(oldPass.equals("") || oldPass.isEmpty() || oldPass== null){
			message.put("errorOldPassword", "Enter User Name");
		}
		if(newPass.equals("") || newPass.isEmpty() || newPass== null){
			message.put("errorNewPassword", "Enter password");
		}
		if(confirmPass.equals("") || confirmPass.isEmpty() || confirmPass== null){
			message.put("errorConfirmPassword", "Enter password");
		}
		
		return message;
	}
}
