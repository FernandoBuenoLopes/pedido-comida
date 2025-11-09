DROP SCHEMA IF EXISTS restaurante CASCADE;

CREATE SCHEMA restaurante;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS restaurante.restaurantes CASCADE;

CREATE TABLE restaurante.restaurantes
(
    id uuid NOT NULL,
    nome character varying COLLATE pg_catalog."default" NOT NULL,
    ativo boolean NOT NULL,
    CONSTRAINT restaurantes_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS aprovacao_status;

CREATE TYPE aprovacao_status AS ENUM ('APROVADO', 'REJEITADO');

DROP TABLE IF EXISTS restaurante.pedido_aprovacao CASCADE;

CREATE TABLE restaurante.pedido_aprovacao
(
    id uuid NOT NULL,
    restaurante_id uuid NOT NULL,
    pedido_id uuid NOT NULL,
    status aprovacao_status NOT NULL,
    CONSTRAINT pedido_aprovacao_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS restaurante.produtos CASCADE;

CREATE TABLE restaurante.produtos
(
    id uuid NOT NULL,
    nome character varying COLLATE pg_catalog."default" NOT NULL,
    preco numeric(10,2) NOT NULL,
    disponivel boolean NOT NULL,
    CONSTRAINT produtos_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS restaurante.restaurante_produtos CASCADE;

CREATE TABLE restaurante.restaurante_produtos
(
    id uuid NOT NULL,
    restaurante_id uuid NOT NULL,
    produto_id uuid NOT NULL,
    CONSTRAINT restaurante_produtos_pkey PRIMARY KEY (id)
);

ALTER TABLE restaurante.restaurante_produtos
    ADD CONSTRAINT "FK_RESTAURANTE_ID" FOREIGN KEY (restaurante_id)
    REFERENCES restaurante.restaurantes (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE restaurante.restaurante_produtos
    ADD CONSTRAINT "FK_PRODUTO_ID" FOREIGN KEY (produto_id)
    REFERENCES restaurante.produtos (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE RESTRICT
    NOT VALID;

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('INICIADO', 'COMPLETO', 'FALHO');

DROP TABLE IF EXISTS restaurante.pedido_outbox CASCADE;

CREATE TABLE restaurante.pedido_outbox
(
    id uuid NOT NULL,
    saga_id uuid NOT NULL,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    processado_em TIMESTAMP WITH TIME ZONE,
    tipo character varying COLLATE pg_catalog."default" NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    aprovacao_status aprovacao_status NOT NULL,
    version integer NOT NULL,
    CONSTRAINT pedido_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX "restaurante_pedido_outbox_saga_status"
    ON "restaurante".pedido_outbox
    (tipo, aprovacao_status);

CREATE UNIQUE INDEX "restaurante_pedido_outbox_saga_id"
    ON "restaurante".pedido_outbox
    (tipo, saga_id, aprovacao_status, outbox_status);

DROP MATERIALIZED VIEW IF EXISTS restaurante.pedido_restaurante_m_view;

CREATE MATERIALIZED VIEW restaurante.pedido_restaurante_m_view
TABLESPACE pg_default
AS
 SELECT r.id AS restaurante_id,
    r.nome AS restaurante_nome,
    r.ativo AS restaurante_ativo,
    p.id AS produto_id,
    p.nome AS produto_nome,
    p.preco AS produto_preco,
    p.disponivel AS produto_disponivel
   FROM restaurante.restaurantes r,
    restaurante.produtos p,
    restaurante.restaurante_produtos rp
  WHERE r.id = rp.restaurante_id AND p.id = rp.produto_id
WITH DATA;

refresh materialized VIEW restaurante.pedido_restaurante_m_view;

DROP function IF EXISTS restaurante.refresh_pedido_restaurante_m_view;

CREATE OR replace function restaurante.refresh_pedido_restaurante_m_view()
returns trigger
AS '
BEGIN
    refresh materialized VIEW restaurante.pedido_restaurante_m_view;
    return null;
END;
'  LANGUAGE plpgsql;

DROP trigger IF EXISTS refresh_pedido_restaurante_m_view ON restaurante.restaurante_produtos;

CREATE trigger refresh_pedido_restaurante_m_view
after INSERT OR UPDATE OR DELETE OR truncate
ON restaurante.restaurante_produtos FOR each statement
EXECUTE PROCEDURE restaurante.refresh_pedido_restaurante_m_view();