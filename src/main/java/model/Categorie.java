/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dbtable.Connexion;
import dbtable.DBTable;
import java.sql.Connection;

/**
 *
 * @author Fanjava-P14A-V46
 */
public class Categorie extends DBTable{
    private int id;
    private String intitule;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
    
    public static Categorie[] getCategories() throws Exception{
        Connection c=(new Connexion()).getConnection();
        DBTable[] demande=(new Categorie()).find("select * from Categorie ", c);
        c.close();
        Categorie[] cat=new Categorie[demande.length];
        for(int i=0; i<demande.length; i++)
        {
         cat[i]=(Categorie)demande[i];   
        }
        return (cat);
    }
    public Modele pourcentage() throws Exception{
        Connection c=new Connexion().getConnection();
        Count count=(Count) ((new Count()).find("select count(*) from enchere", c)[0]);
        int cEnchere=count.getCount();
        Count count2=(Count) ((new Count()).find("select count(*) from enchere where idCategorie="+this.getId(), c)[0]);
        int cCate=count2.getCount();
        c.close();
        double pourcentage=(cCate*100)/cEnchere;
        Modele m=new Modele();
        m.setNom(this.getIntitule());
        m.setValeur(pourcentage);
        return m;
    }
    public static Modele[] populaire() throws Exception{
        Connection c=new Connexion().getConnection();
        DBTable[] cate=(new Categorie()).find("select * from Categorie", c);
        Modele[] m=new Modele[cate.length];
        for(int i=0;i<cate.length;i++){
            Categorie cat=(Categorie)(cate[i]);
            m[i]=cat.pourcentage();
        }
        c.close();
        return Modele.tri(m);
    }
    public static Modele[] prixMins() throws Exception{
        Connection c=new Connexion().getConnection();
        DBTable[] cate=(new Categorie()).find("select * from Categorie", c);
        Modele[] m=new Modele[cate.length];
        for(int i=0;i<cate.length;i++){
            Categorie cat=(Categorie)(cate[i]);
            m[i]=cat.prixMin();
        }
        c.close();
        return Modele.tri(m);
    }
    
    public static Modele[] prixMaxs() throws Exception{
        Connection c=new Connexion().getConnection();
        DBTable[] cate=(new Categorie()).find("select * from Categorie", c);
        Modele[] m=new Modele[cate.length];
        for(int i=0;i<cate.length;i++){
            Categorie cat=(Categorie)(cate[i]);
            m[i]=cat.prixMax();
        }
        c.close();
        return Modele.tri(m);
    }

        public Modele prixMin() throws Exception{
        Connection c=new Connexion().getConnection();
        Count count2=(Count) ((new Count()).find("select min(dernierMontant) as count from enchere where (idCategorie="+this.getId()+" and etat=1)", c)[0]);
        int cCate=count2.getCount();
        c.close();
        Modele m=new Modele();
        m.setNom(this.getIntitule());
        m.setValeur(cCate);
        return m;
    }
    
     public Modele prixMax() throws Exception{
        Connection c=new Connexion().getConnection();
        Count count2=(Count) ((new Count()).find("select max(dernierMontant) as count from enchere where (idCategorie="+this.getId()+" and etat=1)", c)[0]);
        int cCate=count2.getCount();
        c.close();
        Modele m=new Modele();
        m.setNom(this.getIntitule());
        m.setValeur(cCate);
        return m;
    }

    public static void inserer(String intitule) throws Exception{
        Connection c=new Connexion().getConnection();
        Categorie cat=new Categorie();
        cat.setIntitule(intitule);
        cat.insert(c);
        c.close();
    }

}
