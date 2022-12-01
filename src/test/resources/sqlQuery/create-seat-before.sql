delete from seats;
delete from category;
delete from aircrafts;

insert into aircrafts (id, aircraft_number, flight_range, model, model_year)
values (1, '17000012', 3800, 'Embraer E170STD', 2002);

insert into category (id, category_type) values (1, 'ECONOMY');

insert into seats (id, seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
values (1, '1A', true, true, (select category.id from category where category.id = 1),
        (select aircrafts.id from aircrafts where aircrafts.id = 1));
