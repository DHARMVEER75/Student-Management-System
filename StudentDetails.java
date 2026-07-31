import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class StudentDetails extends HttpServlet{
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
			String s1 = "select * from studentmarks where std_id="+studentId;
			//out.println(s1);
			ResultSet rs = s.executeQuery(s1);
			if(rs.next()){
				float hindi = rs.getFloat(3);
				float english = rs.getFloat(4);
				float maths = rs.getFloat(5);
				float science = rs.getFloat(6);
				float socialScience = rs.getFloat(7);
				float computer = rs.getFloat(8);
				float sanskrit = rs.getFloat(9);
				float percentage =((hindi+english+maths+science+socialScience+computer+sanskrit)/700)*100;
				out.println("<h1>Student Detail</h1><br>");
				out.println("Student Id: "+rs.getString(1)+"<br>");
				out.println("Student Name: "+rs.getString(2)+"<br>");
				out.println("<h2>Student Marks</h2><br>");
				out.println("Hindi : "+hindi+"<br>");
				out.println("English: "+english+"<br>");
				out.println("Maths: "+maths+"<br>");
				out.println("Science: "+science+"<br>");
				out.println("Social Science: "+socialScience+"<br>");
				out.println("Computer: "+computer+"<br>");
				out.println("Sanskrit: "+sanskrit+"<br>");
				out.println("<h3>Total Percentage : "+percentage+"% <h3><br>");
                                	out.println("<a href='excelDownload?studentId="+studentId+"' style='display:inline-block;background:#198754;color:white;padding:10px 20px;border-radius:5px;text-decoration:none;font-size:16px;margin-top:10px;'>Download Excel</a><br><br>");
			

			}
			else
				out.println("No Such student exist<br>");
		}catch(Exception e){
			out.println(e);
		}
		out.println("<a href='home.html'>Back to Home</a>");
		out.println("</body></html>");
	}
}