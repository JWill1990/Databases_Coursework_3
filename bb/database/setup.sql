DROP TABLE IF EXISTS Likers;
DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Topic;
DROP TABLE IF EXISTS Forum;

CREATE TABLE Forum (
    id INTEGER PRIMARY KEY,
    title VARCHAR(100) NOT NULL
);

CREATE TABLE Topic (
    id INTEGER PRIMARY KEY,
    forumID INTEGER REFERENCES Forum(id),
    title VARCHAR(100) NOT NULL
);

CREATE TABLE Person (
    id INTEGER PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(10) NOT NULL UNIQUE,
    stuId VARCHAR(10) NULL
);

CREATE TABLE Post (
    id INTEGER PRIMARY KEY,
    topicID INTEGER REFERENCES Topic(id),
    personID INTEGER REFERENCES Person(id),
    postNumber INTEGER NOT NULL,
    text VARCHAR(1024) NOT NULL,
    postedAt INTEGER NOT NULL
);

CREATE TABLE Likers (
	topicID INTEGER REFERENCES Topic(id),
    personID INTEGER REFERENCES Person(id)
);

INSERT INTO Person values (1,'Jack','JackW','jw1234');
INSERT INTO Person values (2,'Liam','LiamW','lw1234');
INSERT INTO Person values (3,'Durgesh','DurgeshP','dp1234');
INSERT INTO Person values (4,'David','DavidB',null);

INSERT INTO forum values (1,'Database');
INSERT INTO forum values (2,'Oops');
INSERT INTO forum values (3,'C Programming Language');

INSERT INTO Topic values (1,1,'CourseWork3');
INSERT INTO Topic values (2,1,'General Feedback');
INSERT INTO Topic values (3,2,'Graphics Assignment');
INSERT INTO Topic values (4,3,'When is the teletext result going to be out');

INSERT INTO post values (1,1,3,1,'What is better to use PreparedStatement or Statement for this assignment',4248221);
INSERT INTO post values (2,1,4,2,'This is for you to explore',4264278);

INSERT INTO post values (3,2,4,1,'General feedback will be posted here',4238293);

INSERT INTO likers values (1, 1);
INSERT INTO likers values (2, 3);
