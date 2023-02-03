package com.example.TPSpring;

import dbtable.DBTable;
import java.util.Collections;
import model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import token.*;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@RestController  
public class TpSpringApplication {

    public static void main(String[] args) {
            SpringApplication.run(TpSpringApplication.class, args);
    }
/************************************************************/
//Utilisateur
    @CrossOrigin @GetMapping("/login")
    public Object login(@RequestParam(value = "pseudo") String pseudo,@RequestParam(value = "mdp") String mdp) throws Exception {
        try{
            Utilisateur u=new Utilisateur();
            u.setPseudo(pseudo);
            u.setMdp(mdp);
            return u.login();
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/inscription")
    public Object inscription(@RequestParam(value = "pseudo") String pseudo,@RequestParam(value = "mdp") String mdp,@RequestParam(value = "rmdp") String rmdp) throws Exception {
        try{
            Utilisateur.inscription(pseudo, mdp, rmdp);
            return new Data("OK");
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/infoUtilisateur")
    public Object infoUtilisateur(@RequestParam(value = "idUtilisatuer") String id) throws Exception {
        try{
            Utilisateur u=new Utilisateur();
            u.setId(Integer.parseInt(Token.decode(id)));
            return u.recherche();
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    } 
    @CrossOrigin @GetMapping("/acquisition")
    public Object acquisition() throws Exception {
        try{
            return Utilisateur.acquisition();
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/participation")
    public Object participation() throws Exception {
        try{
            return Utilisateur.participation();
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
/************************************************************/
//Rechage Compte 
    @CrossOrigin @GetMapping("/recharger")
    public Object recharger(@RequestParam(value = "id") int id) throws Exception {
        try{
            Recharge r=Recharge.getById(id);
            Utilisateur u=new Utilisateur();
            u.setId(r.getIdUtilisateur());
            u.recharger(r.getSolde(),r);
            
            return new Data("OK");
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/demandeRecharge")
    public Object demandeRecharge(@RequestParam(value = "id") String id,@RequestParam(value = "montant") double montant) throws Exception {
        try{
            Recharge.demandeRecharge(Integer.parseInt(Token.decode(id)), montant);
            return new Data("OK");
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/recharges")
    public Object recharges() throws Exception {
        try{
            return Recharge.recharges();
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
/************************************************************/
//Categorie
    @CrossOrigin @GetMapping("/categories")
    public Object getCategories() throws Exception {
        try{
            Categorie[] tab=new Categorie().getCategories();
            return new Data(tab);
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/populaire")
    public Object populaire() throws Exception {
        try{
            return Categorie.populaire();
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/categorie")
    public Object insertcategories(@RequestParam(value = "nom") String nom) throws Exception {
        try{
            Categorie c=new Categorie();
            c.inserer(nom);
            return "ok";
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/prixMax")
    public Object prixMax() throws Exception {
        try{
            return Categorie.prixMaxs();
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/prixMin")
    public Object prixMin() throws Exception {
        try{
            return Categorie.prixMins();
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
/************************************************************/
//Photos   
    @CrossOrigin @PostMapping("/photo")
    public Object photo( @RequestParam(value = "idEnchere") int idEnchere,@RequestParam(value = "lien") String lien) throws Exception {
        
        Photo p=new Photo();
        p.setIdEnchere(idEnchere);
        p.setLien(lien);
        p.nouveau();
        try{
            return new Data("photo inserer");
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/photos")
    public Object getphotos(@RequestParam(value = "idEnchere") String idEnchere) throws Exception {
        DBTable[] tab=new Photo().recherche(idEnchere);
        try{
            return new Data(tab);
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
/************************************************************/
//Enchere
    @CrossOrigin @GetMapping("/nouveauenchere")
    public Object nouveauenchere(@RequestParam(value = "idUtilisateur") String idUtilisateur,@RequestParam(value = "idCategorie") int idCategorie,@RequestParam(value = "descri") String descri,@RequestParam(value = "durre") double durre,@RequestParam(value = "prixminimal") double prixminimal) throws Exception {
        Enchere e= new Enchere();
        e.setIdUtilisateur(Integer.parseInt(Token.decode(idUtilisateur)));
        e.setIdCategorie(idCategorie);
        e.setDescri(descri);
        e.setDuree(durre);
        e.setPrixminimal(prixminimal);
        e.nouveau();
        try{
            return new Data("enchere inserer");
        }
        catch(Exception ex)
        {
            return new Erreur("500",ex.getMessage());
        }
    }    
    @CrossOrigin @GetMapping("/listeencheres")
    public Object getListEncheres() throws Exception {
        try{
            Enchere[] tab=new Enchere().getListEncheres();
            return tab;
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }	
    @CrossOrigin @GetMapping("/encheresMisyId")
    public Object getListEnchereAnah(@RequestParam(value = "id") String id) throws Exception {
        try{
              Enchere tab=new Enchere().getListEnchereAnah(Integer.parseInt(Token.decode(id)));
            return new Data(tab);
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/modifEtatEnchere")
    public Object modifEtatEnchere(@RequestParam(value = "id") int id) throws Exception {

        new Enchere().modifEtatEnchere(id);
       
        return new Data("ok");
    }
    @CrossOrigin @GetMapping("/testfin")
    public Object testFinEnchere(@RequestParam(value = "id") int id) throws Exception {
        try{
              Object tab=new Enchere().testFinEnchere(id);
            return new Data(tab);
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
    @CrossOrigin @GetMapping("/recherche")
    public Object recherche(@RequestParam(value = "idCategorie") String idCategorie,@RequestParam(value = "descri") String descri,@RequestParam(value = "date") String date,@RequestParam(value = "prixminimal") String prixminimal,@RequestParam(value = "status") String status)throws Exception {
     Enchere[] tab=new Enchere().search(descri, idCategorie, date, prixminimal, status);
        
        try{
            return new Data(tab);
        }
        catch(Exception ex)
        {
            return new Erreur("500",ex.getMessage());
        }
    }
    
    @CrossOrigin @GetMapping("/enchere")
    public Object ficheEnchere(@RequestParam(value = "id") int id) throws Exception {
        try{
            Enchere tab=new Enchere().getFicheEnchere(id);
            return new Data(tab);
        }
        catch(Exception e)
        {
            return new Erreur("500",e.getMessage());
        }
    }
/************************************************************/
//notifiaction
    @CrossOrigin @GetMapping("/envoiNotif")
    public Object envoiNotif(@RequestParam(value = "id") int id,@RequestParam(value = "idU") int idU,@RequestParam(value = "texte") String texte,@RequestParam(value = "typenotif") int typenotif) throws Exception {
        Notificationapp e=new Notificationapp();
        e.envoiNotif(id,idU,texte,typenotif);
        return new Data("ok");
    }
/************************************************************/
//offre
    @CrossOrigin @GetMapping("/nouvelleoffre")
    public Object nouvelleoffre(@RequestParam(value = "idenchere") int idenchere,@RequestParam(value = "idutilisateur") String token,@RequestParam(value = "montant") double montant) throws Exception {
        try{
            Enchere[] tab=(Enchere[])getListEncheres();
            Utilisateur u=(Utilisateur)infoUtilisateur(token);
            Offre o=new Offre();
            return o.nouveau(tab,u,idenchere,montant);
        }
        catch(Exception e){
            return new Erreur("500",e.getMessage()); 
        }
    }
    
    @CrossOrigin @GetMapping("/logout")
    public String logout(@RequestParam(value = "token") String token) throws Exception {
        Utilisateur.logout(token);
        return "succes";
    }
}
