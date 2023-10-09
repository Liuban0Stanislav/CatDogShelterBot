-- liquibase formatted sql

-- changeset VPrivalov:1

create table animals
(
    id         BIGINT primary key,
    name       TEXT,
    age        INT,
    sex        TEXT,
    shelter_id BIGINT NOT NULL
);

create table shelters
(
    id                  BIGINT primary key,
    about               TEXT,
    address             TEXT,
    route_map           bytea,
    work_time           TEXT,
    territory_pass      TEXT,
    security_phone      TEXT,
    inside_restrictions TEXT,
    safety_rules        TEXT,
    animals_advice      TEXT
);

create table clients
(
    id          BIGINT primary key,
    first_name  TEXT   NOT NULL,
    last_name   TEXT   NOT NULL,
    telegram_id BIGINT NOT NULL,
    chat_id     BIGINT NOT NULL,
    phone       TEXT
);

create table adopters
(
    id               BIGINT primary key,
    client_id        BIGINT  NOT NULL,
    animal_id        BIGINT  NOT NULL,
    shelter_id       BIGINT  NOT NULL,
    probation_start  DATE    NOT NULL,
    probation_end    DATE    NOT NULL,
    probation_result BOOLEAN NOT NULL
);

create table reports
(
    id          BIGINT primary key,
    date_report DATE   NOT NULL,
    client_id   BIGINT NOT NULL,
    animal_id   BIGINT NOT NULL,
    photo       bytea  NOT NULL,
    diet        TEXT   NOT NULL,
    health      TEXT   NOT NULL,
    behavior    TEXT   NOT NULL
);

-- changeset PVladimirov:2
alter table animals
add foreign key (shelter_id) references shelters (id);

alter table reports
    add foreign key (animal_id) references animals (id);

alter table adopters
    add foreign key (animal_id) references animals (id);

alter table adopters
    add foreign key (shelter_id) references shelters (id);

alter table reports
    add foreign key (client_id) references clients (id);

alter table adopters
    add foreign key (client_id) references clients (id);