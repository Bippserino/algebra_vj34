package model;

import jakarta.persistence.*;

@Entity
@Table(name = "Upis")
public class Upis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long upisID;

    @ManyToOne
    @JoinColumn(name = "IDPolaznik", referencedColumnName = "polaznikID")
    private Polaznik polaznik;


    @ManyToOne
    @JoinColumn(name = "IDProgramObrazovanja", referencedColumnName = "ProgramObrazovanjaID")
    private ProgramObrazovanja program;

    public Upis() {

    }

    public Upis(Polaznik polaznik, ProgramObrazovanja program) {
        this.polaznik = polaznik;
        this.program = program;
    }

    public Long getUpisID() {
        return upisID;
    }

    public void setUpisID(Long upisID) {
        this.upisID = upisID;
    }

    public Polaznik getPolaznik() {
        return polaznik;
    }

    public void setPolaznik(Polaznik polaznik) {
        this.polaznik = polaznik;
    }

    public ProgramObrazovanja getProgram() {
        return program;
    }

    public void setProgram(ProgramObrazovanja program) {
        this.program = program;
    }
}
