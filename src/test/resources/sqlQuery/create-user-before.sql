insert into roles (id, name)
values (1, 'ROLE_ADMIN');
insert into roles (id, name)
values (2, 'ROLE_MANAGER');
insert into roles (id, name)
values (3, 'ROLE_PASSENGER');

insert into admin (id, email, password)
values (2, 'admin@mail.ru', 'admin');
insert into airline_manager (id, email, password)
values (3, 'manager@mail.ru', 'manager');
insert into passengers (id, email, first_name, last_name, middle_name, birth_date, gender, phone_number,
                        serial_number_passport, passport_issuing_country, passport_issuing_date, password)
values (4, 'passenger@mail.ru', 'Ivan', 'Ivanov', 'Jovanovich', TO_DATE('2003/11/09', 'YYYY/MM/DD'), 'MALE',
        '79222222222', '2222 222222', 'Russia', TO_DATE('2006/01/11', 'YYYY/MM/DD'), 'passenger');

insert into user_roles (users_id, roles_id)
values (2, 1);
insert into user_roles (users_id, roles_id)
values (3, 2);
insert into user_roles (users_id, roles_id)
values (4, 3)

