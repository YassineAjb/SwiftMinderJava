package org.example.models.Boutique;

import org.example.models.User.User;
import org.example.services.Boutique.ServiceProduit;

import java.sql.SQLException;

public class Commande {
    private int idCommande;
    private User user;
    private int Somme;
    private Produit produit;
    private int quantite;

    public Commande(int idCommande, User user, int somme,Produit produit,int quantite) {
        this.idCommande = idCommande;
        this.user = user;
        this.Somme = somme;
        this.produit = produit;
        this.quantite = quantite;
    }

    public Commande(User user, int somme,Produit produit,int quantite) {
        this.user = user;
        this.Somme = somme;
        this.produit = produit;
        this.quantite = quantite;
    }
    public Commande()
    {

    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public ServiceProduit getServiceProduit() {
        return serviceProduit;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSomme() {
        return Somme;
    }

    public void setSomme(int somme) {
        Somme = somme;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    private final ServiceProduit  serviceProduit = new ServiceProduit();

    @Override
    public String toString() {
        try {
            return
                    "Produit:" + serviceProduit.afficherUnProduit(produit.getId())+

                    "    Somme=" + Somme ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
