create table StreamTeaAccount(
    TeaId VARCHAR(4) PRIMARY KEY,
	TeaUid VARCHAR(100),
	TeaPassword VARCHAR(100) NOT NULL
)

create table StreamRoom(
	TeaId VARCHAR(4) UNIQUE,
	RoomNumber VARCHAR(30),
	PRIMARY KEY(TeaId, RoomNumber),
	ClassId VARCHAR(36)
);