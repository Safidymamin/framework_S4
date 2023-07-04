package etu2003.framework.servlet;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

public class ModelView{
    String url;
    HashMap<String,Object> data = new HashMap<>();

    public void addItem(String key,Object value){
        this.data.put(key, value);
    }

    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
        public HashMap<String,Object> getData(){
        return this.data;
    }
    public void setData(HashMap<String,Object> data){
        this.data = data;
    }
}