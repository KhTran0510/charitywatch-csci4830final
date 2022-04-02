
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;

//////////
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
//////////

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SignUp() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String first = request.getParameter("first");
      String last = request.getParameter("last");
      String email = request.getParameter("email");
      String address = request.getParameter("address");
      String password = request.getParameter("password");

      Connection connection = null;
      //String insertSql_foundation =
      String insertSql = " INSERT INTO foundation (first, last, email, address, password) values (?, ?, ?, ?, ?)";
      //String insertSql_donor = " INSERT INTO donor (id, Makes, Models, VIN, Year, Price, Phone, Email) values (default, ?, ?, ?, ?, ?, ?, ?)";

      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, first);
         preparedStmt.setString(2, last);
         preparedStmt.setString(3, email);
         preparedStmt.setString(4, address);
         preparedStmt.setString(5, password);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      
      request.setAttribute("first", first);
      request.setAttribute("last", last);
      
      request.getRequestDispatcher("/WEB-INF/test.jsp").forward(request, response);
   
      
      /*
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Sign Up members";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //
            
			"  <form action=\"\" method=\"POST\"> " + "\n" + //
			"<input type=\"radio\" id=\"donor\" name=\"acctype\" value=\"Donor\" checked>\r\n"
			+ "		<label for=\"donor\">Donor</label><br>\r\n"
			+ "		<input type=\"radio\" id=\"foundation\" name=\"acctype\" value=\"Foundation\">\r\n"
			+ " 		<label for=\"foundation\">Foundation</label><br> "+
			

            "  <li><b>First</b>: " + first + "\n" + //
            "  <li><b>Last</b>: " + last + "\n" + //
            "  <li><b>Email</b>: " + email + "\n" + //
            "  <li><b>Address</b>: " + address + "\n" + //
            "  <li><b>Pass</b>: " + password + "\n" + //
            "</ul>\n");

      out.println("<a href=./search_data.html>Search Data</a> <br>");
      out.println("<input type=\"submit\" value=\"Submit\" /></form>");
      out.println("</body></html>");*/
      
      
      
      
      /*
      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Sign Up members";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>First</b>: " + first + "\n" + //
            "  <li><b>Last</b>: " + last + "\n" + //
            "  <li><b>Email</b>: " + email + "\n" + //
            "  <li><b>Address</b>: " + address + "\n" + //
            "  <li><b>Pass</b>: " + password + "\n" + //
            "</ul>\n");

      out.println("<a href=./search_data.html>Search Data</a> <br>");
      out.println("</body></html>");
      out.println("<a href=./sign_up.html>Sign Up</a> <br>");
      out.println("</body></html>");*/
      
   }
   

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
   

}
