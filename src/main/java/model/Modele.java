/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Vector;

/**
 *
 * @author Fanjava
 */
public class Modele {
    private String nom;
    private double valeur;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
    
    public static int getMax(Vector m){
        double max=0;
        int mMax=0;
        for(int i=0;i<m.size();i++){
            Modele modele=((Modele)(m.get(i)));
            if(modele.getValeur()>=max){
                mMax=i;
                max=modele.getValeur();
            }
        }
        return mMax;
    }
    
    public static Modele[] tri(Modele[] m){
        Vector v=new Vector();
        for(int i=0;i<m.length;i++){
            v.add(m[i]);
        }
        Vector v2=new Vector();
        while(v.size()!=0){
            int max=getMax(v);
            v2.add(v.get(max));
            v.remove(max);
        }
        Modele[] m2=new Modele[v2.size()];
        for(int i=0;i<m2.length;i++){
            Modele md=(Modele)(v2.get(i));
            m2[i]=md;
        }
        return m2;
    }
}
