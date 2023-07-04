package etu2003.framework.servlet;

import etu2003.framework.Mapping;
import etu2003.framework.utils.Fonctions;
import etu2003.annotation.url;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.io.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException;

public class FrontServlet extends HttpServlet{

    HashMap<String, Mapping> mappingUrls;

    public void init() throws ServletException {
        
        try {
            String packageName = getServletContext().getInitParameter("packageName");
            
            // sprint3
            Fonctions.mameno_HashMap(mappingUrls, packageName);
        
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String packageName = getServletContext().getInitParameter("packageName");
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "//")); 
        out.println("Front Servlet");   
        out.println("Root: " + root);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
    }


}

