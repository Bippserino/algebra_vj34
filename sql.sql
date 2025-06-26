CREATE TABLE Polaznik (
    polaznikID INT PRIMARY KEY IDENTITY(1,1),
    ime NVARCHAR(100) NOT NULL,
    prezime NVARCHAR(100) NOT NULL
);

CREATE TABLE ProgramObrazovanja (
    programObrazovanjaID INT PRIMARY KEY IDENTITY(1,1),
    naziv NVARCHAR(100) NOT NULL,
    CSVET INT NOT NULL
);

CREATE TABLE Upis (
    upisID INT PRIMARY KEY IDENTITY(1,1),
    IDProgramObrazovanja INT NOT NULL,
    IDPolaznik INT NOT NULL,
    FOREIGN KEY (IDProgramObrazovanja) REFERENCES ProgramObrazovanja(programObrazovanjaID),
    FOREIGN KEY (IDPolaznik) REFERENCES Polaznik(polaznikID)
);
