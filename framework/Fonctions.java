package etu2003.framework.utils;

import etu2003.framework.Mapping;
import etu2003.framework.servlet.ModelView;
import etu2003.annotation.url;
import java.util.*;
import java.net.URL;

import java.io.*;
// import java.lang.reflect.Method;
import java.util.*;
import java.lang.reflect.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import etu2003.annotation.url;
import jakarta.servlet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Fonctions{
    // SPRINT 7: Recuperation données formulaire
    public static Object recuperationInputData(Object object, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,InvocationTargetException, IOException{
        PrintWriter out = response.getWriter();
        Field[] fields = object.getClass().getDeclaredFields();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.getName().equals(paramName)) {
                    String methodName = "set" + capitalizeFirstLetter(field.getName());
                    Method method = object.getClass().getMethod(methodName, field.getType());
                    Object paramValue = request.getParameter(paramName);
                    method.invoke(object, paramValue);
                }
            }
        }
          Field[] newFields = object.getClass().getDeclaredFields();
        for (Field field : newFields) {
            field.setAccessible(true);
            String cle = field.getName();
            Object valeur = field.get(object);
            if (request.getAttribute(cle) == null) {
                request.setAttribute(cle, valeur);
            }
        }
        request.setAttribute((String)object.getClass().getName(), object);
        return object;
    }
    


    // SPRINT 6: maka donnée anle modelView ho dispatchena
    public static void recuperationData(ModelView modelView, HttpServletRequest request, HttpServletResponse response){
        HashMap<String, Object> data = modelView.getData();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object valeurObjet = entry.getValue();
            request.setAttribute(key, (Object)valeurObjet);
            if (request.getAttribute(key) == null) {
                request.setAttribute(key, (Object)valeurObjet);
            }
        }
    }

// SPRINT 5: maka modelView anaovana dispatcher
    // recuperation valeur de retour et dispatcher
    public static ModelView recup_ModelView(HashMap<String, Mapping> mappingUrls,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out =response.getWriter();
        
        ModelView retour = new ModelView();
        // sprint5
        String stringUri = request.getRequestURI();
        String[] arrayPath = stringUri.split("/");
        String cle = arrayPath[arrayPath.length - 1];

        try{
            Mapping mapping = new Mapping();
            for(String key : mappingUrls.keySet()){
                if(key.equals(cle)){
                    mapping = mappingUrls.get(key);
                    break;
                }
            }
            if(!mapping.getClassName().equals("default")){
                // out.println("tsy null tsony ilay mapping");
                String nomMethode = cle;
                String nomDeClasse = (String) mapping.getClassName();
                java.lang.Class cl = java.lang.Class.forName(nomDeClasse);
                Object object = cl.newInstance();
                String method = (String) mapping.getMethod();
                Method methode = object.getClass().getDeclaredMethod(method);
                retour = (ModelView) methode.invoke(object);
                

            }else{
                // out.println("Null ilay mapping ehh");
            }

        } catch(Exception e){
            e.printStackTrace(out);
        }
        return retour;
    }

    // SPRINT 3
       public static HashMap<String,Mapping> mameno_HashMap(HashMap<String,Mapping> mappingUrls, String packageName) throws Exception{
        try {
        mappingUrls = new HashMap<String, Mapping>();
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "//")); 
            for (File file : new File(root.getFile().replaceAll("%20", " ")).listFiles()) {
                if (file.getName().contains(".class")) {
                    String className = file.getName().replaceAll(".class$", "");
                    Class<?> cls = Class.forName(packageName + "." + className);
                    for (Method method : cls.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(url.class)) {
                            mappingUrls.put(method.getAnnotation(url.class).value(), new Mapping(cls.getName(), method.getName()));
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            throw new ServletException(e);
        }
           return mappingUrls;
    }

    
    public static String capitalizeFirstLetter(String mot){
        if(mot == null || mot.isEmpty()) return mot;
        else{
            char firstChar = Character.toUpperCase(mot.charAt(0));
            return firstChar + mot.substring(1);
        }
    }

    public static Object getMyObject(HashMap<String, Mapping> mappingUrls, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        Object object = new Object();

        String stringUri = request.getRequestURI();
        String[] arrayPath = stringUri.split("/");
        String cleUrl = arrayPath[arrayPath.length - 1];

        for(String keyMethod : mappingUrls.keySet()){
            Mapping mapping = mappingUrls.get(keyMethod);
            if (cleUrl.equals(keyMethod)) {
                Class<?> myClass = Class.forName(mapping.getClassName());
                object = myClass.newInstance();
            }
        }

        return object;
    }

}