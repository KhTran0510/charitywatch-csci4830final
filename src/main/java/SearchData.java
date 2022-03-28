import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchData")
public class SearchData extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchData() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String keywordmake = request.getParameter("keywordmake");
	  String keywordmodel = request.getParameter("keywordmodel");
      String keywordvin = request.getParameter("keywordvin");
      String keywordyear = request.getParameter("keywordyear");
      String keywordprice = request.getParameter("keywordprice");
      String keywordphone = request.getParameter("keywordphone");
      String keywordemail = request.getParameter("keywordemail");
      search(keywordmake, keywordmodel, keywordvin, keywordyear, keywordprice, keywordphone, keywordemail, response);
   }

   void search(String keywordmake, String keywordmodel, String keywordvin, String keywordyear, String keywordprice, String keywordphone, String keywordemail, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;

         if (keywordmake.isEmpty() &&keywordmodel.isEmpty() && keywordvin.isEmpty() &&keywordyear.isEmpty() &&keywordprice.isEmpty() &&keywordphone.isEmpty() &&keywordemail.isEmpty()) {
            String selectSQL = "SELECT * FROM Cars";
            preparedStatement = connection.prepareStatement(selectSQL);
         }
         else {
        	 String selectSQL = "SELECT * FROM Cars WHERE Makes LIKE ? AND Models LIKE ? AND VIN LIKE ? AND Year LIKE ? AND Price LIKE ? AND Phone LIKE ? AND Email LIKE ?";
        	 String make = keywordmake + "%";
             String model = keywordmodel + "%";
             String vin = keywordvin + "%";
             String year = keywordyear + "%";
             String price = keywordprice + "%";
             String phone = keywordphone + "%";
             String email = keywordemail + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, make);
             preparedStatement.setString(2, model);
             preparedStatement.setString(3, vin);
             preparedStatement.setString(4, year);
             preparedStatement.setString(5, price);
             preparedStatement.setString(6, phone);
             preparedStatement.setString(7, email);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String make = rs.getString("Makes").trim();
            String model = rs.getString("Models").trim();
            String vin = rs.getString("VIN").trim();
            String year = rs.getString("Year").trim();
            String price = rs.getString("Price").trim();
            String phone = rs.getString("Phone").trim();
            String email = rs.getString("Email").trim();

            if (keywordmake.isEmpty() || make.contains(keywordmake)) {
               //out.println("ID: " + id + ", ");
               out.println("Make: " + make + ", ");
               out.println("Model: " + model + ", ");
               out.println("VIN: " + vin + ", ");
               out.println("Year: " + year + ", ");
               out.println("Price: " + price + ", ");
               out.println("Phone: " + phone + ", ");
               out.println("Email: " + email + "<br>");
            }
         }
         out.println("<a href=./search_data.html>Search Data</a> <br>");
         out.println("</body></html>");
         out.println("<a href=./sign_up.html>Sign Up</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
