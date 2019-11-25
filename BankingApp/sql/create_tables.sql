CREATE TABLE admins(

	id		INT				PRIMARY KEY,
	name	VARCHAR2(20)	NOT NULL								UNIQUE,
	passwd	VARCHAR2(20)	NOT NULL								UNIQUE

);

CREATE TABLE users(

	id		INT				PRIMARY KEY,
	name 	VARCHAR2(20)	NOT NULL								UNIQUE,
	passwd	VARCHAR2(20)	NOT NULL								UNIQUE

);

CREATE TABLE accounts(

	id		INT				PRIMARY KEY,
	owner	INT				NOT NULL								REFERENCES users(id),
	bal		FLOAT			NOT NULL,
	acc_t	VARCHAR2(8)		NOT NULL

);

CREATE TABLE transactions(

	id		INT				PRIMARY KEY,
	acc		INT				NOT NULL								REFERENCES accounts(id),
	bal		FLOAT			NOT NULL,
	trn_t	VARCHAR2(8)		NOT NULL,
	sec		INT				NOT NULL,
	nano	INT				NOT NULL

);
commit;