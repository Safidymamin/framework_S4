/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.util.Vector;
import utils.Annotation;

/**
 *
 * @author Safidy Maminirina
 */

    
public class Emp {
    
    @Annotation(url="/framework/jsp/parler")
    public void parler(){
    }
    
    @Annotation(url="/framework/jsp/get-emp")
    public Vector getAll(){
        Vector<String> vs = new Vector<>();
        vs.add("ok");
        return vs;
    }
    
    @Annotation(url="/framework/jsp/add-emp")
    public void insert(){
        
    }
}

