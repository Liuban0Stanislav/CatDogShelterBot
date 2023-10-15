-- liquibase formatted sql

-- changeset VPrivalov:3
alter table shelters add column shelter_type smallint;

alter table shelters alter column shelter_type set not null;