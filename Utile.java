package etu2003.framework.utils;
import java.util.*;
import etu2003.framework.*;
import java.net.URL;
import java.io.*;
import java.lang.reflect.*;
import jakarta.servlet.ServletException;

public class Utile{
    public static void getAllMapping(HashMap<String,Mapping> mappingUrls, String packageName)throws Exception{
         try {
        mappingUrls = new HashMap<String, Mapping>();
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "//")); 
            for (File file : new File(root.getFile().replaceAll("%20", " ")).listFiles()) {
                if (file.getName().contains(".class")) {
                    String className = file.getName().replaceAll(".class$", "");
                    Class<?> cls = Class.forName(packageName + "." + className);
                    for (Method method : cls.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(Annotation.class)) {
                            mappingUrls.put(method.getAnnotation(Annotation.class).url(), new Mapping(cls.getName(), method.getName()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}