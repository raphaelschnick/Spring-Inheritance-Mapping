INSERT INTO manufacturer (name)
VALUES ('Mercedes'),
       ('Audio'),
       ('SyCar'),
       ('Tesla');

INSERT INTO car (color, manufacturer_id, price)
VALUES ('#23535F', 1, 25.0000),
       ('#0000F', 3, 125.000);

INSERT INTO bike (color, manufacturer_id, price)
VALUES ('#23535F', 1, 25.0000),
       ('#0000F', 3, 125.000);

INSERT INTO truck (color, manufacturer_id, price, max_weight)
VALUES ('#23535F', 1, 25.0000, 5000.00),
       ('#0000F', 3, 125.000, 25.000);

INSERT INTO wheel (size)
VALUES (27),
       (22),
       (20);

INSERT INTO customer (name)
VALUES ('Raphael'),
       ('Stephan'),
       ('Jan'),
       ('Lukas'),
       ('Jean'),
       ('Joris');

INSERT INTO `order` (created_at, status, customer_id)
VALUES ('2022-01-01', 'PLACED', 1),
       ('2019-09-17', 'DELIVERED', 1),
       ('2021-10-22', 'SHIPPED', 1);
