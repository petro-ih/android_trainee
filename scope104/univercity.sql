CREATE TABLE "faculty" (
	"id"	int NOT NULL,
	"name"	nvarchar(20) NOT NULL UNIQUE,
	PRIMARY KEY("id")
);

CREATE TABLE "discipline" (
	"id"	int NOT NULL,
	"name"	nvarchar(100) NOT NULL UNIQUE,
	PRIMARY KEY("id")
);

CREATE TABLE "department" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT UNIQUE,
	"faculty_id"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("faculty_id") REFERENCES "faculty"("id")
);

CREATE TABLE "teacher" (
	"id"	INTEGER NOT NULL UNIQUE,
	"first_name"	TEXT NOT NULL,
	"second_name"	TEXT NOT NULL,
	"patronymic"	TEXT NOT NULL,
	"department_id"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("department_id") REFERENCES "department"("id")
);

CREATE TABLE "nagruzka" (
	"id"	INTEGER NOT NULL UNIQUE,
	"teacher_id"	INTEGER NOT NULL,
	"discipline_id"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("teacher_id") REFERENCES "teacher"("id"),
	FOREIGN KEY("discipline_id") REFERENCES "discipline"("id")
);

CREATE TABLE "group" (
	"id"	INTEGER NOT NULL UNIQUE,
	"name"	INTEGER NOT NULL,
	"faculty_id"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("faculty_id") REFERENCES "faculty"("id")
);

CREATE TABLE "student" (
	"id"	INTEGER NOT NULL UNIQUE,
	"first_name"	TEXT NOT NULL,
	"second_name"	TEXT NOT NULL,
	"patronymic"	TEXT NOT NULL,
	"date_of_birth"	INTEGER NOT NULL,
	"group_id"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "mark" (
	"id"	INTEGER NOT NULL UNIQUE,
	"date"	INTEGER NOT NULL,
	"mark"	INTEGER NOT NULL,
	"student_id"	INTEGER NOT NULL,
	"discipline_id"	INTEGER NOT NULL,
	FOREIGN KEY("student_id") REFERENCES "student"("id"),
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("discipline_id") REFERENCES "discipline"("id")
);
