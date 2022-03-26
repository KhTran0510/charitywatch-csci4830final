import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Info;
import util.UtilDBHa;

@WebServlet("/SimpleInsertHB")
public class SimpleInsertHB extends HttpServlet implements Info {
   private static final long serialVersionUID = 1L;

   public SimpleInsertHB() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String make = request.getParameter("make").trim();
      String model = request.getParameter("model").trim();
      String vin = request.getParameter("vin").trim();
      String year = request.getParameter("year").trim();
      String price = request.getParameter("price").trim();
      String phone = request.getParameter("phone").trim();
      String email = request.getParameter("email").trim();
      UtilDBHa.createCars(make, model, vin, year, price, phone, email);

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Car Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");
      out.println("<ul>");
      out.println("<li> Make: " + make);
      out.println("<li> Model: " + model);
      out.println("<li> VIN: " + vin);
      out.println("<li> Year: " + year);
      out.println("<li> Price: " + price);
      out.println("<li> Phone: " + phone);
      out.println("<li> Email: " + email);
      out.println("</ul>");
      out.println("<a href=/" + projectName + "/" + searchWebName + ">Search Car</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
