CREATE TABLE car
(
    id              bigint         NOT NULL AUTO_INCREMENT,
    color           character(255) NOT NULL,
    manufacturer_id bigint         NOT NULL,

    CONSTRAINT PK_car_id PRIMARY KEY (id)
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
