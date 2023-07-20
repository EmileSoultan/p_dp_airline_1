
INSERT INTO passengers (first_name, last_name, middle_name, birth_date, gender, email, phone_number,
                        serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('John2', 'Simons2', 'J2', TO_DATE('2003/11/08', 'YYYY/MM/DD'), 'MALE', 'passenger2@mail.ru', '79111181111',
        '0010 001000', TO_DATE('2006/01/11', 'YYYY/MM/DD'), 'Россия', 1002);

INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Пётр2', 'Петров2', 'Петрович2', TO_DATE('1986/01/11', 'YYYY/MM/DD'), 'MALE', 'petrov2@mail.ru', '79111511111',
        '1121 111121', TO_DATE('2006/01/11', 'YYYY/MM/DD'), 'Россия', 1001);


INSERT INTO aircrafts (id, aircraft_number, model, model_year, flight_range)
VALUES (2001, '0000002001', 'Embraer E170STD', 2002, 3800),
       (2002, '0000002002', 'Airbus A320-200', 2011, 4300);


INSERT INTO destination (id, airport_code, airport_name, city_name, timezone, country_name)
VALUES (3001, 'SVO', 'Шереметьево', 'Москва', '+3', 'Россия'),
       (3002, 'VOG', 'Гумрак', 'Волгоград', '+3', 'Россия'),
       (3003, 'UFA', 'Уфа', 'Уфа', '+5', 'Россия');


INSERT INTO flights (id, code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES (4001, 'MSKVOG', NOW(), NOW(), 'ON_TIME',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2001),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        (SELECT destination.id FROM destination WHERE city_name = 'Волгоград')),
       (4002, 'VOGUFA', NOW(), NOW(), 'DELAYED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2002),
        (SELECT destination.id FROM destination WHERE city_name = 'Волгоград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Уфа'));


insert into category(id, category_type)
values (5001, 'BUSINESS'),
       (5002, 'ECONOMY');


INSERT INTO booking (id, booking_number, booking_data_time, passenger_id, flight_id, category_id)
VALUES (6001, '000000001', NOW(),
           -- for Get
        (SELECT passengers.id FROM passengers WHERE passengers.id = 1001),
        (SELECT flights.id FROM flights WHERE flights.id = 4001),
        (SELECT category.id FROM category WHERE category.id = 5001)),
       -- for Edit
       (6002, '000000002', NOW(),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 1002),
        (SELECT flights.id FROM flights WHERE flights.id = 4002),
        (SELECT category.id FROM category WHERE category.id = 5002)),
       -- for Delete
       (6003, '000000003', NOW(),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 1002),
        (SELECT flights.id FROM flights WHERE flights.id = 4002),
        (SELECT category.id FROM category WHERE category.id = 5002));