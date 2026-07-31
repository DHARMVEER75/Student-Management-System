import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class Logout extends HttpServlet
{
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        HttpSession s = req.getSession(false);

        if(s != null)
        {
            s.invalidate();
        }

        // logout ke time uname/upass autofill cookies bhi clear kar do
        Cookie c1 = new Cookie("uname", "");
        Cookie c2 = new Cookie("upass", "");
        c1.setMaxAge(0);
        c2.setMaxAge(0);
        c1.setPath(req.getContextPath()+"/");
        c2.setPath(req.getContextPath()+"/");
        res.addCookie(c1);
        res.addCookie(c2);

        // session destroy hone ke baad seedha login page par bhej do
        res.sendRedirect(req.getContextPath()+"/login.html");
    }
}
