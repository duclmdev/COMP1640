CREATE TABLE Faculties
(
  id          INTEGER PRIMARY KEY AUTOINCREMENT,
  name        TEXT,
  description TEXT
);

CREATE TABLE Users
(
  id              INTEGER PRIMARY KEY AUTOINCREMENT,
  name            TEXT,
  username        TEXT,
  hashed_password TEXT,
  email           TEXT,
  role            INTEGER,
  role_id         INTEGER
);

CREATE TABLE Administrators
(
  id INTEGER PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE Managers
(
  id INTEGER PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE Coordinators
(
  id         INTEGER PRIMARY KEY AUTOINCREMENT,
  faculty_id INTEGER,
  FOREIGN KEY (faculty_id) REFERENCES Faculties (id)

);

CREATE TABLE Students
(
  id         INTEGER PRIMARY KEY AUTOINCREMENT,
  faculty_id INTEGER,
  FOREIGN KEY (faculty_id) REFERENCES Faculties (id)
);

CREATE TABLE Guests
(
  id         INTEGER PRIMARY KEY AUTOINCREMENT,
  faculty_id INTEGER,
  FOREIGN KEY (faculty_id) REFERENCES Faculties (id)
);

CREATE TABLE PublishYears
(
  id              INTEGER PRIMARY KEY AUTOINCREMENT,
  name            TEXT,
  year            INTEGER,
  first_deadline  DATETIME,
  second_deadline DATETIME

);

CREATE TABLE Submissions
(
  id           INTEGER PRIMARY KEY AUTOINCREMENT,
  name         TEXT,
  student_id   INTEGER,
  publish_year INTEGER,
  submit_time  TIME DEFAULT CURRENT_TIMESTAMP,
  chosen       BOOLEAN,
  FOREIGN KEY (student_id) REFERENCES Students (id),
  FOREIGN KEY (publish_year) REFERENCES PublishYears (id)
);

CREATE TABLE Files
(
  id              INTEGER PRIMARY KEY AUTOINCREMENT,
  submission_id   INTEGER,
  disk_location   TEXT UNIQUE,
  hash_code       TEXT,
  hash_expiration DATETIME,
  FOREIGN KEY (submission_id) REFERENCES Submissions (id)
);

CREATE TABLE Comments
(
  coordinator_id INTEGER NOT NULL,
  submission_id  INTEGER NOT NULL,
  subject        TEXT,
  content        TEXT,
  time           DATETIME,
  PRIMARY KEY (coordinator_id, submission_id),
  FOREIGN KEY (coordinator_id) REFERENCES Coordinators (id),
  FOREIGN KEY (submission_id) REFERENCES Submissions (id)
);


--
INSERT INTO Faculties(name, description)
VALUES ('Software Engineering', 'SE description'),
       ('Business Administration', 'BA description'),
       ('Graphic Design', 'GD description');

INSERT INTO Students(id, faculty_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO Users (name, username, hashed_password, email, role, role_id)
VALUES ('An Administrator', 'admin', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 0, 1),
       ('A Manager', 'manager', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 1, 1),
       ('A Coordinator', 'coordinator', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 2, 1),
       ('An SE Student', 'sestudent', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 3, 1),
       ('An BA Student', 'bastudent', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 3, 2),
       ('An GD Student', 'gdstudent', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 3, 3),
       ('A Guest', 'guest', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 4, 1);


UPDATE Users
SET hashed_password = '1000:a4fe87aaab4b4263e0ab02d78660228b:708efd7ff07fa8743029accbd5d' ||
                      'ef0a25f08b8fdf1c68db6af0d7f03c97e82c32331fcd3d6b6784615d9f2ec9d22' ||
                      'e9b460be1778840e1b48fb0c7c95e927478c'
WHERE 1 = 1;

INSERT INTO PublishYears(name, year, first_deadline, second_deadline)
VALUES ('Annual Magazine 2018', 2018, '2018-03-31', '2018-03-20'),
       ('Annual Magazine 2019', 2019, '2019-03-31', '2019-03-20');



