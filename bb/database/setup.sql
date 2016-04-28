DROP TABLE IF EXISTS PostLikers;
DROP TABLE IF EXISTS TopicLikers;
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
    username VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    stuId VARCHAR(10) NULL
);

CREATE TABLE Post (
    id INTEGER PRIMARY KEY,
    topicID INTEGER REFERENCES Topic(id),
    personID VARCHAR(10) REFERENCES Person(username),
    text VARCHAR(1024) NOT NULL,
    postedAt INTEGER NOT NULL
);

CREATE TABLE TopicLikers (
    topicID INTEGER REFERENCES Topic(id),
    personID VARCHAR(10) REFERENCES Person(username)
);

CREATE TABLE PostLikers (
    postID INTEGER REFERENCES Post(id),
    personID VARCHAR(10) REFERENCES Person(username)
);

INSERT INTO Person values ('JackW','Jack','jw1234');
INSERT INTO Person values ('LiamW','Liam','lw1234');
INSERT INTO Person values ('DurgeshP','Durgesh','dp1234');
INSERT INTO Person values ('DavidB','David',null);

INSERT INTO forum values (1,'Database');
INSERT INTO forum values (2,'Oops');
INSERT INTO forum values (3,'C Programming Language');

INSERT INTO Topic values (1,1,'CourseWork3');
INSERT INTO Topic values (2,1,'General Feedback');
INSERT INTO Topic values (3,2,'Graphics Assignment');
INSERT INTO Topic values (4,3,'When is the teletext result going to be out');

INSERT INTO Post values (1,1,'DurgeshP','What is better to use PreparedStatement or Statement for this assignment',1460286600000);
INSERT INTO Post values (2,1,'DavidB','This is for you to explore',1460290463000);

INSERT INTO Post values (3,2,'DavidB','General feedback will be posted here',1462882463000);

INSERT INTO TopicLikers values (1, 'JackW');
INSERT INTO TopicLikers values (2, 'DurgeshP');
