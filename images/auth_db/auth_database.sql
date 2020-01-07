CREATE DATABASE project_two
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\connect project_two


CREATE TABLE public.user_entity
(
    mail character varying(255) NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    role character varying(255),
    CONSTRAINT user_entity_pkey PRIMARY KEY (mail)
);

ALTER TABLE public.user_entity OWNER TO postgres;
