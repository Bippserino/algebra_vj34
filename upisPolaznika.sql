CREATE PROCEDURE UpisiPolaznika
    @IDPolaznik INT,
    @IDProgramObrazovanja INT
AS
BEGIN
    INSERT INTO upis (idpolaznik, idprogramobrazovanja)
    VALUES (@idPolaznik, @idProgramObrazovanja);
END;