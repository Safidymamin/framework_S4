package etu2003.framework.servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import etu2003.framework.Mapping;
import etu2003.framework.utils.*;
import java.io.IOException;
import java.io.PrintWriter;

public class FrontServlet extends HttpServlet{
    HashMap<String,Mapping> MappingUrl;
      public void init() throws ServletException {
        String packageName = getServletContext().getInitParameter("packageName");

        try{
            PrintWriter writer = getServletContext().getWriter();
            writer.println("<html><body>");
            writer.println("<h1>Contenu généré dans la méthode init du servlet</h1>");
            writer.println("</body></html>");
            Utile.getAllMapping(this.MappingUrl, packageName);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {}
}