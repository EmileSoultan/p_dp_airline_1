-- changeset aleksandr_netesov:1.3.6
INSERT INTO flight_seats (fare, is_registered, is_sold, flight_id, seat_id)
VALUES (3500, true, false, (SELECT flights.id FROM flights WHERE flights.id = 4),
        (SELECT seats.id FROM seats WHERE seats.id = 1));
INSERT INTO flight_seats (fare, is_registered, is_sold, flight_id, seat_id)
VALUES (4800, true, true, (SELECT flights.id FROM flights WHERE flights.id = 3),
        (SELECT seats.id FROM seats WHERE seats.id = 2));
INSERT INTO flight_seats (fare, is_registered, is_sold, flight_id, seat_id)
VALUES (6300, true, true, (SELECT flights.id FROM flights WHERE flights.id = 6),
        (SELECT seats.id FROM seats WHERE seats.id = 3));
INSERT INTO flight_seats (fare, is_registered, is_sold, flight_id, seat_id)
VALUES (7500, false, false, (SELECT flights.id FROM flights WHERE flights.id = 8),
        (SELECT seats.id FROM seats WHERE seats.id = 1));
INSERT INTO flight_seats (fare, is_registered, is_sold, flight_id, seat_id)
VALUES (5400, true, true, (SELECT flights.id FROM flights WHERE flights.id = 2),
        (SELECT seats.id FROM seats WHERE seats.id = 4));
INSERT INTO flight_seats (fare, is_registered, is_sold, flight_id, seat_id)
VALUES (8800, false, true, (SELECT flights.id FROM flights WHERE flights.id = 8),
        (SELECT seats.id FROM seats WHERE seats.id = 3));
INSERT INTO flight_seats (fare, is_registered, is_sold, flight_id, seat_id)
VALUES (6400, true, true, (SELECT flights.id FROM flights WHERE flights.id = 6),
        (SELECT seats.id FROM seats WHERE seats.id = 5));