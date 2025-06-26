CREATE PROCEDURE IspisPolaznikaZaProgram
    @IDProgramObrazovanja INT
AS
BEGIN
    SELECT 
        p.ime, 
        p.prezime, 
        po.naziv, 
        po.csvet
    FROM polaznik AS p
    INNER JOIN upis AS u ON p.polaznikid = u.idpolaznik
    INNER JOIN programobrazovanja AS po ON u.idprogramobrazovanja = po.programobrazovanjaid
    WHERE po.programobrazovanjaid = @IDProgramObrazovanja;
END;
