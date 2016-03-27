
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
/**
 * Servlet implementation class generateQR_code
 */
@WebServlet("/generateQR_code")
public class generateQR_code extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public generateQR_code() {
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
		PrintWriter pw = response.getWriter();
		HttpSession session=request.getSession();
		String path = (String) session.getAttribute("path");
		String qrCodeData=(String)session.getAttribute("UserName")+" "
				+(int) session.getAttribute("SurveyNumber")+" "+path;
		ByteArrayOutputStream out = 
		QRCode.from(qrCodeData).to(ImageType.PNG).withSize(500,500).stream();
		
		try {
			FileOutputStream fout = new FileOutputStream(new File(path+"/QR_code.png"));

			fout.write(out.toByteArray());

			fout.flush();
			fout.close();
			RequestDispatcher rd=request.getRequestDispatcher("QR-Code.jsp");
			rd.forward(request,response);

		} catch (Exception e) {
			// Do Logging
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
