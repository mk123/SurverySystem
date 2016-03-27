
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.*;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("UserName");
		String question = request.getParameter("question");
		//// Remove any new line from question
		question = question.replaceAll("\r\n", " ");
		String option_1 = request.getParameter("option_1");
		String option_2 = request.getParameter("option_2");
		String option_3 = request.getParameter("option_3");
		String option_4 = request.getParameter("option_4");
		String option_5 = request.getParameter("option_5");
		int totalNumberOfOptions = 0;
		session.setAttribute("QuestionNumber", (int) session.getAttribute("QuestionNumber") + 1);
		String path = (String) session.getAttribute("path");
		/// The following block counts the number of options entered by User.
		{
			if (option_1 != "")
				totalNumberOfOptions++;
			if (option_2 != "")
				totalNumberOfOptions++;
			if (option_3 != "")
				totalNumberOfOptions++;
			if (option_4 != "")
				totalNumberOfOptions++;
			if (option_5 != "")
				totalNumberOfOptions++;
		}
		if (request.getParameter("Add") != null || request.getParameter("Finish") != null) {

			// PrintWriter output=new PrintWriter(new
			// File("/home/manjeet/Desktop/Data/Output.out"));
			File tempFilepath = new File(path);
			if (!tempFilepath.exists()) {
				tempFilepath.mkdirs();
			}
			FileOutputStream outputStream = new FileOutputStream((tempFilepath + "/temp.txt"), true);
			PrintWriter streamWriter = new PrintWriter(outputStream);
			streamWriter.println(question);
			streamWriter.println(totalNumberOfOptions);
			streamWriter.println(option_1);
			streamWriter.println(option_2);
			if (!option_3.equals("")) {
				streamWriter.println(option_3);
			}
			if (!option_4.equals("")) {
				streamWriter.println(option_4);
			}
			if (!option_5.equals("")) {
				streamWriter.println(option_5);
			}
			streamWriter.flush();
			streamWriter.close();
			if (request.getParameter("Add") != null)
				response.sendRedirect("Questions.jsp");
		}

		if (request.getParameter("Finish") != null) {
			int numberOfPreviousFiles = new File(path).list().length; /// including
																		/// tempFile
			session.setAttribute("SurveyNumber", numberOfPreviousFiles);
			String JsonFileName = "File" + numberOfPreviousFiles + ".json";
			File jsonFile = new File(path, JsonFileName);
			PrintWriter output = new PrintWriter(jsonFile);
			Scanner inputFile = new Scanner(new File(path, "temp.txt"));
			/// the following loop will be used of converting temp.txt to json
			/// file.
			JSONObject j_obj = new JSONObject();
			JSONArray questions = new JSONArray();
			while (inputFile.hasNextLine()) {

				JSONObject qustn = new JSONObject();
				String Statement = inputFile.nextLine();
				qustn.put("statement", Statement);
				JSONArray options = new JSONArray();
				totalNumberOfOptions = Integer.parseInt(inputFile.nextLine());
				for (int option = 0; option < totalNumberOfOptions; option++) {
					options.add(inputFile.nextLine());
				}
				qustn.put("options", options);
				questions.add(qustn);
			}

			j_obj.put("questions", questions);
			output.println(j_obj.toJSONString());
			output.flush();
			output.close();
			inputFile.close();
			Boolean deleted = new File(path, "temp.txt").delete();
			request.getRequestDispatcher("generateQR_code").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
