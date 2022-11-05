CREATE TABLE USERS(
    id serial primary key,
    firstName varchar(40) not null,
    lastName varchar(40),
    email varchar not null,
    password varchar(60) not null,
    country varchar,
    gender varchar,
    dateOfBirth date check ( dateOfBirth < current_date),
    registrationDate timestamp default current_timestamp,
    editedDate timestamp default current_timestamp,
    website_role varchar(20) default 'user',
    UNIQUE (email, password)
);


CREATE TABLE POSTS (
    id serial primary key,
    title varchar(40) not null,
    post_body varchar default '',
    created_at timestamp,
    edited_at timestamp default current_timestamp,
    category_id integer not null references categories(id),
    likes integer check ( likes > 0),
    author_id integer not null references users(id)
);


CREATE TABLE CATEGORIES (
    id serial primary key,
    title varchar(40) not null
);

CREATE TABLE COMMENTS(
    id serial primary key,
    text varchar(400) not null,
    post_id integer not null references posts(id),
    author_id integer not null references users(id),
    created_at timestamp default current_timestamp
);

CREATE TABLE TAGS(
    id serial primary key,
    tag_title varchar(80) not null,
    post_id integer not null references posts(id) check ( post_id >= 0 ),
    UNIQUE (post_id, tag_title)
);

