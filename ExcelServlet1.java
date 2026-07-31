import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class ExcelServlet1 extends HttpServlet
{
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        ServletContext ctx = getServletContext();

        // getting context params (same as other servlets)
        String driver = ctx.getInitParameter("driver");
        String url = ctx.getInitParameter("url");
        String user = ctx.getInitParameter("user");
        String pass = ctx.getInitParameter("pass");

        // agar studentId aaya hai to sirf usi ek student ka data,
        // warna sabhi students ka data
        String studentId = req.getParameter("studentId");

        try
        {
            Class.forName(driver);
            Connection c = DriverManager.getConnection(url, user, pass);

            Statement stmt = c.createStatement();

            String s1;
            String fileName;

            if (studentId != null && !studentId.trim().equals(""))
            {
                s1 = "select * from studentmarks where std_id=" + studentId;
                fileName = "Student_" + studentId + ".xls";
            }
            else
            {
                s1 = "select * from studentmarks";
                fileName = "StudentRecords.xls";
            }

            ResultSet rs = stmt.executeQuery(s1);
            ResultSetMetaData rd = rs.getMetaData();

            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            PrintWriter out = res.getWriter();

            int count = rd.getColumnCount();

            // header row - column names
            for (int i = 1; i <= count; i++)
            {
                out.print(rd.getColumnName(i) + "\t");
            }

            if (studentId != null && !studentId.trim().equals(""))
                out.print("Percentage");

            out.println();

            // data rows
            while (rs.next())
            {
                for (int i = 1; i <= count; i++)
                {
                    out.print(rs.getString(i) + "\t");
                }

                // single student wale case mein percentage bhi add kar do
                if (studentId != null && !studentId.trim().equals(""))
                {
                    float hindi = rs.getFloat(3);
                    float english = rs.getFloat(4);
                    float maths = rs.getFloat(5);
                    float science = rs.getFloat(6);
                    float socialScience = rs.getFloat(7);
                    float computer = rs.getFloat(8);
                    float sanskrit = rs.getFloat(9);
                    float percentage = ((hindi + english + maths + science + socialScience + computer + sanskrit) / 700) * 100;
                    out.print(percentage);
                }

                out.println();
            }

            out.close();
        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }
    }
}
