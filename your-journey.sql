DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_photos CASCADE;
DROP TABLE IF EXISTS user_tokens CASCADE;
DROP TABLE IF EXISTS users_located CASCADE;
DROP TABLE IF EXISTS user_friends CASCADE;
DROP TABLE IF EXISTS user_friend_requests CASCADE;
DROP TABLE IF EXISTS places CASCADE;
DROP TABLE IF EXISTS place_photos CASCADE;
DROP TABLE IF EXISTS place_reports CASCADE;
DROP TABLE IF EXISTS place_likes CASCADE;
DROP TABLE IF EXISTS place_comments CASCADE;
DROP TABLE IF EXISTS dialogs CASCADE;
DROP TABLE IF EXISTS dialog_members CASCADE;
DROP TABLE IF EXISTS messages CASCADE;

CREATE TABLE users (
    id BIGSERIAL,  
    email VARCHAR(256) UNIQUE,
    password_crypt VARCHAR(256), 
    name VARCHAR(64) NOT NULL,
    gender VARCHAR(6) DEFAULT 'none',
    about VARCHAR(1024), 
    country VARCHAR(64), 
    city VARCHAR(64),
    avatar VARCHAR(512),
    CONSTRAINT pk_users PRIMARY KEY (id)
);


CREATE TABLE user_tokens(
    id BIGSERIAL,
    user_id BIGINT, 
    access_token VARCHAR(64) NOT NULL, 
    expires_in BIGINT NOT NULL, 
    refresh_token VARCHAR(64) NOT NULL,
    CONSTRAINT fk_user_tokens_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT pk_user_tokens PRIMARY KEY (user_id)
);

CREATE TABLE users_located (
    id BIGSERIAL,
    user_id BIGINT NOT NULL,
    current_country VARCHAR(64) NOT NULL,
    current_city VARCHAR(64) NOT NULL,
    last_time BIGINT NOT NULL,
    CONSTRAINT fk_user_located_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT pk_users_located PRIMARY KEY (user_id)
);

CREATE TABLE user_friends(
    id BIGSERIAL,
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL,
    CONSTRAINT fk_user_friends_users1 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_friends_users2 FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT pk_user_friends PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE user_friend_requests(
    id BIGSERIAL,
    from_id BIGINT NOT NULL,
    to_id BIGINT NOT NULL,
    CONSTRAINT fk_user_friend_requests_users1 FOREIGN KEY (from_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_friend_requests_users2 FOREIGN KEY (to_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT pk_user_friend_requests PRIMARY KEY (from_id, to_id)
);


CREATE TABLE places(
    id BIGSERIAL, 
    title VARCHAR(32) NOT NULL, 
    description VARCHAR(1024),
    lat REAL NOT NULL,
    lon REAL NOT NULL,
    country VARCHAR(64), 
    city VARCHAR(64),
    creation_date BIGINT DEFAULT 0,
    owner_id BIGINT NOT NULL,
    private BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_places PRIMARY KEY (id),
    CONSTRAINT fk_places_users FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE place_photos(
    id BIGSERIAL,
    place_id BIGINT NOT NULL,
    idx INT DEFAULT 0,
    uri VARCHAR(512) NOT NULL,
    CONSTRAINT fk_place_photos_places FOREIGN KEY (place_id) REFERENCES places(id) ON DELETE CASCADE,
    CONSTRAINT pk_place_photos PRIMARY KEY (id)
);

CREATE TABLE place_likes(
    id BIGSERIAL,
    user_id BIGINT NOT NULL,
    place_id BIGINT NOT NULL,
    like_date BIGINT DEFAULT 0,
    CONSTRAINT fk_place_likes_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_place_likes_places FOREIGN KEY (place_id) REFERENCES places(id) ON DELETE CASCADE,
    CONSTRAINT pk_place_likes PRIMARY KEY (user_id, place_id)
);

CREATE TABLE place_comments(
    id BIGSERIAL,
    owner_id BIGINT NOT NULL,
    place_id BIGINT NOT NULL,
    body TEXT,
    comment_date BIGINT DEFAULT 0,
    CONSTRAINT fk_place_comments_users FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_place_comments_places FOREIGN KEY (place_id) REFERENCES places(id) ON DELETE CASCADE,
    CONSTRAINT pk_place_comments PRIMARY KEY (id)
);

CREATE TABLE place_reports(
    id BIGSERIAL,
    place_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    report_time BIGINT DEFAULT 0,
    CONSTRAINT fk_place_reports_places FOREIGN KEY (place_id) REFERENCES places(id) ON DELETE CASCADE,
    CONSTRAINT fk_place_reports_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT pk_place_reports PRIMARY KEY (place_id, user_id)
);

CREATE TABLE dialogs(
    id BIGSERIAL,
    title VARCHAR(32),
    CONSTRAINT pk_dialogs PRIMARY KEY (id)
);

CREATE TABLE dialog_members(
    id BIGSERIAL,
    dialog_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_dialog_members_dialogs FOREIGN KEY (dialog_id) REFERENCES dialogs(id) ON DELETE CASCADE,
    CONSTRAINT fk_dialog_members_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT pk_dialog_members PRIMARY KEY (dialog_id, user_id)
);

CREATE TABLE messages(
    id BIGSERIAL,
    body TEXT,
    user_id BIGINT NOT NULL,
    dialog_id BIGINT NOT NULL,
    message_time BIGINT DEFAULT 0,
    CONSTRAINT fk_messages_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_messages_dialogs FOREIGN KEY (dialog_id) REFERENCES dialogs(id) ON DELETE CASCADE,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);
