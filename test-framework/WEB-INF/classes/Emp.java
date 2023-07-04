package models;

import etu2003.annotation.url;

public class Emp{
    String nom;
    String prenom;

    @url(value="emp-nomComplet")
    public String getNomComplet(){
        // System.out.println(this.nom +" "+ this.prenom);
        String nomComplet = this.nom +" "+ this.prenom;
        return nomComplet;
    }
}