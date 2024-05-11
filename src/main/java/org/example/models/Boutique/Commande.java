package org.example.models.Boutique;

import org.example.services.Boutique.ServiceProduit;

import java.sql.SQLException;

public class Commande {
    private int idCommande;
    private int idUser;
    private int Somme;
    private Produit produit;
    private int quantite;

    public Commande(int idCommande, int idUser, int somme,Produit produit,int quantite) {
        this.idCommande = idCommande;
        this.idUser = idUser;
        this.Somme = somme;
        this.produit = produit;
        this.quantite = quantite;
    }

    public Commande(int idUser, int somme,Produit produit,int quantite) {
        this.idUser = idUser;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
