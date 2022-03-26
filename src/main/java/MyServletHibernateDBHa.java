import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datamodel.CarHa;
import util.UtilDBHa;

@WebServlet("/MyServletHibernateDBHa")
public class MyServletHibernateDBHa extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public MyServletHibernateDBHa() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");

      // #1
      UtilDBHa.createCars("Ford", "Explorer", "V111-3333", "2018","45.000","402-111-1111","car1@gmail.com");
      UtilDBHa.createCars("Ford", "Expedition", "V111-2222", "2019","65.000","402-222-2222","car2@gmail.com");
      UtilDBHa.createCars("Acura", "MDX", "V111-4444", "2023","42.000","402-333-3333","car3@gmail.com");
      UtilDBHa.createCars("Lexus", "LS", "V111-5555", "2019","33.000","402-444-4444","car4@gmail.com");
      
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
      List<CarHa> listEmployees = UtilDBHa.listEmployees();
      for (CarHa employee : listEmployees) {
         System.out.println("[DBG] " + employee.getId() + ", " //
               + employee.getMake() + ", " //
               + employee.getModel() + ", " //
               + employee.getVin() + ", " //
               + employee.getYear());

         out.println("<li>" + employee.getId() + ", " //
               + employee.getMake() + ", " //
               + employee.getModel() + ", " //
               + employee.getVin() + ", " //
               + employee.getYear() + "</li>");
      }
      out.println("</ul>");
      out.println("</body></html>");
   }
   
   

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
