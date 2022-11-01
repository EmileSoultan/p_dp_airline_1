delete from aircraft_seat;
delete from aircraft;
delete from seat;

insert into aircraft(id, aircraft_number, flight_range, model, model_year) values
(1, '17000012', 3800, 'Embraer E170STD', 2002),
(2, '5134', 4300, 'Airbus A320-200', 2011);

insert into seat(id, fare, is_near_emergency_exit, is_registered, is_sold, seat_number) values
(1, 500, TRUE, TRUE, TRUE, '2A'),
(2, 700, FALSE, FALSE, TRUE, '3D');

insert into aircraft_seat(aircraft_id, seat_id) values
(1, 1),
(2, 2);