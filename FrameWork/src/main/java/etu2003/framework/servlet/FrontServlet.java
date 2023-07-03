package etu2003.framework.servlet;

import etu2003.framework.Mapping;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.HashMap;

//@WebServlet(name = "FrontServlet", value = "/*")
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> MappingUrl;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
