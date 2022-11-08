create table user_role(
                     user_id int8,
                     role_id int8
);

alter table "user_role" add constraint fk_user_id foreign key (user_id) references "user" (id) on delete cascade;
alter table "user_role" add constraint fk_role_id foreign key (role_id) references role (id) on delete cascade;
alter table "user_role" add primary key (user_id, role_id);