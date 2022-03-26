
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertHa")
public class InsertHa extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertHa() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String make = request.getParameter("Makes");
      String model = request.getParameter("Models");
      String vin = request.getParameter("VIN");
      String year = request.getParameter("Year");
      String price = request.getParameter("Price");
      String phone = request.getParameter("Phone");
      String email = request.getParameter("Email");

      Connection connection = null;
      String insertSql = " INSERT INTO Cars (id, Makes, Models, VIN, Year, Price, Phone, Email) values (default, ?, ?, ?, ?, ?, ?, ?)";

      try {
         DBConnectionHa.getDBConnection(getServletContext());
         connection = DBConnectionHa.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, make);
         preparedStmt.setString(2, model);
         preparedStmt.setString(3, vin);
         preparedStmt.setString(4, year);
         preparedStmt.setString(5, price);
         preparedStmt.setString(6, phone);
         preparedStmt.setString(7, email);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Make</b>: " + make + "\n" + //
            "  <li><b>Model</b>: " + model + "\n" + //
            "  <li><b>VIN</b>: " + vin + "\n" + //
            "  <li><b>Year</b>: " + year + "\n" + //
            "  <li><b>Price</b>: " + price + "\n" + //
            "  <li><b>Phone</b>: " + phone + "\n" + //
            "  <li><b>Email</b>: " + email + "\n" + //

            "</ul>\n");

      out.println("<a href=./search_ha.html>Search Data</a> <br>");
      out.println("</body></html>");
      out.println("<a href=./insert_ha.html>Insert Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
