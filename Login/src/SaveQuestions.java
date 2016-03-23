

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class SaveQuestions
 */
@WebServlet("/SaveQuestions")
public class SaveQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveQuestions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		HttpSession session=request.getSession(false);
		session.setAttribute("QuestionNumber", (int)session.getAttribute("QuestionNumber")+1);
		if(request.getParameter("Add")!=null)
		{
		String userName=(String) session.getAttribute("UserName");
		String question=request.getParameter("question");
		String option_1=request.getParameter("option_1");
		String option_2=request.getParameter("option_2");
		String option_3=request.getParameter("option_3");
		String option_4=request.getParameter("option_4");
		String option_5=request.getParameter("option_5");
		/*
		 * This code is not working.
		PrintWriter output=new PrintWriter(new File("WebContent/Output.out"));
		output.println(userName);
		*/
		response.sendRedirect("Questions.jsp");
		}
		else
		{
			pw.println("Thank u <b>"+request.getSession().getAttribute("UserName")+"</b> your visit to the site.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
