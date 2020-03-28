CREATE SCHEMA sep; 

CREATE TABLE sep.classrooms
(
    classroomid character varying NOT NULL,
    classroomname character varying,
    CONSTRAINT "classroomId_pk" PRIMARY KEY (classroomid)
);

CREATE TABLE sep.credentials
(
    fname character varying NOT NULL,
    lname character varying NOT NULL,
    email character varying NOT NULL,
    accountnumber character varying NOT NULL,
    password character varying NOT NULL,
    isteacher boolean,
    CONSTRAINT "accountNumber_pk" PRIMARY KEY (accountnumber)
);

CREATE TABLE sep.question
(
    word character varying,
    type character varying,
    question character varying,
    answer1 character varying,
    answer2 character varying,
    answer3 character varying,
    answer4 character varying,
    correct character varying,
    "accountNumber" character varying
);

CREATE TABLE sep.sessionserver
(
    accesscode character varying NOT NULL,
    ipaddress character varying NOT NULL,
    portnumber character varying NOT NULL,
    teachername character varying,
    classroom character varying,
    CONSTRAINT "accessCode_pk" PRIMARY KEY (accesscode),
    CONSTRAINT classroom_fk FOREIGN KEY (classroom)
        REFERENCES sep.classrooms (classroomid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE sep.topics
(
    topics character varying NOT NULL,
    CONSTRAINT topic_pk PRIMARY KEY (topics)
);

CREATE TABLE sep.words
(
    word character varying NOT NULL,
    topic character varying NOT NULL,
    CONSTRAINT word_pk PRIMARY KEY (word, topic),
    CONSTRAINT topic_fk FOREIGN KEY (topic)
        REFERENCES sep.topics (topics) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

INSERT INTO sep.credentials(
	fname, lname, email, accountnumber, password, isteacher)
	VALUES ('John', 'Doe', 'jd@mail.com', 'john0001', 'password', true),
    ('James', 'May', 'jm@mail.com', 'james0001', 'password', false),
    ('Alsison', 'Goodchild', 'ag@mail.com', 'alison0001', 'password', false);

INSERT INTO sep.classrooms(
	classroomid, classroomname)
	VALUES ('L1AM1', 'Lokale 1 Aftenshold Modul 1'),
    ('L2DM2', 'Lokale 2 Dagshold Modul 2'),
    ('L3AM5', 'Lokale 3 Aftenshold Modul 5');

INSERT INTO sep.topics(
	topics)
	VALUES ('Hus'),
    ('Fritid'),
    ('Frivilligt Arbejde'),
    ('Uddannelse');

INSERT INTO sep.words(
	word, topic)
	VALUES ('tørre af', 'Hus'),
    ('vaske', 'Hus'),
    ('feje', 'Hus'),
    ('svabe', 'Hus'),
    ('rengøre', 'Hus'),
    ('i går', 'Fritid'),
    ('klokken', 'Fritid'),
    ('bruge', 'Fritid'),
    ('tage afsted', 'Fritid'),
    ('grine', 'Fritid'),
    ('ubetalt', 'Frivilligt Arbejde'),
    ('social', 'Frivilligt Arbejde'),
    ('erfaring', 'Frivilligt Arbejde'),
    ('samarbejde', 'Frivilligt Arbejde'),
    ('sportsklubbers', 'Frivilligt Arbejde'),
    ('aftenskole', 'Uddannelse'),
    ('skolepraktik', 'Uddannelse'),
    ('erhvervsakademi', 'Uddannelse'),
    ('folkehøjskolerne', 'Uddannelse'),
    ('jobcentre', 'Uddannelse');