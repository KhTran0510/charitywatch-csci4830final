import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datamodel.CarHa;
import util.UtilDB;

@WebServlet("/MyServletHibernateDB")
public class MyServletHibernateDB extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public MyServletHibernateDB() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");

      // #1
      UtilDB.createCars("Khoa1", "Tran1","user1@gmail.com", "PKI 1 UNO", "password01");
      UtilDB.createCars("Khoa2", "Tran2","user2@gmail.com", "PKI 2 UNO", "password02");
      UtilDB.createCars("Khoa3", "Tran3","user3@gmail.com", "PKI 3 UNO", "password03");
      UtilDB.createCars("Khoa4", "Tran4","user4@gmail.com", "PKI 4 UNO", "password04");
      UtilDB.createCars("Khoa5", "Tran5","user5@gmail.com", "PKI 5 UNO", "password05");
      UtilDB.createCars("Khoa6", "Tran6","user6@gmail.com", "PKI 6 UNO", "password06");
      UtilDB.createCars("Khoa7", "Tran7","user7@gmail.com", "PKI 7 UNO", "password07");
      UtilDB.createCars("Khoa8", "Tran8","user8@gmail.com", "PKI 8 UNO", "password08");
      UtilDB.createCars("Khoa9", "Tran9","user9@gmail.com", "PKI 9 UNO", "password09");
      
      // #2
      retrieveDisplayData(response.getWriter());
   }

   void retrieveDisplayData(PrintWriter out) {
      String title = "Database Result:";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");
      out.println("<ul>");
      List<CarHa> listEmployees = UtilDB.listEmployees();
      for (CarHa employee : listEmployees) {
         System.out.println("[DBG] "
               + employee.getFirst() + ", " //
               + employee.getLast() + ", " //
               + employee.getEmail() + ", " //
               + employee.getAddress());

         out.println("<li>"
               + employee.getFirst() + ", " //
               + employee.getLast() + ", " //
               + employee.getEmail() + ", " //
               + employee.getAddress() + "</li>");
      }
      out.println("</ul>");
      out.println("</body></html>");
   }
   
   

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
