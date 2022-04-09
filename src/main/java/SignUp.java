
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
import java.sql.ResultSet;

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
      
      String typeinput = request.getParameter("submit");
      
      if ("Create Foundation Account".equals(typeinput)) {	//*********Submit New Foundation Account********
    	  String fullname = request.getParameter("nameF");		
          String found_name = request.getParameter("found_name");
          String emailF = request.getParameter("emailF").toLowerCase();
          String addressF = request.getParameter("addressF");
          String passwordF = request.getParameter("passwordF");
          
          																//if there is at least one blank text box then popup an alert window
          if (fullname.isBlank() || found_name.isBlank() || emailF.isBlank() || addressF.isBlank() || passwordF.isBlank()) {
        	  PrintWriter out = response.getWriter();
        	  response.setContentType("text/html");
        	  out.println("<script type=\"text/javascript\">");
        	  out.println("alert('Missing Info to Submit!!');");
        	  out.println("window.location.href=\"sign_up.html\";");
        	  out.println("</script>");
        	  															//check if account already existed or not
          }else if(checkAccountExistence(typeinput, found_name, emailF) == true) {
        	  PrintWriter out = response.getWriter();
        	  response.setContentType("text/html");
        	  out.println("<script type=\"text/javascript\">");
        	  out.println("alert('Account is already exist!!');");
        	  out.println("window.location.href=\"sign_up.html\";");
        	  out.println("</script>");
        	 
        	  //request.getRequestDispatcher("/sign_up.html").forward(request, response);
    	  
          }else {
        	  foundationSignup(fullname, found_name, emailF, addressF, passwordF, response);
          }
	      
      } else {													//*********Submit New Donor Account********
    	  String first = request.getParameter("first");		
          String last = request.getParameter("last");
          String email = request.getParameter("email").toLowerCase();
          String address = request.getParameter("address");
          String password = request.getParameter("password");
          																//if there is at least one blank text box then popup an alert window
          if (first.isBlank() || last.isBlank() || email.isBlank() || address.isBlank() || password.isBlank()) {
        	  PrintWriter out = response.getWriter();
        	  response.setContentType("text/html");
        	  out.println("<script type=\"text/javascript\">");
        	  out.println("alert('Missing Info to Submit!!');");
        	  out.println("window.location.href=\"sign_up.html\";");
        	  out.println("</script>");
        	  
          }else if(checkAccountExistence(typeinput, "", email) == true) {
        	  PrintWriter out = response.getWriter();
        	  response.setContentType("text/html");
        	  out.println("<script type=\"text/javascript\">");
        	  out.println("alert('Account is already exist!!');");
        	  out.println("window.location.href=\"sign_up.html\";");
        	  out.println("</script>");
        	 
        	  //request.getRequestDispatcher("/sign_up.html").forward(request, response);
    	  
          }else {
        	  donorsSignup(first, last, email, address, password, response);
          }
      
      }
      
   }
   															
   													//Foundation v1 = found_name, v2 = email
   														//Donor v1 = "", v2 = email
   protected boolean checkAccountExistence(String accType, String v1, String v2) {
	   Connection connection = null;
       //String insertSql_foundation =
        
       //String insertSql = " INSERT INTO foundation (first, last, email, address, password) values (?, ?, ?, ?, ?)";
       String selectSql;
       
       if (accType.equals("Create Foundation Account")) {
    	   selectSql = "SELECT * FROM foundation WHERE found_name = ? OR email = ? UNION "
    	   		+ "SELECT * FROM donors WHERE email = ?";
    	   try {
    	          DBConnection.getDBConnection(getServletContext());
    	          connection = DBConnection.connection;
    	          PreparedStatement preparedStmt = connection.prepareStatement(selectSql);
    	          preparedStmt.setString(1, v1);
    	          preparedStmt.setString(2, v2);
    	          preparedStmt.setString(3, v2);
    	          ResultSet rsCheck = preparedStmt.executeQuery();
    	    	  
    	          
    	          if (rsCheck.next() != false) {	//if account already existence
    	        	  connection.close();
    	        	  return true;				
    	          }else {
    	        	  connection.close();
    	        	  return false;
    	          }
    	          
    	          
    	          
    	       } catch (Exception e) {
    	          e.printStackTrace();
    	       }
    	   
       }else {
    	   selectSql = "SELECT * FROM donors WHERE email = ? UNION "
    	   		+ "SELECT * FROM foundation WHERE email = ?";
    	   try {
    	          DBConnection.getDBConnection(getServletContext());
    	          connection = DBConnection.connection;
    	          PreparedStatement preparedStmt = connection.prepareStatement(selectSql);
    	          preparedStmt.setString(1, v2);
    	          preparedStmt.setString(2, v2);
    	          ResultSet rsCheck = preparedStmt.executeQuery();
    	          
    	          if (rsCheck.next() != false) {	//if account already existence
    	        	  connection.close();
    	        	  return true;				
    	          }else {
    	        	  connection.close();
    	        	  return false;
    	          }
    	       } catch (Exception e) {
    	          e.printStackTrace();
    	       }
       }
       
       
       
       
       
       
       return true;
   }
   
   protected void foundationSignup(String fullname, String found_name, String emailF, String addressF, String passwordF, HttpServletResponse response) {
	   		Connection connection = null;
	      //String insertSql_foundation =
	       
	      String insertSql = " INSERT INTO foundation (found_name, fullname, email, address, password) values (?, ?, ?, ?, ?)";
	      //String insertSql_donor = " INSERT INTO donor (id, Makes, Models, VIN, Year, Price, Phone, Email) values (default, ?, ?, ?, ?, ?, ?, ?)";
	      
	      
	      try {
	         DBConnection.getDBConnection(getServletContext());
	         connection = DBConnection.connection;
	         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
	         preparedStmt.setString(1, found_name);
	         preparedStmt.setString(2, fullname);
	         preparedStmt.setString(3, emailF);
	         preparedStmt.setString(4, addressF);
	         preparedStmt.setString(5, passwordF);
	         preparedStmt.execute();
	         connection.close();
	         
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
		          		

		            "  <li><b>Fullname</b>: " + fullname + "\n" + //
		            "  <li><b>Foundation Name</b>: " + found_name + "\n" + //
		            "  <li><b>Email</b>: " + emailF + "\n" + //
		            "  <li><b>Address</b>: " + addressF + "\n" + //
		           // "  <li><b>Pass</b>: " + password + "\n" + //
		            "</ul>\n");

		      out.println("<a href=./login.html>Login Page</a> <br>");
		      out.println("</body></html>");
		      
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      
	      
   }
   
   protected void donorsSignup(String first, String last, String email, String address, String password, HttpServletResponse response) {
	   Connection connection = null;
       //String insertSql_foundation =
        
       //String insertSql = " INSERT INTO foundation (first, last, email, address, password) values (?, ?, ?, ?, ?)";
       String insertSql = " INSERT INTO donors (first, last, email, address, password) values (?, ?, ?, ?, ?)";

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
               // "  <li><b>Pass</b>: " + password + "\n" + //
                "</ul>\n");

          out.println("<a href=./login.html>Login Page</a> <br>");
          out.println("</body></html>");
          
       } catch (Exception e) {
          e.printStackTrace();
       }
   
   //request.setAttribute("first", first);
   //request.setAttribute("last", last);
   
   //request.getRequestDispatcher("/WEB-INF/test.jsp").forward(request, response);
       
   }
   

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
   

}
