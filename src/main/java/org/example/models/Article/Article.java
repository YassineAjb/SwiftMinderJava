package org.example.models.Article;
public class Article {
    private int idArticle;
    private String TitreArticle ;
    private String ContenuArticle ;
    private String ImageArticle;
    private int IdJournaliste;

    public Article(String titreArticle, String contenuArticle, String imageArticle, int idJournaliste) {
        TitreArticle = titreArticle;
        ContenuArticle = contenuArticle;
        ImageArticle = imageArticle;
        IdJournaliste = idJournaliste;
    }
    public Article(String titreArticle, String contenuArticle) {
        TitreArticle = titreArticle;
        ContenuArticle = contenuArticle;

    }


    public Article(int idArticle, String titreArticle, String contenuArticle, String imageArticle, int idJournaliste) {
        this.idArticle = idArticle;
        TitreArticle = titreArticle;
        ContenuArticle = contenuArticle;
        ImageArticle = imageArticle;
        IdJournaliste = idJournaliste;
    }

    public Article() {
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }
    public String getTitreArticle() {
        return TitreArticle ;
    }
    public void setTitreArticle(String titreArticle) {
        TitreArticle = titreArticle;
    }

    public String getContenuArticle() {
        return ContenuArticle;
    }

    public void setContenuArticle(String contenuArticle) {
        ContenuArticle = contenuArticle;
    }

    public String getImageArticle() {
        return ImageArticle;
    }

    public void setImageArticle(String imageArticle) {
        ImageArticle = imageArticle;
    }

    @Override


    public String toString() {
        return "Article{" +
                "idArticle=" + idArticle +
                ", titreArticle='" + TitreArticle+ '\'' +
                ", ContenuArticle='" + ContenuArticle + '\'' +
                ", ImageArticle='" + ImageArticle + '\'' +
                '}';
    }

    public int getIdJournaliste() {
        return IdJournaliste;
    }



}


