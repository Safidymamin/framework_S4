package models;

import etu2003.annotation.url;
import etu2003.framework.servlet.ModelView;

public class Emp{
    String nom;
    String prenom;

    @url(value="emp-nomComplet")
    public ModelView getNomComplet(){
        ModelView modelView = new ModelView();
        modelView.setUrl("nomComplet.jsp");
        String nomComplet = this.nom +" "+ this.prenom;
        return modelView;
    }
}