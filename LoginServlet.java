import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.net.URLEncoder;

public class LoginServlet extends HttpServlet{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		res.setContentType("text/html");
		
		String name = req.getParameter("name");
		String pass = req.getParameter("pass");
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Oracle123");
			
			Statement s = c.createStatement();
			String s1 = "select * from emp101 where name='"+name+"' and pass='"+pass+"'";
			ResultSet rs = s.executeQuery(s1);
			
			
			if(rs.next()){
				// IMPORTANT: create session and mark user as logged in
				// (AuthFilter checks this attribute on every request)
				HttpSession session = req.getSession(true);
				session.setAttribute("user", name);

				// username/password ko 1 hour ke liye cookie mein save karo
				// taaki login page dobara khulne par fields autofill ho jayein
				// NOTE: raw value mein space/special char hone par purana Cookie API
				// IllegalArgumentException de sakta hai, isliye pehle URL-encode kar rahe hain.
				// URLEncoder space ko "+" banata hai jise JS ka decodeURIComponent samajh nahi
				// payega, isliye "+" ko manually "%20" mein badal rahe hain taaki client-side
				// decodeURIComponent() sahi se decode kar sake.
				String encName = URLEncoder.encode(name, "UTF-8").replace("+", "%20");
				String encPass = URLEncoder.encode(pass, "UTF-8").replace("+", "%20");

				Cookie c1 = new Cookie("uname", encName);
				Cookie c2 = new Cookie("upass", encPass);
				c1.setMaxAge(60*60);   // 1 hour
				c2.setMaxAge(60*60);   // 1 hour
				// context-path pe depend karne ke bajaye "/" use kiya, taaki deployment
				// context badalne (ROOT ya /x1 etc.) par bhi cookie path mismatch na ho
				c1.setPath("/");
				c2.setPath("/");
				res.addCookie(c1);
				res.addCookie(c2);

				// use redirect (not include) so the browser URL updates
				// and AuthFilter lets subsequent requests through
				res.sendRedirect(req.getContextPath()+"/home.html");
			}
			else{
				res.sendRedirect(req.getContextPath()+"/register.html?notfound=1");
			}
		}catch(Exception e){
			PrintWriter out = res.getWriter();
			out.println("<html><body>");
			out.println(e);
			out.println("</body></html>");
		}
	}
}
