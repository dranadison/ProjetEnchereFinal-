/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import dbtable.Connexion;
import dbtable.DBTable;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.Utilisateur;
import java.sql.Connection;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Fanjava-P14A-V46
 */
public class Token extends DBTable{

    private int id;
    private int idUtilisateur;
    private String valeur;
    private String expiration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
    


    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }    

    public static String generateToken(Utilisateur user) throws Exception {
        String jwtToken="";
        jwtToken = Jwts.builder().setSubject(user.getId()+"").setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "enchere").compact();
        Connection c=(new Connexion()).getConnection();
        Token t=new Token();
        Date d=new Date();
        d.setHours(d.getHours()+2);
        String expi=(d.getYear()+1900)+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
        t.setValeur(jwtToken);
        t.setExpiration(expi);
        t.setIdUtilisateur(user.getId());
        t.insert(c);
        c.close();
        return jwtToken;
    }
    
    public static String decode(String token){
        Base64.Decoder decoder=Base64.getUrlDecoder();
        String[] chunks=token.split("\\.");
        String[] one=new String(decoder.decode(chunks[1])).split(",");
        String[] two=one[0].split(":");
        String val=two[1].replace("\"", "");
        return val;
    }
    
}
