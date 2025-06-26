CREATE PROCEDURE PrebaciPolaznika
    @idPolaznik INT,
    @idStariProgram INT,
    @idNoviProgram INT
AS
BEGIN
    UPDATE upis
    SET idprogramobrazovanja = @idNoviProgram
    WHERE idpolaznik = @idPolaznik AND idprogramobrazovanja = @idStariProgram;
END;
