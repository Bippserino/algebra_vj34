import model.Polaznik;
import model.ProgramObrazovanja;
import model.Upis;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void dodajPolaznika(String ime, String prezime) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Polaznik p = new Polaznik();
            p.setIme(ime);
            p.setPrezime(prezime);

            session.persist(p);

            tx.commit();
            System.out.println("Polaznik dodan.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dodajProgramObrazovanja(String naziv, int csvet) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            ProgramObrazovanja prog = new ProgramObrazovanja();
            prog.setNaziv(naziv);
            prog.setCSVET(csvet);

            session.persist(prog);

            tx.commit();
            System.out.println("Program obrazovanja dodan.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void upisiPolaznika(Long idPolaznik, Long idProgram) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Polaznik p = session.get(Polaznik.class, idPolaznik);
            ProgramObrazovanja prog = session.get(ProgramObrazovanja.class, idProgram);

            if (p == null || prog == null) {
                System.out.println("Ne postoji polaznik ili program sa zadanim ID-em.");
                return;
            }

            Upis upis = new Upis();
            upis.setPolaznik(p);
            upis.setProgram(prog);

            session.persist(upis);

            tx.commit();
            System.out.println("Polaznik upisan.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void prebaciPolaznika(Long idPolaznik, Long idStariProgram, Long idNoviProgram) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Polaznik p = session.get(Polaznik.class, idPolaznik);
            ProgramObrazovanja stariProg = session.get(ProgramObrazovanja.class, idStariProgram);
            ProgramObrazovanja noviProg = session.get(ProgramObrazovanja.class, idNoviProgram);

            if (p == null || stariProg == null || noviProg == null) {
                System.out.println("Ne postoje uneseni polaznik ili programi.");
                tx.rollback();
                return;
            }

            Upis upis = session.createQuery(
                            "FROM Upis WHERE polaznik.polaznikID = :idPol AND program.programObrazovanjaID = :idProg",
                            Upis.class)
                    .setParameter("idPol", idPolaznik)
                    .setParameter("idProg", idStariProgram)
                    .uniqueResult();

            if (upis == null) {
                System.out.println("Polaznik nije upisan u stari program.");
                tx.rollback();
                return;
            }

            upis.setProgram(noviProg);
            session.update(upis);

            tx.commit();
            System.out.println("Polaznik prebačen.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ispisPolaznikaZaProgram(Long idPrograma) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Upis> upisi = session.createQuery(
                            "FROM Upis WHERE program.programObrazovanjaID = :id", Upis.class)
                    .setParameter("id", idPrograma)
                    .list();

            if (upisi.isEmpty()) {
                System.out.println("Nema polaznika za taj program.");
                return;
            }

            for (Upis upis : upisi) {
                System.out.printf("%s %s | Program: %s | CSVET: %d%n",
                        upis.getPolaznik().getIme(),
                        upis.getPolaznik().getPrezime(),
                        upis.getProgram().getNaziv(),
                        upis.getProgram().getCSVET());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("Odaberi: \n1 - unesi novog polaznika\n2 - unesi novi program obrazovanja" +
                    "\n3 - upiši polaznika na program obrazovanja\n4 - prebaci polaznika iz jednog u drugi" +
                    " program obrazovanja\n5 - ispiši podatke o programu obrazovanja za zadani ID" +
                    "\n6 - izlaz");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                System.out.println("Unesi ime: ");
                String ime = scanner.nextLine();
                System.out.println("Unesi prezime: ");
                String prezime = scanner.nextLine();

                dodajPolaznika(ime, prezime);
            }
            else if (input.equals("2")) {
                System.out.println("Unesi naziv: ");
                String naziv = scanner.nextLine();
                System.out.println("Unesi CSVET: ");
                String CSVET = scanner.nextLine();

                dodajProgramObrazovanja(naziv, Integer.parseInt(CSVET));
            }
            else if (input.equals("3")) {
                System.out.println("Unesi ID polaznika: ");
                String IDPolaznik = scanner.nextLine();
                System.out.println("Unesi ID programa obrazovanja: ");
                String IDProgramObrazovanja = scanner.nextLine();

                upisiPolaznika(Long.parseLong(IDPolaznik), Long.parseLong(IDProgramObrazovanja));
            }
            else if (input.equals("4")) {
                System.out.println("Unesi ID polaznika: ");
                String IDPolaznik = scanner.nextLine();
                System.out.println("Unesi stari ID programa obrazovanja: ");
                String IDStariProgram = scanner.nextLine();
                System.out.println("Unesi novi ID programa obrazovanja: ");
                String IDNoviProgram = scanner.nextLine();

                prebaciPolaznika(Long.parseLong(IDPolaznik), Long.parseLong(IDStariProgram), Long.parseLong(IDNoviProgram));
            }

            else if (input.equals("5")) {
                System.out.println("Unesi ID programa obrazovanja: ");
                String IDProgramaObrazovanja = scanner.nextLine();

                ispisPolaznikaZaProgram(Long.parseLong(IDProgramaObrazovanja));
            }
            else if (input.equals("6")) {
                break;
            }

        }
    }
}
