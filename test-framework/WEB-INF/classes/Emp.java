package models;

import etu2003.annotation.url;
import etu2003.framework.servlet.ModelView;

public class Emp{
    String nom;
    String prenom;

// sprint5: maka modelView anaovana dispatcher
    @url(value="emp-nomComplet")
    public ModelView getNomComplet(){
        ModelView modelView = new ModelView();
        modelView.setUrl("nomComplet.jsp");
        return modelView;
    }

// sprint6: maka donnée anle modelView ho dispatchena
    @url(value="emp-recupDonnee")
    public ModelView getDonnees(){
        ModelView modelView = new ModelView();
        modelView.setUrl("recupDonnee.jsp");
        modelView.addItem("nom","Rakotondrasoa");
        modelView.addItem("prenom","Zety");
        String nomComplet = this.nom +" "+ this.prenom;
        return modelView;
    }
}