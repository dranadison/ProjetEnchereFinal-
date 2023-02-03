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
public class Notificationapp extends DBTable{
    private int id;
    private int idUtilisateur;
    private String texte;
    private int typeNotification;

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

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public int getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(int typeNotification) {
        this.typeNotification = typeNotification;
    }
        public static void envoiNotif(int id,int idU, String texte,int typenotif) throws Exception{
       
        Notificationapp n=new Notificationapp();
        n.setId(id);
        n.setIdUtilisateur(idU);
        n.setTexte(texte);
        n.setTypeNotification(typenotif);
        
        Connection c=(new Connexion()).getConnection();
        n.insert(c);
        c.close();
    }
}
