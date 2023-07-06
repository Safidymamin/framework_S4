package etu2003.framework.utils;

import etu2003.framework.Mapping;
import etu2003.framework.servlet.ModelView;
import etu2003.annotation.url;

import java.net.URL;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

public class Fonctions{

// SPRINT-8: manisy parametre am fonction
    // ty any am SPRINT 5 no apesaina
    public static Object setMethodsParameters(Method methode, Object objet, HttpServletRequest request) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        Enumeration<String> paramNames_1 = request.getParameterNames();
        HashMap<String, Object[]> parametres = new HashMap<>();
        while (paramNames_1.hasMoreElements()) {
            String paramName = paramNames_1.nextElement();
            Object[] paramValues = request.getParameterValues(paramName);
            parametres.put(paramName, paramValues);
        }
        
        Parameter[] parametresfonction = methode.getParameters();
        Object[] values = new Object[parametresfonction.length];

        // Création du tableau de noms de paramètres
        String[] parametersclasses = new String[parametresfonction.length];
        // Parcours des paramètres et récupération des noms
        for (int i = 0; i < parametresfonction.length; i++) {
            parametersclasses[i] = parametresfonction[i].getName();
        }
        
        for (Map.Entry<String, Object[]> entry : parametres.entrySet()) { //pour chaque clé / clé 
            String key = entry.getKey();
            for(int i = 0; i < parametersclasses.length; i++){
                if(parametersclasses[i].equals(key)){
                    Object[] params = parametres.get(key);
                    if(params.length == 1){
                        values[i] = convertToPrimitive(params[0], parametresfonction[i].getType());
                        i += 1;
                    }
                    else if(params.length > 1){
                        int[] array_object_to_set = new int[params.length];
                        int j = 0;
                        for(Object p : params){
                            System.out.println("p: "+p);
                            array_object_to_set[j] = Integer.parseInt((String) p);//Utile.convertToPrimitive(p, int.class);
                            j += 1;
                        }
                        values[i] = array_object_to_set;
                        i += 1;
                    }

                }
            }
        }
        Object retour = null;
        retour = methode.invoke(objet, values);
        return retour;
   }

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
        // out.println("Tokny eto leizy");
        // out.println("tyy "+(String)object.getClass().getName()+" tyy");
        // out.println("Tokny eto leizy");
        request.setAttribute((String)object.getClass().getName(), object);
       
        return object;
    }

// SPRINT 6: maka donnée anle modelView ho dispatchena
    public static void recuperationData(ModelView modelView, HttpServletRequest request, HttpServletResponse response){
        HashMap<String, Object> data = modelView.getData();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object valeurObjet = entry.getValue();
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
                Method methode = obtenirMethode(object, method);

                // ty niova sprint 8
                

                // retour = (ModelView) methode.invoke(object);
                retour = (ModelView) setMethodsParameters(methode, object, request);
                

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

// Fonctions nilaina teny fotsiny
    private static Object convertToPrimitive(Object value, Class<?> type) {
        if (type.equals(byte.class)) {
            return Byte.valueOf(value.toString());
        } else if (type.equals(short.class)) {
            return Short.valueOf(value.toString());
        } else if (type.equals(int.class)) {
            return Integer.valueOf(value.toString());
        } else if (type.equals(long.class)) {
            return Long.valueOf(value.toString());
        } else if (type.equals(float.class)) {
            return Float.valueOf(value.toString());
        } else if (type.equals(double.class)) {
            return Double.valueOf(value.toString());
        } else if (type.equals(boolean.class)) {
            return Boolean.valueOf(value.toString());
        } else if (type.equals(char.class)) {
            return value.toString().charAt(0);
        } else {
            throw new IllegalArgumentException("Type non supporté : " + type.getName());
        }
    }

    public static Method obtenirMethode(Object object, String nomMethode){
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().equals(nomMethode)){
                return method;
            }
        }
        return null;
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