CREATE OR ALTER PROCEDURE DodajProgramObrazovanja
	@naziv NVARCHAR(100),
	@CSVET INT
AS
BEGIN
	INSERT INTO ProgramObrazovanja(naziv, CSVET)
	VALUES (@naziv, @CSVET);
END;