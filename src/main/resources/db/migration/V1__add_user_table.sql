create sequence user_id_seq INCREMENT 1 START 1;


create table "user"(
                       id int8 primary key default nextval('user_id_seq'),
                       username varchar(50) not null unique,
                       password varchar(500) not null
);

alter sequence user_id_seq owned by "user".id;