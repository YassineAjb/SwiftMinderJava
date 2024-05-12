package org.example.models.Employe;

public class Joueur {
    private int id;
    private String Position;
    private int Hauteur;
    private int Poids;
    private String Piedfort;
    private String Nom;
    private String Prenom;
    private int Age;
    private String imagePath;
    private String Link;
    private int shirtnum;

    public Joueur() {
    }

    public Joueur(int id, String position, int hauteur, int poids, String piedfort, String nom, String prenom, int Age) {
        this.id = id;
        Position = position;
        Hauteur = hauteur;
        Poids = poids;
        Piedfort = piedfort;
        Nom = nom;
        Prenom = prenom;
        this.Age = Age;
    }

    public Joueur(String position, int hauteur, int poids, String piedfort, String nom, String prenom, int Age) {
        Position = position;
        Hauteur = hauteur;
        Poids = poids;
        Piedfort = piedfort;
        Nom = nom;
        Prenom = prenom;
        this.Age = Age;
    }
    public Joueur(String position, int hauteur, int poids, String piedfort, String nom, String prenom, int Age,String imagePath) {
        Position = position;
        Hauteur = hauteur;
        Poids = poids;
        Piedfort = piedfort;
        Nom = nom;
        Prenom = prenom;
        this.Age = Age;
        this.imagePath = imagePath;
    }
    public Joueur(int id, String position, int hauteur, int poids, String piedfort, String nom, String prenom, int Age,String imagePath) {
        this.id = id;
        Position = position;
        Hauteur = hauteur;
        Poids = poids;
        Piedfort = piedfort;
        Nom = nom;
        Prenom = prenom;
        this.Age = Age;
        this.imagePath = imagePath;
    }

    public Joueur(String position, int hauteur, int poids, String piedfort, String nom, String prenom, int age, String imagePath, String link) {
        Position = position;
        Hauteur = hauteur;
        Poids = poids;
        Piedfort = piedfort;
        Nom = nom;
        Prenom = prenom;
        Age = age;
        this.imagePath = imagePath;
        Link = link;
    }

    public Joueur(int id, String position, int hauteur, int poids, String piedfort, String nom, String prenom, int age, String imagePath, String link) {
        this.id = id;
        Position = position;
        Hauteur = hauteur;
        Poids = poids;
        Piedfort = piedfort;
        Nom = nom;
        Prenom = prenom;
        Age = age;
        this.imagePath = imagePath;
        Link = link;
    }

    public Joueur(int id, String position, int hauteur, int poids, String piedfort, String nom, String prenom, int age, String imagePath, String link, int shirtnum) {
        this.id = id;
        Position = position;
        Hauteur = hauteur;
        Poids = poids;
        Piedfort = piedfort;
        Nom = nom;
        Prenom = prenom;
        Age = age;
        this.imagePath = imagePath;
        Link = link;
        this.shirtnum = shirtnum;
    }

    public Joueur(String position, int hauteur, int poids, String piedfort, String nom, String prenom, int age, String imagePath, String link, int shirtnum) {
        Position = position;
        Hauteur = hauteur;
        Poids = poids;
        Piedfort = piedfort;
        Nom = nom;
        Prenom = prenom;
        Age = age;
        this.imagePath = imagePath;
        Link = link;
        this.shirtnum = shirtnum;
    }

    public String getPosition() {
        return Position;
    }
    public void setPosition(String position) {
        Position = position;
    }
    public int getHauteur() {
        return Hauteur;
    }
    public void setHauteur(int hauteur) {
        Hauteur = hauteur;
    }
    public int getPoids() {
        return Poids;
    }
    public void setPoids(int poids) {
        Poids = poids;
    }
    public String getPiedfort() {
        return Piedfort;
    }
    public void setPiedfort(String piedfort) {
        Piedfort = piedfort;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getLink() {
        return Link;
    }
    public void setLink(String link) {
        Link = link;
    }
    public int getShirtnum() {
        return shirtnum;
    }
    public void setShirtnum(int shirtnum) {
        this.shirtnum = shirtnum;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "id=" + id +
                ", Position='" + Position + '\'' +
                ", Hauteur=" + Hauteur +
                ", Poids=" + Poids +
                ", Piedfort='" + Piedfort + '\'' +
                ", Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", Age=" + Age +
                ", imagePath='" + imagePath + '\'' +
                ", Link='" + Link + '\'' +
                ", shirtnum=" + shirtnum +
                '}';
    }
}
