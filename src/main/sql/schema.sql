CREATE TABLE USERS
(
    id               serial primary key,
    firstName        varchar(40) not null,
    lastName         varchar(40),
    email            varchar     not null,
    password         varchar(60) not null,
    country          varchar,
    gender           varchar,
    dateOfBirth      date check ( dateOfBirth < current_date),
    registrationDate timestamp   default current_timestamp,
    editedDate       timestamp   default current_timestamp,
    website_role     varchar(20) default 'user',
    UNIQUE (email, password)
);



CREATE TABLE POSTS
(
    id         serial primary key,
    title      varchar(40) not null,
    post_body  varchar   default '',
    created_at timestamp,
    edited_at  timestamp default current_timestamp,
    author_id  integer     not null references users (id)
);



CREATE TABLE COMMENTS
(
    id         serial primary key,
    text       varchar(400) not null,
    post_id    integer      not null references posts (id),
    author_id  integer      not null references users (id),
    created_at timestamp default current_timestamp
);

CREATE TABLE POST_TAGS
(
    id        serial primary key,
    tag_name_id integer not null references TAG_NAMES(id) check ( tag_name_id > 0),
    post_id   integer   not null references posts (id) check ( post_id > 0 ),
    UNIQUE (tag_name_id, post_id)
);

CREATE TABLE TAG_NAMES(
   id serial primary key,
   name varchar not null unique
);


CREATE TABLE likes
(
    id         serial primary key,
    user_id    int references USERS (id),
    comment_id int references COMMENTS (id),
    post_id    int references POSTS (id),
        check ( coalesce((user_id)::boolean::integer, 0)
                    +
                coalesce((comment_id)::boolean::integer, 0) = 1),
    UNIQUE (user_id, comment_id, post_id)
);

