CREATE TABLE Faculties (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    name        TEXT,
    description TEXT
);

CREATE TABLE Users (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    name            TEXT,
    username        TEXT,
    hashed_password TEXT,
    email           TEXT,
    role            INTEGER,
    role_id         INTEGER
);

CREATE TABLE Administrators (
    id INTEGER PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE Managers (
    id INTEGER PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE Coordinators (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    faculty_id INTEGER,
    FOREIGN KEY (faculty_id) REFERENCES Faculties (id)

);

CREATE TABLE Students (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    faculty_id INTEGER,
    rollnumber TEXT UNIQUE,
    FOREIGN KEY (faculty_id) REFERENCES Faculties (id)
);

CREATE TABLE Guests (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    faculty_id INTEGER,
    FOREIGN KEY (faculty_id) REFERENCES Faculties (id)
);

CREATE TABLE PublishYears (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    name            TEXT,
    year            INTEGER,
    first_deadline  DATETIME,
    second_deadline DATETIME

);

CREATE TABLE Submissions (
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    name         TEXT,
    student_id   INTEGER,
    publish_year INTEGER,
    submit_time  TIME DEFAULT CURRENT_TIMESTAMP,
    chosen       BOOLEAN,
    FOREIGN KEY (student_id) REFERENCES Students (id),
    FOREIGN KEY (publish_year) REFERENCES PublishYears (id)
);

CREATE TABLE Files (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    submission_id   INTEGER,
    disk_location   TEXT UNIQUE,
    hash_code       TEXT,
    hash_expiration DATETIME,
    FOREIGN KEY (submission_id) REFERENCES Submissions (id)
);

CREATE TABLE Comments (
    coordinator_id INTEGER NOT NULL,
    submission_id  INTEGER NOT NULL,
    time           DATETIME,
    subject        TEXT,
    content        TEXT,
    PRIMARY KEY (coordinator_id, submission_id, time),
    FOREIGN KEY (coordinator_id) REFERENCES Coordinators (id),
    FOREIGN KEY (submission_id) REFERENCES Submissions (id)
);


--
INSERT INTO Faculties(name, description)
VALUES ('Software Engineering', 'SE description'),
       ('Business Administration', 'BA description'),
       ('Graphic Design', 'GD description');

INSERT INTO Users (name, username, hashed_password, email, role, role_id)
VALUES ('An Administrator', 'admin', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 0, 1),
       ('A Manager', 'manager', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 1, 1),
       ('A Coordinator', 'coordinator', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 2, 1),
       ('An SE Student', 'sestudent', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 3, 1),
       ('An BA Student', 'bastudent', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 3, 2),
       ('An GD Student', 'gdstudent', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 3, 3),
       ('A Guest', 'guest', 'dummy_pwd', 'duclmgch15319@fpt.edu.vn', 4, 1);

INSERT INTO Students(id, faculty_id, rollnumber)
VALUES (1, 1, 'GCH15319'),
       (2, 2, 'GBH15591'),
       (3, 3, 'GGG12345');

INSERT INTO Coordinators (faculty_id) VALUES (1);
INSERT INTO Guests (faculty_id) VALUES (1);

UPDATE Users
SET hashed_password = '1000:a4fe87aaab4b4263e0ab02d78660228b:708efd7ff07fa8743029accbd5d' ||
                      'ef0a25f08b8fdf1c68db6af0d7f03c97e82c32331fcd3d6b6784615d9f2ec9d22' ||
                      'e9b460be1778840e1b48fb0c7c95e927478c'
WHERE 1 = 1;

INSERT INTO PublishYears(name, year, first_deadline, second_deadline)
VALUES ('Annual Magazine 2018', 2018, '2018-03-31', '2018-03-20'),
       ('Annual Magazine 2019', 2019, '2019-04-30', '2019-05-20');

INSERT INTO Submissions (id, name, student_id, publish_year, submit_time)
VALUES (1, 'id lobortis convallis tortor risus dapibus', 2, 2, '2019-04-14 15:44:54'),
       (2, 'consectetuer adipiscing elit proin interdum mauris', 2, 2, '2019-04-21 06:38:42'),
       (3, 'platea dictumst maecenas ut massa quis augue', 3, 2, '2019-04-15 19:05:50'),
       (4, 'eleifend luctus ultricies eu nibh quisque', 1, 2, '2019-04-24 13:32:54'),
       (5, 'habitasse platea dictumst morbi vestibulum velit id pretium iaculis diam', 1, 2, '2019-04-10 16:14:54'),
       (6, 'sapien non mi integer ac neque duis', 2, 2, '2019-04-01 11:27:10'),
       (7, 'vestibulum aliquet ultrices erat tortor sollicitudin mi', 2, 2, '2019-04-24 22:28:03'),
       (8, 'nisl nunc nisl duis bibendum felis sed', 1, 2, '2019-04-13 05:58:40'),
       (9, 'duis aliquam convallis nunc proin at turpis a pede', 2, 2, '2019-04-08 21:57:15'),
       (10, 'nibh in quis justo maecenas', 2, 2, '2019-04-26 08:56:49'),
       (11, 'maecenas tincidunt lacus at velit vivamus vel', 2, 2, '2019-04-08 15:54:46'),
       (12, 'posuere cubilia curae donec pharetra magna vestibulum aliquet ultrices', 3, 2, '2019-04-15 06:26:16'),
       (13, 'rutrum rutrum neque aenean auctor gravida sem praesent id', 2, 2, '2019-04-27 21:06:28'),
       (14, 'nunc proin at turpis a pede posuere', 3, 2, '2019-04-07 21:55:03'),
       (15, 'in consequat ut nulla sed accumsan', 3, 2, '2019-04-09 18:53:35'),
       (16, 'varius ut blandit non interdum in ante vestibulum ante', 3, 2, '2019-04-28 19:39:15'),
       (17, 'ut mauris eget massa tempor convallis', 1, 2, '2019-04-24 02:14:48'),
       (18, 'nulla dapibus dolor vel est donec odio justo sollicitudin', 1, 2, '2019-04-14 12:42:54'),
       (19, 'at vulputate vitae nisl aenean', 1, 2, '2019-04-14 11:15:35'),
       (20, 'bibendum felis sed interdum venenatis turpis', 1, 2, '2019-04-21 07:42:01'),
       (21, 'ipsum integer a nibh in', 1, 2, '2019-04-07 06:36:48'),
       (22, 'dui vel nisl duis ac nibh', 2, 2, '2019-04-03 11:00:43'),
       (23, 'quis libero nullam sit amet turpis elementum ligula vehicula', 3, 2, '2019-04-21 12:17:33'),
       (24, 'semper est quam pharetra magna ac consequat metus sapien', 2, 2, '2019-04-24 06:32:14'),
       (25, 'consequat in consequat ut nulla', 1, 2, '2019-04-27 21:12:59'),
       (26, 'luctus et ultrices posuere cubilia curae mauris viverra diam', 3, 2, '2019-04-06 15:11:51'),
       (27, 'justo morbi ut odio cras', 3, 2, '2019-04-18 14:42:16'),
       (28, 'mi in porttitor pede justo eu', 3, 2, '2019-04-05 18:48:07'),
       (29, 'venenatis turpis enim blandit mi in', 2, 2, '2019-04-03 08:03:13'),
       (30, 'odio in hac habitasse platea dictumst maecenas ut', 1, 2, '2019-04-20 15:35:43'),
       (31, 'at nunc commodo placerat praesent blandit', 3, 2, '2019-04-01 23:31:00'),
       (32, 'suscipit a feugiat et eros vestibulum ac', 3, 2, '2019-04-24 11:18:35'),
       (33, 'quam a odio in hac habitasse platea dictumst', 3, 2, '2019-04-09 23:43:14'),
       (34, 'nullam varius nulla facilisi cras non', 2, 2, '2019-04-18 04:49:03'),
       (35, 'lobortis sapien sapien non mi integer ac neque', 2, 2, '2019-04-28 07:35:12'),
       (36, 'neque sapien placerat ante nulla justo aliquam quis', 3, 2, '2019-04-17 16:19:09'),
       (37, 'diam vitae quam suspendisse potenti nullam porttitor', 2, 2, '2019-04-27 15:02:06'),
       (38, 'condimentum id luctus nec molestie sed justo pellentesque', 3, 2, '2019-04-08 19:03:05'),
       (39, 'curae mauris viverra diam vitae quam', 3, 2, '2019-04-21 18:01:40'),
       (40, 'integer aliquet massa id lobortis convallis tortor risus', 3, 2, '2019-04-15 01:56:38'),
       (41, 'quam fringilla rhoncus mauris enim leo rhoncus sed', 2, 2, '2019-04-24 12:19:29'),
       (42, 'aenean fermentum donec ut mauris eget massa', 3, 2, '2019-04-15 15:03:46'),
       (43, 'nec dui luctus rutrum nulla tellus in sagittis', 2, 2, '2019-04-04 22:05:11'),
       (44, 'in felis eu sapien cursus vestibulum proin eu mi', 3, 2, '2019-04-05 13:38:28'),
       (45, 'duis consequat dui nec nisi volutpat eleifend', 3, 2, '2019-04-10 02:57:21'),
       (46, 'leo odio porttitor id consequat in consequat ut nulla sed', 1, 2, '2019-04-19 21:41:20'),
       (47, 'donec odio justo sollicitudin ut suscipit a', 3, 2, '2019-04-23 00:09:43'),
       (48, 'nulla justo aliquam quis turpis', 2, 2, '2019-04-10 13:59:09'),
       (49, 'eget eleifend luctus ultricies eu nibh', 2, 2, '2019-04-19 19:31:53'),
       (50, 'maecenas pulvinar lobortis est phasellus', 1, 2, '2019-04-06 14:26:49'),
       (51, 'convallis tortor risus dapibus augue', 3, 2, '2019-04-21 12:08:05'),
       (52, 'viverra diam vitae quam suspendisse potenti nullam', 2, 2, '2019-04-23 10:30:10'),
       (53, 'erat vestibulum sed magna at nunc commodo placerat', 3, 2, '2019-04-16 02:59:16'),
       (54, 'lacus curabitur at ipsum ac tellus semper interdum', 1, 2, '2019-04-23 06:17:47'),
       (55, 'vehicula condimentum curabitur in libero ut massa', 2, 2, '2019-04-23 13:46:58'),
       (56, 'morbi quis tortor id nulla', 2, 2, '2019-04-08 08:32:29'),
       (57, 'sit amet sem fusce consequat nulla', 2, 2, '2019-04-01 01:18:58'),
       (58, 'et ultrices posuere cubilia curae mauris viverra diam vitae quam', 2, 2, '2019-04-23 19:20:46'),
       (59, 'nisi venenatis tristique fusce congue diam', 3, 2, '2019-04-20 16:42:18'),
       (60, 'nulla ultrices aliquet maecenas leo odio condimentum id', 2, 2, '2019-04-03 12:10:11'),
       (61, 'interdum mauris ullamcorper purus sit', 2, 2, '2019-04-17 21:54:05'),
       (62, 'non ligula pellentesque ultrices phasellus', 1, 2, '2019-04-24 20:00:02'),
       (63, 'vestibulum ante ipsum primis in faucibus orci luctus', 2, 2, '2019-04-05 08:06:47'),
       (64, 'placerat ante nulla justo aliquam quis turpis eget elit sodales', 2, 2, '2019-04-05 09:28:14'),
       (65, 'mi nulla ac enim in', 2, 2, '2019-04-10 15:10:12'),
       (66, 'blandit lacinia erat vestibulum sed magna', 3, 2, '2019-04-15 09:11:40'),
       (67, 'a nibh in quis justo', 2, 2, '2019-04-05 00:41:47'),
       (68, 'faucibus orci luctus et ultrices posuere cubilia curae nulla dapibus', 3, 2, '2019-04-20 02:14:10'),
       (69, 'justo in hac habitasse platea dictumst', 2, 2, '2019-04-08 13:40:38'),
       (70, 'sit amet consectetuer adipiscing elit proin risus', 3, 2, '2019-04-13 03:29:48'),
       (71, 'vivamus metus arcu adipiscing molestie hendrerit at vulputate vitae nisl', 2, 2, '2019-04-29 03:52:45'),
       (72, 'bibendum felis sed interdum venenatis turpis enim blandit', 2, 2, '2019-04-10 14:52:18'),
       (73, 'ridiculus mus etiam vel augue vestibulum', 3, 2, '2019-04-14 08:52:17'),
       (74, 'vestibulum eget vulputate ut ultrices vel augue vestibulum ante', 3, 2, '2019-04-06 23:16:52'),
       (75, 'nibh in hac habitasse platea', 3, 2, '2019-04-25 23:34:57'),
       (76, 'nulla elit ac nulla sed vel enim sit amet nunc', 1, 2, '2019-04-06 18:27:38'),
       (77, 'vulputate luctus cum sociis natoque penatibus et', 1, 2, '2019-04-18 00:26:34'),
       (78, 'justo sollicitudin ut suscipit a feugiat et eros vestibulum ac', 1, 2, '2019-04-21 10:51:52'),
       (79, 'mauris lacinia sapien quis libero nullam sit amet turpis', 3, 2, '2019-04-17 00:02:03'),
       (80, 'potenti in eleifend quam a odio in hac habitasse', 2, 2, '2019-04-11 17:15:37'),
       (81, 'duis aliquam convallis nunc proin at turpis a pede', 2, 2, '2019-04-17 08:43:34'),
       (82, 'molestie hendrerit at vulputate vitae nisl aenean lectus', 3, 2, '2019-04-28 12:00:54'),
       (83, 'dolor morbi vel lectus in quam fringilla rhoncus mauris enim', 1, 2, '2019-04-29 22:37:50'),
       (84, 'tincidunt in leo maecenas pulvinar lobortis est phasellus sit amet', 1, 2, '2019-04-16 01:20:01'),
       (85, 'tortor quis turpis sed ante vivamus tortor duis', 3, 2, '2019-04-12 11:49:34'),
       (86, 'nunc proin at turpis a pede posuere', 1, 2, '2019-04-29 22:25:45'),
       (87, 'turpis enim blandit mi in porttitor', 1, 2, '2019-04-22 20:49:39'),
       (88, 'sit amet turpis elementum ligula vehicula consequat', 2, 2, '2019-04-25 23:09:08'),
       (89, 'nibh quisque id justo sit amet sapien', 1, 2, '2019-04-08 20:47:19'),
       (90, 'auctor gravida sem praesent id massa id nisl venenatis lacinia', 3, 2, '2019-04-25 10:11:33'),
       (91, 'maecenas rhoncus aliquam lacus morbi quis tortor id nulla ultrices', 2, 2, '2019-04-13 11:36:07'),
       (92, 'proin leo odio porttitor id consequat', 2, 2, '2019-04-19 07:39:02'),
       (93, 'a feugiat et eros vestibulum', 2, 2, '2019-04-17 19:16:52'),
       (94, 'vel accumsan tellus nisi eu', 1, 2, '2019-04-21 17:45:16'),
       (95, 'dapibus duis at velit eu', 2, 2, '2019-04-06 19:26:11'),
       (96, 'pellentesque at nulla suspendisse potenti cras in purus eu', 3, 2, '2019-04-02 03:43:51'),
       (97, 'feugiat et eros vestibulum ac est lacinia nisi venenatis tristique', 1, 2, '2019-04-26 03:15:21'),
       (98, 'quam a odio in hac habitasse platea dictumst maecenas', 2, 2, '2019-04-09 11:42:10'),
       (99, 'interdum eu tincidunt in leo maecenas pulvinar lobortis est', 1, 2, '2019-04-14 04:51:28'),
       (100, 'lobortis ligula sit amet eleifend pede libero quis orci nullam', 3, 2, '2019-04-19 03:32:38');




