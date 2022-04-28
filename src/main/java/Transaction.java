

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Transaction
 */
@WebServlet("/Transaction")
public class Transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transaction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String transactionType = request.getParameter("submit");
		String found_name = request.getParameter("foundName");
		String note = request.getParameter("note");
		
		
		String email = (String) request.getSession().getAttribute("email");
		String password = (String) request.getSession().getAttribute("password");
		String acctype = (String) request.getSession().getAttribute("acctype");
		
		System.out.println(acctype);
		System.out.println(email);
		System.out.println(password);
		
		
		if(transactionType.equals("Donate")) {
			
			if (request.getParameter("amount").isBlank()) {
				printerror("Please enter an amount for donation!", response);
				 
			}else {
				try {
			        double amount = Double.parseDouble(request.getParameter("amount"));
			        if (amount > 0)
			        	donor_deposit(email, found_name, amount, note, response);
			        else {
			        	printerror("Invalid Amount!", response);
			        }
			    } catch (NumberFormatException nfe) {
			    	printerror("Invalid input type!", response);
			    }
			}
			
		}
			
			//System.out.println(amount);
			//System.out.println(found_name);
			//donor_deposit(email, found_name, amount, note);
		if (transactionType.equals("Withdraw")) {
			double total = (Double) request.getSession().getAttribute("total");
			
			if (request.getParameter("amount").isBlank()) {
				printerror("Please enter an amount for withdrawal!", response);
				 
			}else {
				try {
			        double amount = Double.parseDouble(request.getParameter("amount"));
			        if (amount < total && amount > 0)
			        	foundation_withdraw(email, amount, total, note, response);
			        else if (amount > total)
			        	printerror("Insufficient amount to withdraw!", response);
			        else if (amount < 0)
			        	printerror("Invalid Amount!", response);
			        
			    } catch (NumberFormatException nfe) {
			    	printerror("Invalid input type!", response);
			    }
			}
				
		}
	}
	

	protected void donor_deposit(String donor_email, String found_name, Double amount, String note, HttpServletResponse response) {
		
		String searchSQL = "SELECT email FROM foundation WHERE found_name = ?";
		
		
		Connection connection = null;
		PreparedStatement preparedStatementSearch = null;
		PreparedStatement preparedStatementInsert = null;
	      ResultSet rs = null;
	      try {
	         DBConnection.getDBConnection(getServletContext());
	         connection = DBConnection.connection;
	         preparedStatementSearch = connection.prepareStatement(searchSQL);
	         
	         preparedStatementSearch.setString(1, found_name);
	         //connection.close();
	         rs = preparedStatementSearch.executeQuery();
	         String found_email = null;
	         while (rs.next()) {
	             //int id = rs.getInt("id");
	        	 found_email = rs.getString("email").trim();
	         }
	         
	         
        	 String insertSQL = "insert into finance (found_email,donor_email,amount,spent,note,date_time)"
        	 					+ " values (?,?,?,0,?,sysdate());";
        	 preparedStatementInsert = connection.prepareStatement(insertSQL);
             preparedStatementInsert.setString(1, found_email);
             preparedStatementInsert.setString(2, donor_email);
             preparedStatementInsert.setDouble(3, amount);
             preparedStatementInsert.setString(4, note+"<br>");
             preparedStatementInsert.execute();
	         
	         /*System.out.println(found_email);
	         System.out.println(donor_email);
	         System.out.println(amount);*/
             PrintWriter out = response.getWriter();//
	       	  response.setContentType("text/html");
	       	  out.println("<script type=\"text/javascript\">");
	       	  out.printf("alert('Transaction Success\\n"
	       	  		+ "Donate amount: $%.2f\\n"
	       	  		+ "to Foundation: %s\\n"
	       	  		+ "Note: %s\\n" 
	       	  		+ "');\n",amount, found_name, note);
	       	  out.println("window.location.href=\"Login\";");
	       	  out.println("</script>");
	        
		
	      } catch (SQLException se) {
		         se.printStackTrace();
		         printerror("No foundation selected!", response);
		         
		      } catch (Exception e) {
		         e.printStackTrace();
		      } finally {
		         try {
		            if (preparedStatementSearch != null)
		               preparedStatementSearch.close();
		            
		            if (preparedStatementInsert != null)
			               preparedStatementInsert.close();
		         } catch (SQLException se2) {
		         }
		         try {
		            if (connection != null)
		               connection.close();
		         } catch (SQLException se) {
		            se.printStackTrace();
		         }
		      }
	      System.out.println("Deposit Success");
		
		
		
	}
	
	protected void foundation_withdraw(String email, Double withdraw, Double total, String note, HttpServletResponse response) {
		String selectSQL = "select * from finance where found_email = ? and spent < amount;";
		
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
		  
		  if (note.isBlank()) {
			  printerror("Missing note!", response);
		  }
		
		Connection connection = null;
		PreparedStatement preparedStatementSearch = null;
		PreparedStatement preparedStatementUpdate = null;
	    ResultSet rs = null;
	      try {
	         DBConnection.getDBConnection(getServletContext());
	         connection = DBConnection.connection;
	         preparedStatementSearch = connection.prepareStatement(selectSQL);
	         
	         preparedStatementSearch.setString(1, email);
             rs = preparedStatementSearch.executeQuery();
	         
            
             String adjustSpentSQL = "update finance set spent = ?, note = ? where id = ?;";
             
             
             
             int countDonor = 0;
             double amountForReceipt = withdraw;
             
             while (rs.next()) {
            	 double donateAmount = rs.getDouble("amount");
            	 double donateSpent = rs.getDouble("spent");
            	 int id = rs.getInt("id");
            	 String donateNote = rs.getString("note");
            	 LocalDateTime now = LocalDateTime.now();
            	 double remain_withdraw_diff = (withdraw - (donateAmount - donateSpent));
            	 //if withdraw amount less than the remain amount => spent += withdraw amount => break loop
            	 if(remain_withdraw_diff <= 0) {
            		 preparedStatementUpdate = connection.prepareStatement(adjustSpentSQL);
            		 preparedStatementUpdate.setDouble(1, withdraw + donateSpent);
            		 preparedStatementUpdate.setString(2, donateNote + " (Used for " + note +" at: " + dtf.format(now) +")<br>");
            		 preparedStatementUpdate.setInt(3, id);
            		 preparedStatementUpdate.execute();
            		 countDonor++;
            		 break;
            	 }
            	 //if withdraw amount greater than the remain amount => spent = donateAmount; amount -= remain => break loop
            	 else if(remain_withdraw_diff > 0) {
            		 preparedStatementUpdate = connection.prepareStatement(adjustSpentSQL);
            		 preparedStatementUpdate.setDouble(1, donateAmount);
            		 preparedStatementUpdate.setString(2, donateNote + " (Used for " + note +" at: " + dtf.format(now) +")<br>");
            		 preparedStatementUpdate.setInt(3, id);
            		 preparedStatementUpdate.setInt(3, id);
            		 preparedStatementUpdate.execute();
            		 withdraw -= (donateAmount - donateSpent);
            		 countDonor++;
            	 }
            	 
             }
             connection.close();
             
             PrintWriter out = response.getWriter();//
	       	  response.setContentType("text/html");
	       	  out.println("<script type=\"text/javascript\">");
	       	  out.printf("alert('Transaction Success\\n"
	       	  		+ "Successfully withdraw: $%.2f\\n"
	       	  		+ "from %s donor(s)\\n"
	       	  		+ "');\n", amountForReceipt, countDonor);
	       	  out.println("window.location.href=\"Login\";");
	       	  out.println("</script>");
	         
	      } catch (SQLException se) {
		         se.printStackTrace();
		      } catch (Exception e) {
		         e.printStackTrace();
		      } finally {
		         try {
		            if (preparedStatementSearch != null)
		               preparedStatementSearch.close();
		            
		            if (preparedStatementUpdate != null)
			               preparedStatementUpdate.close();
		         } catch (SQLException se2) {
		         }
		         try {
		            if (connection != null)
		               connection.close();
		         } catch (SQLException se) {
		            se.printStackTrace();
		         }
		      }
		System.out.println("Withdraw Success");
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void printerror(String feedback, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("text/html");
		 	out.println("<script type=\"text/javascript\">");
		 	out.println("alert('"+feedback+"\\n');");
		 	out.println("window.location.href=\"Login\";");
		 	out.println("</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	  
	}
}


//request.getSession().removeAttribute(found_name) 




