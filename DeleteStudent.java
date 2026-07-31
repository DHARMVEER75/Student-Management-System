import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class DeleteStudent extends HttpServlet{
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
		
		
		try{
			Class.forName(driver);
			Connection c = DriverManager.getConnection(url,user,pass);
			
			Statement s = c.createStatement();
			String s1 = "delete from studentmarks where std_id="+studentId;
			//out.println(s1);
			int i = s.executeUpdate(s1);
			if(i>0)
				out.println("Record Deleted Successfully<br>");
			else
				out.println("Record not Deleted<br>");
		}catch(Exception e){
			out.println(e);
		}
		out.println("<a href='home.html'>Back to Home</a>");
		out.println("</body></html>");
	}
}