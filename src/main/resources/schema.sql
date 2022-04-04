CREATE TABLE if not exists USERS (
    userID INTEGER PRIMARY KEY AUTO_INCREMENT,
    username varchar(50) not null,
    password varchar(100) not null,
    role varchar(20),
    description varchar(50),
    num_of_following INTEGER,
    NUM_OF_FOLLOWERS INTEGER,
    PROFILE_IMG_URL varchar(50)
);

CREATE TABLE if not exists POSTS (
    postID INTEGER PRIMARY KEY AUTO_INCREMENT,
    userID INTEGER not null,
    description varchar(50),
    likes INTEGER,
    date DATE,
    IMG_PATH varchar(50),
    FOREIGN KEY (userID) REFERENCES USERS(userID)
);

CREATE TABLE if not exists COMMENTS (
    commID INTEGER PRIMARY KEY AUTO_INCREMENT,
    userID INTEGER not null,
    postID INTEGER not null,
    date DATE,
    text varchar(50),
    FOREIGN KEY (userID) REFERENCES USERS(userID),
    FOREIGN KEY (postID) REFERENCES POSTS(postID)
);

CREATE TABLE if not exists LIKES (
    userID INTEGER,
    postID INTEGER,
    FOREIGN KEY (userID) REFERENCES USERS(userID),
    FOREIGN KEY (postID) REFERENCES POSTS(postID),
    PRIMARY KEY (userID, postID)
);

CREATE TABLE if not exists  FOLLOWERS (
    followerID INTEGER,
    followingID INTEGER,
    PRIMARY KEY (followerID, followingID),
    FOREIGN KEY (followerID) REFERENCES USERS(userID),
    FOREIGN KEY (followingID) REFERENCES USERS(userID)
);

# CREATE TABLE if not exists SPRING_SESSION
# (
#     PRIMARY_ID            CHAR(36) NOT NULL,
#     SESSION_ID            CHAR(36) NOT NULL,
#     CREATION_TIME         BIGINT   NOT NULL,
#     LAST_ACCESS_TIME      BIGINT   NOT NULL,
#     MAX_INACTIVE_INTERVAL INT      NOT NULL,
#     EXPIRY_TIME           BIGINT   NOT NULL,
#     PRINCIPAL_NAME        VARCHAR(100),
#     CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
# );
#
# CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
# CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
# CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);
#
# CREATE TABLE if not exists SPRING_SESSION_ATTRIBUTES
# (
#     SESSION_PRIMARY_ID CHAR(36)     NOT NULL,
#     ATTRIBUTE_NAME     VARCHAR(200) NOT NULL,
#     ATTRIBUTE_BYTES    BLOB         NOT NULL,
#     CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
#     CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION (PRIMARY_ID) ON DELETE CASCADE
# );
