import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
// import javax.servlet.annotation.WebServlet;

// @WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		res.setContentType("text/html");
		
		PrintWriter out = res.getWriter();
		out.println("<html><body>");
		
		String name = req.getParameter("newname");
		String pass = req.getParameter("newpass");
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Oracle123");
			
			Statement s = c.createStatement();
			String s1 = "insert into emp101 values('"+name+"','"+pass+"')";
			int x = s.executeUpdate(s1);

			if(x > 0){
				out.println("Register Successfully");
				out.println("<a href='login.html'>Back to Login</a>");
			}
			else
				out.println("<h1 style ='margin:50px;'>Register not Done</h1><br>");
			
		}catch(Exception e){ out.println(e);}
		
		out.println("</body></html>");
	}
}