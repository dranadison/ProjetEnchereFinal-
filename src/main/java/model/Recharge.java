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
public class Recharge extends DBTable{
    private int id;
    private int idUtilisateur;
    private double solde;
    private int etat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
    public static void demandeRecharge(int idUtilisateur,double montant) throws Exception{
        Recharge r=new Recharge();
        r.setIdUtilisateur(idUtilisateur);
        r.setSolde(montant);
        r.setEtat(0);
        Connection c=(new Connexion()).getConnection();
        r.insert(c);
        c.close();
    }
    
    public static Recharge getById(int id) throws Exception{
        Connection c=(new Connexion()).getConnection();
        DBTable[] demande=(new Recharge()).find("select * from recharge where id="+id, c);
        c.close();
        return (Recharge)(demande[0]);
    }
    public static DBTable[] recharges() throws Exception{
        Connection c=new Connexion().getConnection();
        DBTable[] tab=new Recharge().find("select * from Recharge where etat=0",c);
        c.close();
        return tab;
    }
}
