

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

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String acctype = request.getParameter("acctype");
		String email = request.getParameter("email").toLowerCase();
		String password = request.getParameter("password");
		
		
		
		if (email.isBlank() || password.isBlank()) {
       	  PrintWriter out = response.getWriter();
       	  response.setContentType("text/html");
       	  out.println("<script type=\"text/javascript\">");
       	  out.println("alert('Missing Info to Login!!\\ndsafafg');");
       	  out.println("window.location.href=\"login.html\";");
       	  out.println("</script>");
		}else {
			if(checkAccountExistence(acctype, email, password)) {
				donor_profile(acctype, email, response);
			}else {
				PrintWriter out = response.getWriter();
		       	response.setContentType("text/html");
		       	out.println("<script type=\"text/javascript\">");
		       	out.println("alert('Wrong Email or Password!!');");
		       	out.println("window.location.href=\"login.html\";");
		       	out.println("</script>");
				System.out.println("Wrong Info Account");
			}
		}
		
		
	}
															//v1 = email; v2 = password
	protected boolean checkAccountExistence(String accType, String v1, String v2) {
		   Connection connection = null;
	       //String insertSql_foundation =
	        
	       //String insertSql = " INSERT INTO foundation (first, last, email, address, password) values (?, ?, ?, ?, ?)";
	       String selectSql = "SELECT * FROM "+ accType.toLowerCase() +" WHERE email = ? AND password = ?";
	       try {
 	          DBConnection.getDBConnection(getServletContext());
 	          connection = DBConnection.connection;
 	          PreparedStatement preparedStmt = connection.prepareStatement(selectSql);
 	          preparedStmt.setString(1, v1);
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
	       
	       
	       
	       return false;
	       
	}
		
	
	protected void donor_profile(String accType, String email, HttpServletResponse response) throws IOException {
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

	         
	        	 String selectSQL = "SELECT * FROM finance WHERE donor_email LIKE ?";
	             preparedStatement = connection.prepareStatement(selectSQL);
	             preparedStatement.setString(1, email);
	             
	         ResultSet rs = preparedStatement.executeQuery();
	         
	         while (rs.next()) {
	            //int id = rs.getInt("id");
	            //String id = rs.getString("id").trim();
	            String amount = rs.getString("amount").trim();
	            String spent = rs.getString("spent").trim();
	            
	            	out.println("Amount: " + amount + "<br>");
	            	out.println("Spent: " + spent + "<br>");
	               
	         }
	         out.println("<form action=\"search_data.html\">");
	         out.println("<input type=\"submit\" value=\"Search Foundation\" /></form>");
	         
	         //out.println("<input type=\"button\" onclick=\"location.href=\"search_data.html\";\" value=\"Search Foundation\" /><br/>");
	        
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
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
	
	//*************
			//insert into finance (found_email,donor_email,amount,spent,note,date_time) values("foodB@gmail.com","user@gmail.com",50,0,"hello", sysdate());
	//*************


