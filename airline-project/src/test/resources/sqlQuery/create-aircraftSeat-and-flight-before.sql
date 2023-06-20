insert into aircrafts(id, aircraft_number, model, model_year, flight_range)
values (1, '5134', 'Airbus A320', 2011, 4300);

insert into category(id, category_type)
values (1, 'BUSINESS'),
       (2, 'ECONOMY');

insert into seats (id, seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
values (1, '1A', true, true, (select category.id from category where category.id = 1),
        (select aircrafts.id from aircrafts where aircrafts.id = 1));
insert into seats (id, seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
values (2, '1B', false, false, (select category.id from category where category.id = 2),
        (select aircrafts.id from aircrafts where aircrafts.id = 1));

insert into destination (id, airport_code, airport_name, city_name, country_name, timezone)
values (1, 'VKO', 'Внуково', 'Москва', 'Россия', 'GMT +3');
insert into destination (id, airport_code, airport_name, city_name, country_name, timezone)
values (4, 'OMS', 'Омск', 'Омск', 'Россия', 'GMT +6');

insert into flights (id, code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
values (1, 'MSKOMSK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ON_TIME',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 1),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        (SELECT destination.id FROM destination WHERE city_name = 'Омск'));