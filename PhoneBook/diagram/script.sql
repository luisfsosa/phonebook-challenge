CREATE TABLE contact
(
    id bigint NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    phone character varying(255),
    CONSTRAINT contact_pkey PRIMARY KEY (id)
)