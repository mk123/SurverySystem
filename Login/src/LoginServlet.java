

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import java.util.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String dbClassName = "com.mysql.jdbc.Driver";
	////here UserData is Database that contains table named users 
	////schema of users    users{ name varchar(15), password varchar(20) }
	private static final String CONNECTION ="jdbc:mysql://localhost/UserData";
	public Connection connection=null;
    public String UserName="";
    public String Password="";
    public PrintWriter pw=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		UserName=request.getParameter("UserName");
		Password=request.getParameter("Password");
		pw=response.getWriter();
		RequestDispatcher rd=null;   ////used to forward data to webpages and servlets.
		if(validate(UserName,Password))
		{
			HttpSession session=request.getSession();
			session.setAttribute("UserName", UserName);
			session.setAttribute("path", ("/home/manjeet/Desktop/Data/"+UserName));
			rd=request.getRequestDispatcher("Welcome.html");
			rd.forward(request, response);
			
		}
		else
		{
			rd=request.getRequestDispatcher("Index.html");
			pw.println("Invalid UserName/Password");
			rd.include(request,response);
		}
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public boolean validate(String name,String passwd)
	{
		if(name.equals("") || passwd.equals(""))
		{
			return false;
		}
		
		try
		{
			Class.forName(dbClassName);
			Properties p=new Properties();
			p.put("user","root");
			p.put("password","a");
			connection=(Connection) DriverManager.getConnection(CONNECTION, p);
			PreparedStatement ps=connection.prepareStatement("Select * from users where UserName=? and UserPassword=?");
			ps.setString(1,UserName);
			ps.setString(2,Password);

			ResultSet rs=ps.executeQuery();
			if(rs.first())
			  return true;
			else
			  return false;
			
		}
		catch(Exception e)
		{
			pw.println("Validation of UserName and Password could not be done");
			pw.println(e);
		}
		return true;
	}

}
