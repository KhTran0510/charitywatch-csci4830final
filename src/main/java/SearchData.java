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
   
   private String styleListForHTML = 				//style for list box
			"<style>\n"	
		    + ".listbox {\n"	
		    + "  display: grid;\n"
		    + "	 padding: 10px;\n"
		    + "  border: 1px solid #aaa;\n"
		    + "  background: #eee;\n"
		    + "  position: relative;\n"
		    + "  height: 200px;"
		    + "  overflow-y: auto"
		    + "}\n"
		    
		    + " @charset \"ISO-8859-1\";\r\n"
		    + "\r\n"
		    + "header {\r\n"
		    + "    background-color:#2b2b2b;\r\n"
		    + "    color:white;\r\n"
		    + "    text-align:center;\r\n"
		    + "    padding:5px;	 \r\n"
		    + "}\r\n"
		    + "\r\n"
		    + "body{\r\n"
		    + "	background-color:#edffee;\r\n"
		    + "}\r\n"
		    + "nav {\r\n"
		    + "	background-color:#3b8a3e;\r\n"
		    + "	color:white;\r\n"
		    + "    line-height:30px;\r\n"
		    + "    height:1000px;\r\n"
		    + "    width:100px;\r\n"
		    + "    float:left;\r\n"
		    + "    padding:10px;	      \r\n"
		    + "}\r\n"
		    + "\r\n"
		    + "h1 {\r\n"
		    + "    font-family:roboto;\r\n"
		    + "}\r\n"
		    + "\r\n"
		    + "button {\r\n"
		    + "	background-color: #4CAF50;\r\n"
		    + "    height:70px;\r\n"
		    + "    width:100px;\r\n"
		    + "    border: none;\r\n"
		    + "    color: white;\r\n"
		    + "    text-align: center;\r\n"
		    + "    text-decoration: none;\r\n"
		    + "    display: inline-block;\r\n"
		    + "    font-size: 16px;\r\n"
		    + "    margin: 4px 2px;\r\n"
		    + "    cursor: pointer;	      \r\n"
		    + "}\r\n"
		    + "\r\n"
		    + "section {\r\n"
		    + "	width:350px;\r\n"
		    + "    float:left;\r\n"
		    + "    padding:10px;	 	 \r\n"
		    + "}\r\n"
		    + "\r\n"
		    + "footer {\r\n"
		    + "    background-color:#2b2b2b;\r\n"
		    + "    color:white;\r\n"
		    + "    clear:both;\r\n"
		    + "    text-align:center;\r\n"
		    + "    padding:5px;	 	 \r\n"
		    + "}\r\n"
		    + "\r\n"
		    + "input[type=submit] {\r\n"
		    + "    background-color: #4CAF50;\r\n"
		    + "    border: none;\r\n"
		    + "    color: white;\r\n"
		    + "    padding: 15px 32px;\r\n"
		    + "    text-align: center;\r\n"
		    + "    text-decoration: none;\r\n"
		    + "    display: inline-block;\r\n"
		    + "    font-size: 16px;\r\n"
		    + "    margin: 4px 2px;\r\n"
		    + "    cursor: pointer;\r\n"
		    + "}\r\n"
		    + ".split {\r\n"
		    + "	padding: 0 18px;\r\n"
		    + "  	display: none;\r\n"
		    + "  	overflow: hidden;\r\n"
		    + "}"
		    + "</style>" ;

   public SearchData() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String typeStart = request.getParameter("submit");
	  
	  if (typeStart.equals("Start Donation")) {
		  System.out.println("Donor is trying to make deposit");
		  String email = (String) request.getSession().getAttribute("email");
		  String keywordfound_name = "";
		  //System.out.println(email);
		  donor_start_search(keywordfound_name, email,response);
	  }if (typeStart.equals("SearchData")) {
		  System.out.println("Donor is trying to make deposit");
		  String email = (String) request.getSession().getAttribute("email");
		  String keywordfound_name = request.getParameter("keywordfound_name");
		  donor_start_search(keywordfound_name, email,response);
	  }else if (typeStart.equals("Search")){
		  String keywordfound_name = request.getParameter("keywordfound_name");
	      html_start_search(keywordfound_name, response);
	  }
	  
	  
   }//

   void html_start_search(String keywordfound_name, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Foundation Search";
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
         
         
         out.println("<input type=\"submit\" name=\"submit\" value=\"Search\">");
         
         
         out.println("</form><br/>");
         
         out.println("<div class=\"listbox\">\n");
         out.println("<div>\n");
         while (rs.next()) {
            //int id = rs.getInt("id");
            String found_name = rs.getString("found_name").trim();
            

            if (keywordfound_name.isEmpty() || found_name.contains(keywordfound_name)) {
            	out.println("<input type=\"radio\" id=\""+found_name+"\" name=\"acctype\" value=\""+found_name+"\" checked>");
            	out.println(found_name + "<br>");
               
            }
         }
         
         out.println("</div></div>\n");
         //out.println("<form action=\"Transaction\" method=\"POST\">");
         //out.println("<input type=\"submit\" name=\"submit\" value=\"Donate\" /><br/>\n");
         
         out.println("</form>\n");
         //out.println("<a href=./search_data.html>Search Data</a> <br>");
         out.println(styleListForHTML);
         out.println("</body></html>");
         out.println("<button onclick=\"location.href='./sign_up.html'\">Sign Up</button>");
         out.println("<button onclick=\"location.href='./login.html'\">Login</button>");
        // out.println("</body></html>");
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
   
void donor_start_search(String keywordfound_name, String email,HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Foundation Search";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      /*out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");
            */
      
      out.println(docType + //
	            "<html>\n" + //
	            "<head><title>"+ title +"</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n"); 
	      
      out.println("<div style=\'float:right\'>");
      out.println("<form action=\"Logout\" method=\"POST\">");
      out.println("<button>Logout</button>");
      out.println("</form>");
      out.println("</div>");
	            
	      out.println("<h1 align=\"left\"><t>" + title + "</h1>\n");

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
         //out.println("<input type=\"submit\" name=\"submit\" value=\"Search\" />");
         out.println("<button type=\"submit\" name=\"submit\" value=\"SearchData\">Search</button>");
         
         out.println("</form><br/>");
         
         out.println("<form action=\"Transaction\" method=\"POST\">");
         
         out.println("<div class=\"listbox\">\n");
         out.println("<div>\n");
         while (rs.next()) {
            //int id = rs.getInt("id");
            String found_name = rs.getString("found_name").trim();
            

            if (keywordfound_name.isEmpty() || found_name.contains(keywordfound_name)) {
               //out.println("ID: " + id + ", ");
            	out.println("<input type=\"radio\" id=\""+found_name+"\" name=\"foundName\" value=\""+found_name+"\" checked>");
            	out.println(found_name + "<br>");
               
            }
         }
         
         out.println("</div></div>\n");
         
         out.println("Amount:  <input type=\"text\" name=\"amount\">");
         out.println("<br>Note:<br><textarea name=\"note\" cols=\"40\" rows=\"5\"></textarea>");
         out.println("<br><input type=\"submit\" name=\"submit\" value=\"Donate\" /><br/>\n");
         out.println("</form>\n");
         //out.println("<a href=./search_data.html>Search Data</a> <br>");
         out.println(styleListForHTML);
         out.println("</body></html>");
         
         //out.println("<a href=./sign_up.html>Sign Up</a> <br>");
        // out.println("</body></html>");
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
