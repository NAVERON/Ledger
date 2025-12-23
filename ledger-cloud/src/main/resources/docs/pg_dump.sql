--
-- PostgreSQL database dump
--

-- Dumped from database version 10.19 (Ubuntu 10.19-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.19 (Ubuntu 10.19-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: business_record; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.business_record (
    id integer NOT NULL,
    user_id bigint NOT NULL,
    amount money DEFAULT 0 NOT NULL,
    record_time timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.business_record OWNER TO postgres;

--
-- Name: business_record_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.business_record_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.business_record_id_seq OWNER TO postgres;

--
-- Name: business_record_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.business_record_id_seq OWNED BY public.business_record.id;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: open
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO open;

--
-- Name: role_permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role_permission (
    id integer NOT NULL,
    role_type character(20) DEFAULT 'ANONYMITY'::bpchar NOT NULL,
    description character varying(100),
    permissions character varying(500)
);


ALTER TABLE public.role_permission OWNER TO postgres;

--
-- Name: role_permission_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_permission_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_permission_id_seq OWNER TO postgres;

--
-- Name: role_permission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.role_permission_id_seq OWNED BY public.role_permission.id;


--
-- Name: user_acount; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_acount (
    id integer NOT NULL,
    user_name character(50) DEFAULT ''::bpchar,
    password character(50) DEFAULT ''::bpchar,
    email_address character varying(100),
    phone_number character varying(100),
    role_type character(20) DEFAULT 'ANONYMITY'::bpchar NOT NULL,
    role_id bigint
);


ALTER TABLE public.user_acount OWNER TO postgres;

--
-- Name: user_acount_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_acount_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_acount_id_seq OWNER TO postgres;

--
-- Name: user_acount_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_acount_id_seq OWNED BY public.user_acount.id;


--
-- Name: business_record id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.business_record ALTER COLUMN id SET DEFAULT nextval('public.business_record_id_seq'::regclass);


--
-- Name: role_permission id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_permission ALTER COLUMN id SET DEFAULT nextval('public.role_permission_id_seq'::regclass);


--
-- Name: user_acount id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_acount ALTER COLUMN id SET DEFAULT nextval('public.user_acount_id_seq'::regclass);


--
-- Data for Name: business_record; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.business_record (id, user_id, amount, record_time) FROM stdin;
\.


--
-- Data for Name: role_permission; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role_permission (id, role_type, description, permissions) FROM stdin;
\.


--
-- Data for Name: user_acount; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_acount (id, user_name, password, email_address, phone_number, role_type, role_id) FROM stdin;
\.


--
-- Name: business_record_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.business_record_id_seq', 1, false);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: open
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- Name: role_permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.role_permission_id_seq', 1, false);


--
-- Name: user_acount_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_acount_id_seq', 1, false);


--
-- Name: business_record business_record_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.business_record
    ADD CONSTRAINT business_record_pkey PRIMARY KEY (id);


--
-- Name: role_permission role_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_permission
    ADD CONSTRAINT role_permission_pkey PRIMARY KEY (id);


--
-- Name: user_acount user_acount_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_acount
    ADD CONSTRAINT user_acount_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

