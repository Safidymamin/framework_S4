package model;

import etu2003.annotation.Url;

public class Emp{
    String nom;
    String prenom;

    @Url(value="emp-nomComplet")
    public String getNomComplet(){
        // System.out.println(this.nom +" "+ this.prenom);
        String nomComplet = this.nom +" "+ this.prenom;
        return nomComplet;
    }
}