import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class UpdateStudent extends HttpServlet{
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
			// Column names confirmed from DESC STUDENTMARKS: STD_ID, STUDENT_NAME,
			// HINDI, ENGLISH, MATHS, SCIENCE, SOCIALSCIENCE, COMPUTER, SANSKRIT
			String s1 = "update studentmarks set student_name='"+studentName+"', hindi="+hindi+", english="+english+", maths="+maths+", science="+science+", socialscience="+socialScience+", computer="+computer+", sanskrit="+sanskrit+" where std_id="+studentId;
			//out.println(s1);
			int i = s.executeUpdate(s1);
			if(i>0)
				out.println("Record Updated Successfully<br>");
			else
				out.println("No Such student exist<br>");
		}catch(Exception e){
			out.println(e);
		}
		out.println("<a href='home.html'>Back to Home</a>");
		out.println("</body></html>");
	}
}
