delete from user_roles;
delete from admin;
delete from airline_manager;
delete from passenger;
delete from roles;

insert into roles (id, name) values (1, 'ROLE_ADMIN');
insert into roles (id, name) values (2, 'ROLE_MANAGER');
insert into roles (id, name) values (3, 'ROLE_PASSENGER');

insert into admin (id, email, password) values (2, 'admin@mail.ru', 'admin');
insert into airline_manager (id, email, password) values (3, 'manager@mail.ru', 'manager');
insert into passenger (id, email, password) values (4, 'passener@mail.ru', 'passenger');

insert into user_roles (users_id, roles_id) values (2, 1);
insert into user_roles (users_id, roles_id) values (3, 2);
insert into user_roles (users_id, roles_id) values (4, 3)

