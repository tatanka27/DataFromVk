create sequence role_id_seq INCREMENT 1 START 1;


create table role(
                     id int8 primary key default nextval('role_id_seq'),
                     name varchar(50) not null unique
);

alter sequence role_id_seq owned by role.id;
