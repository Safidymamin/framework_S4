package objet;
import etu2003.framework.Annotation;
public class Emp{

   String nom;
    String prenom;

    @Annotation(url="emp-nomComplet")
    public String getNomComplet(){
        // System.out.println(this.nom +" "+ this.prenom);
        String nomComplet = this.nom +" "+ this.prenom;
        return nomComplet;
    }
}
