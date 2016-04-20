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
    postNumber INTEGER NOT NULL UNIQUE,
    text VARCHAR(1024) NOT NULL,
    postedAt INTEGER NOT NULL,
    likes INTEGER
);

INSERT INTO Person values (1,'Jack','JackW','jw1234');
INSERT INTO Person values (2,'Liam','LiamW','lw1234');
INSERT INTO Person values (3,'Durgesh','DurgeshP','dp1234');
INSERT INTO Person values (4,'David','DavidB',null);
