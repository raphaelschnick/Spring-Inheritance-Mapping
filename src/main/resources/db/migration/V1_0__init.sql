CREATE TABLE car
(
    id              bigint         NOT NULL AUTO_INCREMENT,
    color           character(255) NOT NULL,
    manufacturer_id bigint         NOT NULL,
    price           double         NOT NULL,

    CONSTRAINT PK_car_id PRIMARY KEY (id)
);

CREATE TABLE bike
(
    id              bigint         NOT NULL AUTO_INCREMENT,
    color           character(255) NOT NULL,
    manufacturer_id bigint         NOT NULL,
    price           double         NOT NULL,

    CONSTRAINT PK_bike_id PRIMARY KEY (id)
);

CREATE TABLE truck
(
    id              bigint         NOT NULL AUTO_INCREMENT,
    color           character(255) NOT NULL,
    manufacturer_id bigint         NOT NULL,
    price           double         NOT NULL,
    max_weight      double         NOT NULL,

    CONSTRAINT PK_truck_id PRIMARY KEY (id)
);

CREATE TABLE manufacturer
(
    id   bigint         NOT NULL AUTO_INCREMENT,
    name character(255) NOT NULL,

    CONSTRAINT PK_manufacturer_id PRIMARY KEY (id)
);

CREATE TABLE wheel
(
    id   bigint NOT NULL AUTO_INCREMENT,
    size int    NOT NULL,

    CONSTRAINT PK_wheel_id PRIMARY KEY (id)
);

CREATE TABLE customer
(
    id   bigint         NOT NULL AUTO_INCREMENT,
    name character(255) NOT NULL,

    CONSTRAINT PK_customer_id PRIMARY KEY (id)
);

CREATE TABLE `order`
(
    id          bigint         NOT NULL AUTO_INCREMENT,
    created_at  TIMESTAMP      NOT NULL,
    closed_at   TIMESTAMP,
    status      character(255) NOT NULL,
    customer_id bigint         NOT NULL,

    CONSTRAINT PK_order_id PRIMARY KEY (id)
);
