package cn.ecommerce.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ecommerce.connection.DBCon;
import cn.ecommerce.dao.UserDao;
import cn.ecommerce.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out= response.getWriter()){
			String email=request.getParameter("login-email");
			String password=request.getParameter("login-password");
			
			try {
				UserDao udao= new UserDao(DBCon.getConnection());
				User user=udao.userLogin(email, password);
				
				if(user!=null) {
					request.getSession().setAttribute("auth", user);
					response.sendRedirect("index.jsp");
				}
				else {
					out.print("user login failed");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
