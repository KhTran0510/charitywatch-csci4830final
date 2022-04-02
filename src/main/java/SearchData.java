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
	  String keywordfound_name = request.getParameter("keywordfound_name");
      search(keywordfound_name, response);
   }//

   void search(String keywordfound_name, HttpServletResponse response) throws IOException {
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

         if (keywordfound_name.isEmpty()) {
            String selectSQL = "SELECT * FROM foundation";
            preparedStatement = connection.prepareStatement(selectSQL);
         }
         else {
        	 String selectSQL = "SELECT * FROM foundation WHERE found_name LIKE ?";
        	 String found_name =  "%" + keywordfound_name + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, found_name);
         }
         ResultSet rs = preparedStatement.executeQuery();
         
         
         out.println("<form action=\"SearchData\" method=\"POST\">");
         out.println("Search:  <input type=\"text\" name=\"keywordfound_name\">");
         out.println("<input type=\"submit\" value=\"Search\" /></form><br/>");
         
         while (rs.next()) {
            //int id = rs.getInt("id");
            String found_name = rs.getString("found_name").trim();
            

            if (keywordfound_name.isEmpty() || found_name.contains(keywordfound_name)) {
               //out.println("ID: " + id + ", ");
            	out.println("<input type=\"radio\" id=\""+found_name+"\" name=\"acctype\" value=\""+found_name+"\" checked>");
            	out.println("Foundation: " + found_name + "<br>");
               
            }
         }
         
         out.println("<input type=\"submit\" value=\"Donate\" /><br/>");
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
