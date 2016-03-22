import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.util.*;
public class loginServlet_2 extends HttpServlet
{
private static final String dbClassName = "com.mysql.jdbc.Driver";
////here UserData is Database that contains table named users 
////schema of users    users{ name varchar(15), password varchar(20) }
private static final String CONNECTION ="jdbc:mysql://localhost/UserData";

PrintWriter pw=null;
String password=null;
String name=null;
String errorMsg=null;
Connection connection;
public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
{
 name=request.getParameter("name");
 password=request.getParameter("password");
 pw=response.getWriter();
 try
 {
	Class.forName(dbClassName);
    // Properties for user and password.
    	Properties p = new Properties();
    	p.put("user","root");		/// user and password for mysql database
    	p.put("password","a");

    // Now try to connect
	connection = DriverManager.getConnection(CONNECTION,p);
 }
 catch(Exception e)
 {
   pw.println(e);
 }
 if(request.getParameter("signUp") != null )
 {
  register();
 }
 else
 {
 signIn();
 }
}
 public void signIn()
 {
    try{
  PreparedStatement ps=connection.prepareStatement("Select * from users where UserName=? and UserPassword=?");
  ps.setString(1,name);
  ps.setString(2,password);

  ResultSet rs=ps.executeQuery();
  if(rs.first())
  pw.println("Welcome to website");
  else
  pw.println("Invalid UserName/Password");
  }catch(Exception e)
  {
   pw.println(e);
  }
 }
 public void register()
 {
  if(name.equals("") || password.equals(""))
  {
   pw.println("Invalid UserName/Password");
   return;
  }
  
  try{
  PreparedStatement ps=connection.prepareStatement("Select * from users where Username=?");
  ps.setString(1,name);
  ResultSet rs=ps.executeQuery();  ///Check if UserName Already Exists;
  if(rs.first())
  {
   pw.println("UserName already exists");
   return;
   }
  }catch(Exception e)
  {
   pw.println(e);
   return;
  }
   try{
    PreparedStatement ps=connection.prepareStatement("insert into users values (?,?)");
	ps.setString(1,name);
	ps.setString(2,password);

    int rs=ps.executeUpdate();
    pw.println("Succesfully registered");
   }
   catch(Exception e)
   {
    pw.println(e);
   }
  
 }
 
}
