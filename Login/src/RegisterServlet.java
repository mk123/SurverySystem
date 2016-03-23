

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import sun.security.util.Resources_de;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String dbClassName = "com.mysql.jdbc.Driver";
	private static final String CONNECTION ="jdbc:mysql://localhost/UserData";  //UserData is name of database
	public String tableName="users";
    public String Name=null;
    public String Email=null;
    public String UserName=null;
    public String Password=null;
    public String ConfirmPassword=null;
    public PrintWriter pw=null;
    public Connection connection=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		pw=response.getWriter();
		Name=request.getParameter("Name");
		Email=request.getParameter("Email");
		UserName=request.getParameter("UserName");
		Password=request.getParameter("Password");
		ConfirmPassword=request.getParameter("ConfirmPassword");
		HttpSession session=request.getSession();
		session.setAttribute("UserName", UserName);
		RequestDispatcher rd=null;
		if(insertInDatabase())
		{
			rd=request.getRequestDispatcher("Welcome.html");
			rd.forward(request, response);
			
		}
		else
		{
			rd=request.getRequestDispatcher("signUp.html");
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
	public boolean insertInDatabase()
	{
		if(Password.equals("") || UserName.equals(""))
		{
			pw.println(" UserName/Password can't be null");
			return false;
		}
		if(!Password.equals(ConfirmPassword))
		{
			pw.println("Passwords do not match");
			return false;
		}
		
		try
		{
			Class.forName(dbClassName);
			Properties p=new Properties();
			p.put("user","root");
			p.put("password","a");
			connection=(Connection) DriverManager.getConnection(CONNECTION, p);
			PreparedStatement ps=connection.prepareStatement("Select * from users where UserName=?");
			ps.setString(1,UserName);
			ResultSet rs=ps.executeQuery();
			if(rs.first())
			{
				pw.println("UserName already exist");
				return false;
			}
			ps=connection.prepareStatement("insert into users values (?,?,?,?)");
			ps.setString(1,UserName);
			ps.setString(2,Password);
			ps.setString(3, Name);
			ps.setString(4, Email);
			int result=ps.executeUpdate();
			if(result>0)
			{
				pw.println(result);
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(Exception e)
		{
			pw.println("Validation of UserName and Password could not be done");
			pw.println(e);
		}
		return false;
	}

}
