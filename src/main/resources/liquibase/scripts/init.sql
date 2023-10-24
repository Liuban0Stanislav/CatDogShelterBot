--liquibase formatted sql

--changeset VPrivalov:1
CREATE TABLE IF NOT EXISTS guests
(
    id          BIGSERIAL PRIMARY KEY,
    telegram_id VARCHAR,
    last_visit  TIMESTAMP,
    last_menu   INT
);

--changeset VPrivalov:2
CREATE TABLE IF NOT EXISTS adopters
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR,
    last_name   VARCHAR,
    passport    VARCHAR,
    age         INT,
    phone1      VARCHAR,
    phone2      VARCHAR,
    email       VARCHAR,
    telegram    VARCHAR, -- telegram id
    volunteer_id    INT, -- Lookup to [volunteers] table
    on_probation    BOOL,
    active          BOOL
);

--changeset PVladimirov:3
CREATE TABLE IF NOT EXISTS volunteers
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR,
    telegram    VARCHAR,
    picture     BYTEA
);

--changeset PVladimirov:4
CREATE TABLE IF NOT EXISTS adoption_reports
(
    id  BIGSERIAL PRIMARY KEY,
    adopter_id  BIGSERIAL,
    report_date TIMESTAMP,
    pet_id      INT,
    picture     BYTEA,
    diet        VARCHAR,
    wellbeing   VARCHAR,
    behavior_change VARCHAR
);

-- changeset Stanislav:1
CREATE TABLE IF NOT EXISTS branch_params
(
    id      INT PRIMARY KEY,
    name    VARCHAR,
    country VARCHAR,
    city    VARCHAR,
    zip     VARCHAR,
    address VARCHAR,
    work_hours  VARCHAR,
    map         BYTEA, -- directions how to get there
    info        TEXT,  -- other information
    prob_period INT,   -- probation period (in days)
    prob_extend INT    -- probation extension period (in days)
);

-- changeset Stanislav:2
CREATE TABLE IF NOT EXISTS pets
(
    id          BIGSERIAL PRIMARY KEY,
    nick_name   VARCHAR,
    pet_type    INT, -- enum PetType
    color       INT, -- enum Color
    sex         INT  -- enum Sex
);

-- changeset Stanislav:3
ALTER TABLE volunteers DROP COLUMN IF EXISTS telegram;
ALTER TABLE volunteers ADD COLUMN IF NOT EXISTS chat_id BIGINT;
ALTER TABLE volunteers ADD COLUMN IF NOT EXISTS username VARCHAR;

-- changeset Stanislav:4
CREATE SEQUENCE IF NOT EXISTS branch_params_id_seq;
ALTER TABLE branch_params ALTER COLUMN id SET DEFAULT nextval('branch_params_id_seq');

-- changeset Stanislav:5
ALTER TABLE adopters DROP COLUMN IF EXISTS on_probation;
ALTER TABLE adopters DROP COLUMN IF EXISTS active;
ALTER TABLE adopters ADD COLUMN IF NOT EXISTS status INT;

-- changeset Stanislav:6
ALTER TABLE pets ADD COLUMN IF NOT EXISTS breed_id INT; -- lookup to [breeds] table
ALTER TABLE pets ADD COLUMN IF NOT EXISTS picture BYTEA;
ALTER TABLE pets ADD COLUMN IF NOT EXISTS adopter_id INT; -- lookup to [adopters] table

-- changeset Stanislav:7
ALTER TABLE guests DROP COLUMN IF EXISTS telegram_id;
ALTER TABLE guests ADD COLUMN IF NOT EXISTS chat_id BIGINT;
ALTER TABLE guests ADD COLUMN IF NOT EXISTS username VARCHAR;

--changeset AndreiK:1
CREATE TABLE IF NOT EXISTS breeds
(
    id		    SERIAL PRIMARY KEY,
    pet_type	INT, -- enam PetTypes
    name	    VARCHAR
);

-- changeset VPrivalov:3
CREATE TABLE IF NOT EXISTS adoption_docs
(
    id           SERIAL PRIMARY KEY,
    short_desc   VARCHAR,
    description  VARCHAR
);

-- changeset Stanislav:8
DROP SEQUENCE IF EXISTS adoption_reports_adopter_id_seq CASCADE;
ALTER TABLE adoption_reports ALTER COLUMN adopter_id SET DATA TYPE BIGINT;

-- changeset Stanislav:9
ALTER TABLE branch_params ADD COLUMN IF NOT EXISTS security_info TEXT;
ALTER TABLE branch_params ADD COLUMN IF NOT EXISTS security_contact VARCHAR;
ALTER TABLE branch_params ADD COLUMN IF NOT EXISTS pet_type INT;

-- changeset Stanislav:10
ALTER TABLE adopters DROP COLUMN IF EXISTS telegram;
ALTER TABLE adopters ADD COLUMN IF NOT EXISTS chat_id BIGINT;
ALTER TABLE adopters ADD COLUMN IF NOT EXISTS username VARCHAR;

--changeset PVladimirov:11
ALTER TABLE adoption_reports
ALTER COLUMN report_date TYPE DATE;

-- changeset Stanislav:11
ALTER TABLE adoption_docs DROP COLUMN IF EXISTS short_desc;
ALTER TABLE adoption_docs ALTER COLUMN id SET DATA TYPE INT;
DROP SEQUENCE IF EXISTS adoption_docs_id_seq CASCADE;

-- changeset Stanislav:12
ALTER TABLE adopters ADD COLUMN IF NOT EXISTS update_status INT;

-- changeset Stanislav:13
ALTER TABLE branch_params DROP COLUMN IF EXISTS prob_extend;
ALTER TABLE adopters ADD COLUMN IF NOT EXISTS prob_extend INT;

-- changeset Stanislav:14
ALTER TABLE volunteers DROP COLUMN IF EXISTS picture;