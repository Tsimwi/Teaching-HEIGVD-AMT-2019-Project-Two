CREATE DATABASE project_two
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\connect project_two

CREATE TABLE magic_entity
(
    name character varying(255) NOT NULL,
    entity_creator character varying(255) NOT NULL,
    power integer NOT NULL,
    spell character varying(255) NOT NULL,
    CONSTRAINT magic_entity_pkey PRIMARY KEY (name)
);

ALTER TABLE public.magic_entity OWNER TO postgres;


CREATE TABLE unicorn_entity
(
    name character varying(255) NOT NULL,
    color character varying(255) NOT NULL,
    entity_creator character varying(255) NOT NULL,
    has_wings boolean NOT NULL,
    speed integer NOT NULL,
    CONSTRAINT unicorn_entity_pkey PRIMARY KEY (name)
);

ALTER TABLE public.unicorn_entity OWNER TO postgres;

CREATE TABLE unicorn_entity_magic_entities
(
    unicorn_entities_name character varying(255) NOT NULL,
    magic_entities_name character varying(255) NOT NULL,
    CONSTRAINT fkgbpbhjhw89msdaa8h3f26n7or FOREIGN KEY (unicorn_entities_name)
        REFERENCES unicorn_entity (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fkovhwdsw2mpaoeg91fkc2h01de FOREIGN KEY (magic_entities_name)
        REFERENCES magic_entity (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.unicorn_entity_magic_entities OWNER TO postgres;
