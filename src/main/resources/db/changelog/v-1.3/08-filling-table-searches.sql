-- changeset aleksandr_netesov:1.3.8
INSERT INTO searches(from_id, to_id, return_date, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        (SELECT destination.id FROM destination WHERE city_name = 'Омск'),
        TO_DATE('2022/11/25', 'YYYY/MM/DD'), TO_DATE('2022/11/30', 'YYYY/MM/DD'), 2);
INSERT INTO searches(from_id, to_id, return_date, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Волгоград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        TO_DATE('2022/11/27', 'YYYY/MM/DD'), TO_DATE('2022/11/30', 'YYYY/MM/DD'), 1);