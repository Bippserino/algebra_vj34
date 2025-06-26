CREATE OR ALTER PROCEDURE DodajPolaznika
    @ime NVARCHAR(100),
    @prezime NVARCHAR(100)
AS
BEGIN
    INSERT INTO Polaznik (ime, prezime)
    VALUES (@ime, @prezime);
END;

