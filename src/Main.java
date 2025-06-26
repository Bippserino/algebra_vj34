import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Main {
    private static Connection connection;

    public static void dodajPolaznika(String ime, String prezime) throws SQLException {
        CallableStatement stmt = connection.prepareCall("{call DodajPolaznika(?, ?)}");
        stmt.setString(1, ime);
        stmt.setString(2, prezime);

        stmt.execute();
        System.out.println("Polaznik dodan.");
        stmt.close();
    }

    public static void dodajProgramObrazovanja(String naziv, String CSVET) throws SQLException {
        CallableStatement stmt = connection.prepareCall("{call DodajProgramObrazovanja(?, ?)}");
        stmt.setString(1, naziv);
        stmt.setString(2, CSVET);

        stmt.execute();
        System.out.println("Program obrazovanja dodan.");
        stmt.close();
    }

    public static void upisiPolaznika(String IDPolaznik, String IDProgramObrazovanja) throws SQLException {
        CallableStatement stmt = connection.prepareCall("{call UpisiPolaznika(?, ?)}");
        stmt.setString(1, IDPolaznik);
        stmt.setString(2, IDProgramObrazovanja);

        stmt.execute();
        System.out.println("Polaznik upisan.");
        stmt.close();
    }

    public static void prebaciPolaznika(String IDPolaznik, String IDStariProgram, String IDNoviProgram) throws SQLException {
        CallableStatement stmt = null;
        try {
            connection.setAutoCommit(false);

            stmt = connection.prepareCall("{call PrebaciPolaznika(?, ?, ?)}");
            stmt.setString(1, IDPolaznik);
            stmt.setString(2, IDStariProgram);
            stmt.setString(3, IDNoviProgram);

            stmt.execute();
            System.out.println("Polaznik prebačen.");
            connection.commit();

            stmt.close();
        }
        catch (SQLException e) {
            connection.rollback();
        }
        if (stmt != null) {
            stmt.close();
        }
    }

    public static void ispisPolaznikaZaProgram(String IDProgramaObrazovanja) throws SQLException {
        CallableStatement stmt = connection.prepareCall("{call IspisPolaznikaZaProgram(?)}");
        stmt.setString(1, IDProgramaObrazovanja);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            String nazivPrograma = rs.getString("naziv");
            int csvet = rs.getInt("csvet");

            System.out.println(ime + " " + prezime + " | Program: " + nazivPrograma + " | CSVET: " + csvet);
        }

        rs.close();
        stmt.close();
    }



    public static void main(String[] args) {
        DataSource dataSource = createDataSource();
        Scanner scanner = new Scanner(System.in);
        String input;

        try {
            connection = dataSource.getConnection();
            System.out.println("Uspješno spojeni na bazu podataka!");

            while (true) {
                System.out.println("Odaberi: \n1 - unesi novog polaznika\n2 - unesi novi program obrazovanja" +
                        "\n3 - upiši polaznika na program obrazovanja\n4 - prebaci polaznika iz jednog u drugi" +
                        " program obrazovanja\n5 - ispiši podatke o programu obrazovanja za zadani ID" +
                        "\n6 - izlaz");
                input = scanner.nextLine();
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

                    dodajProgramObrazovanja(naziv, CSVET);
                }
                else if (input.equals("3")) {
                    System.out.println("Unesi ID polaznika: ");
                    String IDPolaznik = scanner.nextLine();
                    System.out.println("Unesi ID programa obrazovanja: ");
                    String IDProgramObrazovanja = scanner.nextLine();

                    upisiPolaznika(IDPolaznik, IDProgramObrazovanja);
                }
                else if (input.equals("4")) {
                    System.out.println("Unesi ID polaznika: ");
                    String IDPolaznik = scanner.nextLine();
                    System.out.println("Unesi stari ID programa obrazovanja: ");
                    String IDStariProgram = scanner.nextLine();
                    System.out.println("Unesi novi ID programa obrazovanja: ");
                    String IDNoviProgram = scanner.nextLine();

                    prebaciPolaznika(IDPolaznik, IDStariProgram, IDNoviProgram);
                }

                else if (input.equals("5")) {
                    System.out.println("Unesi ID programa obrazovanja: ");
                    String IDProgramaObrazovanja = scanner.nextLine();

                    ispisPolaznikaZaProgram(IDProgramaObrazovanja);
                }
                else if (input.equals("6")) {
                    break;
                }
            }

        } catch (SQLException e) {
            System.err.println("Greška prilikom spajanja na bazu podataka:");
            e.printStackTrace();
        }
        finally {
            scanner.close();
        }
    }

    private static DataSource createDataSource() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setEncrypt(false);
        dataSource.setServerName("BOJAN\\SQLEXPRESS");
        dataSource.setPortNumber(15234);
        dataSource.setDatabaseName("JavaAdv");
        dataSource.setUser("sa");
        dataSource.setPassword("SQL");

        return dataSource;
    }
}