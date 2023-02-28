insert into destination (id, airport_code, airport_name, city_name, timezone, country_name) values (2, 'ABA', 'Абакан', 'Абакан', '+3', 'Россия') on conflict (id) do nothing;
insert into destination (id, airport_code, airport_name, city_name, timezone, country_name) values (3, 'ADH', 'Алдан', 'Алдан', '+3', 'Россия') on conflict (id) do nothing;
insert into destination (id, airport_code, airport_name, city_name, timezone, country_name) values (5, 'SVO', 'Шереметьево', 'Москва', '+3', 'Россия') on conflict (id) do nothing;
