/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2003.framework;

/**
 *
 * @author Safidy Maminirina
 */
public class Mapping {
    String className;
    String method;
    
    public String getclassName(){
        return this.className;
    }
    
    public void setclassName(String name){
        this.className=name;
    }
    public String getmethod(){
        return this.method;
    }
    public void setmethod(String method){
        this.method=method;
    }
     public Mapping(String className,String method){
         this.setclassName(className);
         this.setmethod(method);
     }
    
    
}
