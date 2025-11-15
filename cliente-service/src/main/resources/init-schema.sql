DROP SCHEMA IF EXISTS cliente CASCADE;

CREATE SCHEMA cliente;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE cliente.clientes
(
    id uuid NOT NULL,
    nome_usuario character varying COLLATE pg_catalog."default" NOT NULL,
    primeiro_nome character varying COLLATE pg_catalog."default" NOT NULL,
    sobrenome character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT clientes_pkey PRIMARY KEY (id)
);

DROP MATERIALIZED VIEW IF EXISTS cliente.order_cliente_m_view;

CREATE MATERIALIZED VIEW cliente.order_cliente_m_view
TABLESPACE pg_default
AS
 SELECT id,
    nome_usuario,
    primeiro_nome,
    sobrenome
   FROM cliente.clientes
WITH DATA;

refresh materialized VIEW cliente.order_cliente_m_view;

DROP function IF EXISTS cliente.refresh_order_cliente_m_view;

CREATE OR replace function cliente.refresh_order_cliente_m_view()
returns trigger
AS '
BEGIN
    refresh materialized VIEW cliente.order_cliente_m_view;
    return null;
END;
'  LANGUAGE plpgsql;

DROP trigger IF EXISTS refresh_order_cliente_m_view ON cliente.clientes;

CREATE trigger refresh_order_cliente_m_view
after INSERT OR UPDATE OR DELETE OR truncate
ON cliente.clientes FOR each statement
EXECUTE PROCEDURE cliente.refresh_order_cliente_m_view();