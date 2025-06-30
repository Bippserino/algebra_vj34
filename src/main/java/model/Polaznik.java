package model;

import jakarta.persistence.*;

@Entity
@Table(name = "Polaznik")
public class Polaznik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "polaznikID")
    private Long polaznikID;

    private String ime;

    private String prezime;

    public Polaznik() {

    }

    public Polaznik(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
    }

    public Long getPolaznikID() {
        return polaznikID;
    }

    public void setPolaznikID(Long polaznikID) {
        this.polaznikID = polaznikID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
