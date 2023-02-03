/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//https://drive.google.com/drive/folders/1j2MRqOp0ubwI7P-VSkFTlDPKynD8n4Md

package model;

import dbtable.Connexion;
import dbtable.DBTable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Fanjava-P14A-V46
 */
public class Enchere extends DBTable{
    private int id;
    private int idUtilisateur;
    private String dateEnchere;
    private String descri;
    private double duree;
    private int idCategorie;
    private double prixminimal;
    private double commission;
    private int etat;
    private int dernierEncheriseur;
    private double dernierMontant;

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

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

    public String getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(String dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public double getDuree() {
        return duree;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getPrixminimal() {
        return prixminimal;
    }

    public void setPrixminimal(double prixminimal) {
        this.prixminimal = prixminimal;
    }

    public int getDernierEncheriseur() {
        return dernierEncheriseur;
    }

    public void setDernierEncheriseur(int dernierEncheriseur) {
        this.dernierEncheriseur = dernierEncheriseur;
    }

    public double getDernierMontant() {
        return dernierMontant;
    }

    public void setDernierMontant(double dernierMontant) {
        this.dernierMontant = dernierMontant;
    }

    public void nouveau() throws Exception {
        Connection c=new Connexion().getConnection();
        Date d=new Date();
        this.setDateEnchere((d.getYear()+1900)+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds());
        this.setCommission(5);
        this.insert(c);
        c.close();
    }
        public static Enchere[] getListEncheres() throws Exception{
       Connection c=(new Connexion()).getConnection();
       DBTable[] demande=(new Enchere()).find("select * from enchere ", c);
       c.close();
       Enchere[] cat=new Enchere[demande.length];
       for(int i=0; i<demande.length; i++)
       {
        cat[i]=(Enchere)demande[i];   
       }
       return (cat);
    }
    
    public static Enchere getListEnchereAnah(int id) throws Exception{
       Connection c=new Connexion().getConnection();
        DBTable[] demande=new Enchere().find("select * from enchere where idutilisateur ="+id+" ",c);
        c.close();
       return ((Enchere)demande[0]);
    }
    
    public static Object testFinEnchere(int id) throws Exception{
       Connection c=new Connexion().getConnection();
       
        DBTable[] enchere=new Enchere().find("select from enchere where id="+id+"",c);
        DBTable[] demande=new Enchere().find("select case when (now()-(dateenchere + '"+((Enchere)(enchere[0])).getDuree()+" hours') >= '00:00:00.000000') then '1'  when (now()-(dateenchere + '"+((Enchere)(enchere[0])).getDuree()+" hours')< '00:00:00.000000') then '0' end as etat from enchere where id="+id+"",c);
        c.close();
       return ((Enchere)demande[0]).getEtat();
    }
    
    public static void modifEtatEnchere(int id) throws Exception{
       Connection c=new Connexion().getConnection();
        Enchere e=new Enchere();
        e.setId(id);
        e.setEtat(1);
        e.update(c);
    }
    public static Enchere[] search(String desc,String categorieid,String date,String prix,String status) throws  Exception {
      
        String sql = "select * from enchere ";
        if((desc != null || date != null || prix != null || categorieid != null || status != null)&&(desc.equals("")==false || date.equals("")==false  || prix.equals("")==false  || categorieid.equals("")==false  || status.equals("")==false )&&(desc.equals("null")==false || date.equals("null")==false  || prix.equals("null")==false  || categorieid.equals("null")==false  || status.equals("null")==false )) {
            sql=sql+"where ";
            ArrayList<String> requests = new ArrayList<>();
            if(desc != null&&desc.equals("")==false&&desc.equals("null")==false ) {
                requests.add("descri like '%"+desc+"%'");
            }
            if(date != null&&date.equals("")==false&&date.equals("null")==false) {
                requests.add("(dateenchere::date)='"+date+"'");
            }
            if(prix != null&&prix.equals("")==false&&prix.equals("null")==false) {
                requests.add("prixminimal="+prix);
            }
            if(categorieid != null&&categorieid.equals("")==false&&categorieid.equals("null")==false) {
                requests.add("idcategorie="+categorieid);
            }
            if(status != null&&status.equals("")==false&&status.equals("null")==false) {
                requests.add("etat="+status);
            }

            for(int i = 0; i < requests.size(); i += 1) {
                if(i == 0) sql += requests.get(i) + " ";
                else sql += " and " + requests.get(i); 
            }

            System.out.println(sql);

        }
        Connection c=new Connexion().getConnection();
        DBTable[] demande=new Enchere().find(sql,c);
        c.close();
        Enchere[] cat=new Enchere[demande.length];
       for(int i=0; i<demande.length; i++)
       {
        cat[i]=(Enchere)demande[i];   
       }
       return (cat);
    }
    public static Enchere getFicheEnchere(int id) throws Exception{
        Connection c=new Connexion().getConnection();
         DBTable[] demande=new Enchere().find("select * from enchere where id ="+id+" ",c);
         c.close();
        return ((Enchere)demande[0]);
     }
    
}
