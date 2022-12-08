-- changeset aleksandr_netesov:1.3.4.2
INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number, password,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Юзер7', 'Юзеров', 'Юзерович', TO_DATE('1981/01/12', 'YYYY/MM/DD'), 'MALE', 'user7@mail.ru', '79101111111',
        '$2a$12$XTtAr5.9Lg3PDFJuseBleOFNBFFKoyK4d6CW96VVYXm/X4eYDPHsa', '1111 111112',
        TO_DATE('1997/02/11', 'YYYY/MM/DD'), 'Россия', 7);
INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number, password,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Юзер8', 'Юзеров', 'Юзерович', TO_DATE('1984/04/28', 'YYYY/MM/DD'), 'MALE', 'user8@mail.ru', '79101111112',
        '$2a$12$CA6Yrky5qyKaZQl107B6GeSTwTUMppfKOhY5I/TPf4IYCTyAzF8AC', '1111 111113',
        TO_DATE('2003/02/22', 'YYYY/MM/DD'), 'Россия', 8);
INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number, password,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Юзер9', 'Юзерова', 'Юзеровна', TO_DATE('1988/03/30', 'YYYY/MM/DD'), 'FEMALE', 'user9@mail.ru',
        '79101111113',
        '$2a$12$07FvFJ9t5Ya1BYf5CYJOMeANem1.Wl.rZcK7qslw36C2xnUW2QPVq', '1111 111114',
        TO_DATE('2006/04/20', 'YYYY/MM/DD'), 'Россия', 9);
INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number, password,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Юзер10', 'Юзеров', 'Юзерович', TO_DATE('1986/01/11', 'YYYY/MM/DD'), 'MALE', 'user10@mail.ru', '79101111114',
        '$2a$12$dqaA2EVEWLUnr5uFvdvHoOQ7n.QYwYpTPFGMNlENi0DWvrnsJVKTq', '1111 111115',
        TO_DATE('2006/07/11', 'YYYY/MM/DD'), 'Россия', 10);
INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number, password,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Юзер11', 'Юзеров', 'Юзерович', TO_DATE('1988/03/22', 'YYYY/MM/DD'), 'MALE', 'user11@mail.ru', '79101111115',
        '$2a$12$WkBlA/MDkB2bIYBxVnLpxONqJ.awjzAYcAznpLtK.H60yQM8qVOOq', '1111 111116',
        TO_DATE('2006/02/18', 'YYYY/MM/DD'), 'Россия', 11);
INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number, password,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Юзер12', 'Юзерова', 'Юзеровна', TO_DATE('1991/07/20', 'YYYY/MM/DD'), 'FEMALE', 'user12@mail.ru',
        '79101111116',
        '$2a$12$z2/0uIvjp5stiWmw7RLTMus30TsN/XUIvLErTWsGNUEsNT9tX7fiq', '1111 111117',
        TO_DATE('2009/09/17', 'YYYY/MM/DD'), 'Россия', 12);
-- changeset aleksandr_netesov:1.3.4.2
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT passengers.id FROM passengers WHERE passengers.email = 'user7@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_PASSENGER'));
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT passengers.id FROM passengers WHERE passengers.email = 'user8@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_PASSENGER'));
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT passengers.id FROM passengers WHERE passengers.email = 'user9@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_PASSENGER'));
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT passengers.id FROM passengers WHERE passengers.email = 'user10@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_PASSENGER'));
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT passengers.id FROM passengers WHERE passengers.email = 'user11@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_PASSENGER'));
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT passengers.id FROM passengers WHERE passengers.email = 'user12@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_PASSENGER'));

