import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class AddStudent extends HttpServlet{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		res.setContentType("text/html");
		
		ServletContext ctx = getServletContext();
		
		PrintWriter out = res.getWriter();
		
		out.println("<html><body>");
		
		//getting context params
		String driver = ctx.getInitParameter("driver");
		String url = ctx.getInitParameter("url");
		String user = ctx.getInitParameter("user");
		String pass = ctx.getInitParameter("pass");
		
		
		String studentId = req.getParameter("studentId");
		String studentName = req.getParameter("studentName");
		String english = req.getParameter("english");
		String hindi = req.getParameter("hindi");
		String maths = req.getParameter("maths");
		String science = req.getParameter("science");
		String socialScience = req.getParameter("socialScience");
		String computer = req.getParameter("computer");
		String sanskrit = req.getParameter("sanskrit");
		
		try{
			Class.forName(driver);
			Connection c = DriverManager.getConnection(url,user,pass);
			
			Statement s = c.createStatement();
			String s1 = "INSERT INTO STUDENTMARKS VALUES("+ studentId + ","+"'" + studentName + "'" + ","+ hindi + ","+ english+ ","+ maths + ","+ science + ","+ socialScience + ","+ computer + ","+ sanskrit + ")";
			//out.println(s1);
			ResultSet rs = s.executeQuery(s1);
			if(rs.next())
				out.println("Record added Successfully<br>");
			else
				out.println("Record not added<br>");
		}catch(Exception e){
			out.println(e);
		}
		out.println("<a href='home.html'>Back to Home</a>");
		out.println("</body></html>");
	}
}