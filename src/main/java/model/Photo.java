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
public class Photo extends DBTable{
    private int id;
    private int idEnchere;
    private String lien;

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

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public void nouveau() throws Exception {
        Connection c=new Connexion().getConnection();
        this.insert(c);
        c.close();
       }

    public DBTable[] recherche() throws Exception {
        Connection c=new Connexion().getConnection();
        DBTable[] tab=this.find("select * from photo where idEnchere="+idEnchere,c);
        c.close();
        return tab;
    }
}
