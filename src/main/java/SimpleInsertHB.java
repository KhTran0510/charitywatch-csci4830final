import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Info;
import util.UtilDB;

@WebServlet("/SimpleInsertHB")
public class SimpleInsertHB extends HttpServlet implements Info {
   private static final long serialVersionUID = 1L;

   public SimpleInsertHB() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String first = request.getParameter("first").trim();
      String last = request.getParameter("last").trim();
      String email = request.getParameter("email").trim();
      String address = request.getParameter("address").trim();
      String password = request.getParameter("password").trim();
      UtilDB.createCars(first, last, email, address, password);
      
      //run mysql here to insert sysdate *****
      

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
      out.println("<li> First: " + first);
      out.println("<li> Last: " + last);
      out.println("<li> Email: " + email);
      out.println("<li> Address: " + address);

      out.println("</ul>");
      out.println("<a href=/" + projectName + "/" + searchWebName + ">Search Car</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
