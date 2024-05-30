package odk.contact.model;

public class Contact {
    private int id;
    private String prenom;
    private String nom;
    private String telephone;
    private String competence;

    // Constructors, getters, and setters
    public Contact() {}

    public Contact(String prenom, String nom, String telephone, String competence) {
        this.prenom = prenom;
        this.nom = nom;
        this.telephone = telephone;
        this.competence = competence;
    }

    public Contact(int id, String prenom, String nom, String telephone, String competence) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.telephone = telephone;
        this.competence = competence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }
}
