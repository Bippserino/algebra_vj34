package model;

import jakarta.persistence.*;

@Entity
@Table(name = "ProgramObrazovanja")
public class ProgramObrazovanja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programObrazovanjaID;

    private String naziv;

    private int CSVET;

    public ProgramObrazovanja() {
    }

    public ProgramObrazovanja(String naziv, int CSVET) {
        this.naziv = naziv;
        this.CSVET = CSVET;
    }

    public Long getProgramObrazovanjaID() {
        return programObrazovanjaID;
    }

    public void setProgramObrazovanjaID(Long programObrazovanjaID) {
        programObrazovanjaID = programObrazovanjaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getCSVET() {
        return CSVET;
    }

    public void setCSVET(int CSVET) {
        this.CSVET = CSVET;
    }
}
