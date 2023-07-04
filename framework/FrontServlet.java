package etu2003.framework.servlet;

import etu2003.framework.Mapping;
import etu2003.framework.utils.Fonctions;
import etu2003.annotation.url;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import java.io.*;
import java.util.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class FrontServlet extends HttpServlet{

    HashMap<String, Mapping> mappingUrls;

    public void init() throws ServletException {
        
        try {
            String packageName = getServletContext().getInitParameter("packageName");
// sprint3
            mappingUrls = Fonctions.mameno_HashMap(mappingUrls, packageName);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

// sprint5
        ModelView modelView = Fonctions.recup_ModelView(mappingUrls, request, response);
// sprint6: request.setAttribute(key, (Object)valeurObjet);
        Fonctions.recuperationData(modelView, request, response);
        
        
        
        try {
            Object myObject = Fonctions.getMyObject(mappingUrls, request, response);
            // sprint 7: maka données formulaire
            myObject = Fonctions.recuperationInputData(myObject, request, response);
            afficherDetailClass(myObject, out); 

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace(out);
        }
        
        
        String packageName = getServletContext().getInitParameter("packageName");
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "//")); 
        out.println("Front Servlet");   
        out.println("Root: " + root);
        for(String key : mappingUrls.keySet()){
            Mapping mapping = mappingUrls.get(key);
            out.println("<br>"); 
            out.println("Cle: " + key + ", ClassName: "+ mapping.getClassName() + ", Mapping: " + mapping.getMethod());
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/"+modelView.getUrl());
        requestDispatcher.forward(request, response);
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            processRequest(request, response);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace(out);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            processRequest(request, response);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace(out);
        }
    }

    protected void afficherDetailClass(Object object, PrintWriter out)throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,InvocationTargetException {
        out.println("----Debut my object ------------------------------------------------------------------myObject------------------------------------------------------------------");
            Field[] fields = object.getClass().getDeclaredFields();
            Method[] methods = object.getClass().getDeclaredMethods();
            // Method metho =object.getClass().getMethod("setNom",String.class);
            // metho.invoke(object, "Anthony");
            out.println("<br>");
            out.println("Field.length: "+ fields.length);
            for (Field field : fields) {
                field.setAccessible(true);
                out.println("<br>");
                out.println(field.getName()+" = "+(String)field.get(object));
            }
            out.println("<br>");
            out.println("Method.length: "+ methods.length);
            for (Method method : methods) {
                method.setAccessible(true);
                out.println("<br>");
                out.println(method.getName());
            }
            out.println("<br>");
            out.println("----fin my object ------------------------------------------------------------------myObject------------------------------------------------------------------");
            out.println("<br>");
    }


}

