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
 * @author Fanjava
 */
public class Offre extends DBTable {
    private int id;
    private int idEnchere;
    private int idUtilisateur;
    private double montant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEnchere() {
        return idEnchere;
    }

    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String nouveau(Enchere[] tab, Utilisateur u, int idenchere,double montant) throws Exception {
        if(montant>u.getSolde())
        {
            throw new Exception("solde insuffisant");
        }
        double cumule=0;
        for(int i=0;i<tab.length;i++)
        {
            if(tab[i].getDernierEncheriseur()==u.getId())
            {
                cumule=cumule+tab[i].getDernierMontant();
            }
        }
        if(cumule+montant>u.getSolde())
        {
            throw new Exception( "solde bloque par d'autres transactions");
        }
        
        Enchere e=new Enchere();
        for(int i=0;i<tab.length;i++)
        {
            if(tab[i].getId()==idenchere)
            {
                e=tab[i];
            }
        }
        if(montant<=e.getDernierMontant()||montant<e.getPrixminimal()){
            throw new Exception( "montant insuffisant");
        }
        System.out.print(e.getId()+" "+u.getId());
        if(e.getIdUtilisateur()==u.getId()){
            
            throw new Exception( "Vous ne pouvez pas rencherir a votre propre enchere");
        }
        e.setDernierEncheriseur(u.getId());
        e.setDernierMontant(montant);
        Connection c=new Connexion().getConnection();
        e.update(c);
        Offre o=new Offre();
        o.setIdEnchere(idenchere);
        o.setIdUtilisateur(u.getId());
        o.setMontant(montant);
        o.insert(c);
        c.close();
        return "transaction accepter";
    }
    /*******************************************************/
}
