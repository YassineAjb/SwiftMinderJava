package org.example.models.Boutique;

public class Produit implements Comparable<Produit> {
    private int id;
    private String type;
    private String nomProduit;
    private int prixProduit;
    private String tailleProduit;
    private int quantiteProduit;
    private String image;
    public Produit() {
    }
    public Produit(int id, String type, String nomProduit, int prixProduit, String tailleProduit, int quantiteProduit,String image) {
        this.id = id;
        this.type = type;
        this.nomProduit = nomProduit;
        this.prixProduit = prixProduit;
        this.tailleProduit = tailleProduit;
        this.quantiteProduit = quantiteProduit;
        this.image = image;
    }
    public Produit( String nomProduit, int prixProduit, String tailleProduit,String type, int quantiteProduit,String image) {
        this.type = type;
        this.nomProduit = nomProduit;
        this.prixProduit = prixProduit;
        this.tailleProduit = tailleProduit;
        this.quantiteProduit = quantiteProduit;
        this.image = image;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public int getPrixProduit() {
        return prixProduit;
    }


    public String getTailleProduit() {
        return tailleProduit;
    }


    public int getQuantiteProduit() {
        return quantiteProduit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public void setPrixProduit(int prixProduit) {
        this.prixProduit = prixProduit;
    }

    public void setTailleProduit(String tailleProduit) {
        this.tailleProduit = tailleProduit;
    }

    public void setQuantiteProduit(int quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", nomProduit='" + nomProduit + '\'' +
                ", prixProduit=" + prixProduit +
                ", tailleProduit='" + tailleProduit + '\'' +
                ", quantiteProduit=" + quantiteProduit +
                '}';
    }



    @Override
    public boolean equals(Object o)
    {
       if (this==o)return true;
       if (o==null||getClass()!=o.getClass())return false;
       Produit p = (Produit) o;
       return nomProduit==p.nomProduit&&prixProduit==p.prixProduit&&tailleProduit==p.tailleProduit&&type==p.type;

    }

    @Override
    public int compareTo(Produit autreProduit) {
        return Integer.compare(this.prixProduit, autreProduit.prixProduit);
    }

}
