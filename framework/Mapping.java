package etu2003.framework;

public class Mapping{
    String className = "default";
    String method = "default";

    public Mapping(String className, String method) {
        this.className = className;
        this.method = method;
    }

    public String getClassName() {
        return this.className;
    }
    public String getMethod(){
        return this.method;
    }

    public Mapping(){}

}