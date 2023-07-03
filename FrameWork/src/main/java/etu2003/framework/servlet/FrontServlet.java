package etu2003.framework.servlet;

import etu2003.framework.Mapping;
import etu2003.framework.Util;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.HashMap;

//@WebServlet(name = "FrontServlet", value = "/*")
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;

    public void init() throws ServletException {
        String packageName = getServletContext().getInitParameter("packageName");
        try{
            Util.traitementMappingUrls(this.mappingUrls, packageName);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
