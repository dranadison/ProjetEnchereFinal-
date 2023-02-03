/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dbtable.Connexion;
import dbtable.DBTable;
import java.sql.Connection;
import token.Now;
import token.Token;

/**
 *
 * @author Fanjava-P14A-V46
 */
public class Utilisateur extends DBTable{
    private int id;
    private String pseudo;
    private String mdp;
    private int utilisateurtype;
    private double solde;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) throws Exception {
        if(solde<0){
            Exception e=new Exception("Montant negatif");
            throw e;
        }
        this.solde = solde;
    }
    
    public Data login() throws Exception{
        Connection c=(new Connexion()).getConnection();
        DBTable[] list=this.find("select * from Utilisateur where pseudo='"+this.getPseudo()+"'", c);
        DBTable[] user=this.find("select * from Utilisateur where pseudo='"+this.getPseudo()+"' and mdp='"+this.getMdp()+"'", c);
        if(list.length==0){
            Exception e=new Exception("Cet utilisateur n'existe pas");
            throw e;
        }
        else{
            if(user.length==0){
                Exception e=new Exception("Mot de passe incorrect");
                throw e;
            }
        }
        Utilisateur u=(Utilisateur)(user[0]);
        Object[] obj=new Object[1];
        Object t=Token.generateToken(u);
        
        int id=u.getId();
        obj[0]=t;
        Data d=new Data(obj);
        c.close();
        return d;
    }
    
    public static void logout(String valToken) throws Exception{
        Connection c=(new Connexion()).getConnection();
        Token token=(Token)((new Token()).find("select * from Token where valeur='"+valToken+"'", c)[0]);
        Now now=(Now)(new Now().find("select * from now() ", c)[0]);
        token.setExpiration(now.getNow());
        token.update(c);
        c.close();
    }
    
    public void recharger(double montant,Recharge r) throws Exception{
        Connection c=(new Connexion()).getConnection();
        DBTable[] list=this.find("select * from Utilisateur where id="+id, c);
        Utilisateur u=(Utilisateur)(list[0]);
        this.setSolde(u.getSolde()+montant);
        this.update(c);
        r.setEtat(1);
        r.update(c);
        c.close();
    }

    public int getUtilisateurtype() {
        return utilisateurtype;
    }

    public void setUtilisateurtype(int utilisateurtype) {
        this.utilisateurtype = utilisateurtype;
    }
    
    public static void inscription(String pseudo,String mdp,String rmdp) throws Exception{
        Utilisateur u=new Utilisateur();
        u.setPseudo(pseudo);
        if(mdp.equals(rmdp)==false){
           throw (new Exception("Entrez mot passe identiques"));
        }
        u.setMdp(mdp);
        u.setUtilisateurtype(0);
        u.setSolde(0);
        Connection c=(new Connexion()).getConnection();
        u.insert(c);
        c.close();
    }

    public Utilisateur recherche() throws Exception{
        Connection c=(new Connexion()).getConnection();
        Utilisateur dt=(Utilisateur) new Utilisateur().find("select * from Utilisateur where id="+Integer.toString(this.getId()),c)[0];
        c.close();
        return dt;
    }
    
public Modele pourcentageParticipation() throws Exception{
        Connection c=new Connexion().getConnection();
        Count count=(Count) ((new Count()).find("select count(*) from offre", c)[0]);
        int cOffre=count.getCount();
        Count count2=(Count) ((new Count()).find("select count(*) from offre join Utilisateur on utilisateur.id=offre.idUtilisateur where (idUtilisateur="+this.getId()+" and pseudo !='admin')", c)[0]);
        int cUtil=count2.getCount();
        c.close();
        double pourcentage=(cUtil*100)/cOffre;
        Modele m=new Modele();
        m.setNom(this.getPseudo());
        m.setValeur(pourcentage);
        return m;
    }
    
    public Modele pourcentageAcquisition() throws  Exception{
        Connection c=new Connexion().getConnection();
        Count count=(Count) ((new Count()).find("select count(*) from enchere where etat =1", c)[0]);
        int cEnchere=count.getCount();
        Count count2=(Count) ((new Count()).find("select count(*) from enchere join Utilisateur on utilisateur.id=enchere.dernierEncheriseur where (dernierEncheriseur="+this.getId()+" and etat=1 and pseudo !='admin')", c)[0]);
        int cUtil=count2.getCount();
        c.close();
        double pourcentage=(cUtil*100)/cEnchere;
        Modele m=new Modele();
        m.setNom(this.getPseudo());
        m.setValeur(pourcentage);
        return m;
    }
    
    public static Modele[] participation() throws Exception{
        Connection c=new Connexion().getConnection();
        DBTable[] util=(new Utilisateur()).find("select * from Utilisateur", c);
        Modele[] m=new Modele[util.length];
        for(int i=0;i<util.length;i++){
            Utilisateur u=(Utilisateur)(util[i]);
            m[i]=u.pourcentageParticipation();
        }
        c.close();
        return Modele.tri(m);
    }
    
    public static Modele[] acquisition() throws Exception{
        Connection c=new Connexion().getConnection();
        DBTable[] util=(new Utilisateur()).find("select * from Utilisateur", c);
        Modele[] m=new Modele[util.length];
        for(int i=0;i<util.length;i++){
            Utilisateur u=(Utilisateur)(util[i]);
            m[i]=u.pourcentageAcquisition();
        }
        c.close();
        return Modele.tri(m);
    }
}
