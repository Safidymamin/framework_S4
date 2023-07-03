package etu2003.framework;

import etu2003.annotation.Url;
import jakarta.servlet.ServletException;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;

public class Util {

    public static void traitementMappingUrls(HashMap<String,Mapping> mappingUrls, String packageName) throws Exception{
        try {
            mappingUrls = new HashMap<String, Mapping>();
            // String packageName = "etu2033.model";
            // String packageName = getServletContext().getInitParameter("packageName");
            URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "//"));
            for (File file : new File(root.getFile().replaceAll("%20", " ")).listFiles()) {
                if (file.getName().contains(".class")) {
                    String className = file.getName().replaceAll(".class$", "");
                    Class<?> cls = Class.forName(packageName + "." + className);
                    for (Method method : cls.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(Url.class)) {
                            mappingUrls.put(method.getAnnotation(Url.class).value(), new Mapping(cls.getName(), method.getName()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
