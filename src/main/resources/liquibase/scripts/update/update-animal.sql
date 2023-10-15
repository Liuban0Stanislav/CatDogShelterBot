-- liquibase formatted sql

-- changeset VPrivalov:2
alter table animals add column animal_type smallint;

alter table animals alter column animal_type set not null;