import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class ViewAllStudents extends HttpServlet{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		res.setContentType("text/html");
		
		ServletContext ctx = getServletContext();
		
		PrintWriter out = res.getWriter();
		
		out.println("<html><head><style>");
		out.println("*{font-family:Arial, Helvetica, sans-serif;} table{border-collapse:collapse;width:95%;margin:20px auto;} th,td{border:1px solid #999;padding:8px;text-align:center;} th{background:#0d6efd;color:white;} h1{text-align:center;color:#0d6efd;margin-top:20px;} a{display:block;text-align:center;margin:20px;}");
		out.println("</style></head><body>");
		
		out.println("<h1>All Student Records</h1>");
		
		//getting context params
		String driver = ctx.getInitParameter("driver");
		String url = ctx.getInitParameter("url");
		String user = ctx.getInitParameter("user");
		String pass = ctx.getInitParameter("pass");
		
		try{
			Class.forName(driver);
			Connection c = DriverManager.getConnection(url,user,pass);
			
			Statement s = c.createStatement();
			String s1 = "select * from studentmarks";
			ResultSet rs = s.executeQuery(s1);
			
			out.println("<table>");
			out.println("<tr><th>Student ID</th><th>Name</th><th>Hindi</th><th>English</th><th>Maths</th><th>Science</th><th>Social Science</th><th>Computer</th><th>Sanskrit</th><th>Percentage</th></tr>");
			
			boolean anyRecord = false;
			
			while(rs.next()){
				anyRecord = true;
				float hindi = rs.getFloat(3);
				float english = rs.getFloat(4);
				float maths = rs.getFloat(5);
				float science = rs.getFloat(6);
				float socialScience = rs.getFloat(7);
				float computer = rs.getFloat(8);
				float sanskrit = rs.getFloat(9);
				float percentage =((hindi+english+maths+science+socialScience+computer+sanskrit)/700)*100;
				
				out.println("<tr>");
				out.println("<td>"+rs.getString(1)+"</td>");
				out.println("<td>"+rs.getString(2)+"</td>");
				out.println("<td>"+hindi+"</td>");
				out.println("<td>"+english+"</td>");
				out.println("<td>"+maths+"</td>");
				out.println("<td>"+science+"</td>");
				out.println("<td>"+socialScience+"</td>");
				out.println("<td>"+computer+"</td>");
				out.println("<td>"+sanskrit+"</td>");
				out.println("<td>"+percentage+"%</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
			
			if(!anyRecord)
				out.println("<h3 style='text-align:center;'>No records found</h3>");
			else
				out.println("<div style='text-align:center;'><a href='excelDownload' style='display:inline-block;background:#198754;color:white;padding:10px 20px;border-radius:5px;text-decoration:none;font-size:16px;'>Download Excel</a></div>");
			
		}catch(Exception e){
			out.println(e);
		}
		out.println("<a href='home.html'>Back to Home</a>");
		out.println("</body></html>");
	}
}
