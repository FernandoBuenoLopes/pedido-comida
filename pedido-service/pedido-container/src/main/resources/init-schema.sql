DROP SCHEMA IF EXISTS "pedido" CASCADE;

CREATE SCHEMA "pedido";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS pedido_status;
CREATE TYPE pedido_status AS ENUM ('PENDENTE', 'PAGO', 'APROVADO', 'CANCELADO', 'CANCELANDO');

DROP TABLE IF EXISTS "pedido".pedidos CASCADE;

CREATE TABLE "pedido".pedidos
(
    id uuid NOT NULL,
    cliente_id uuid NOT NULL,
    restaurante_id uuid NOT NULL,
    rastreamento_id uuid NOT NULL,
    preco numeric(10,2) NOT NULL,
    pedido_status pedido_status NOT NULL,
    mensagens_falha character varying COLLATE pg_catalog."default",
    CONSTRAINT pedidos_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "pedido".pedido_itens CASCADE;

CREATE TABLE "pedido".pedido_itens
(
    id bigint NOT NULL,
    pedido_id uuid NOT NULL,
    produto_id uuid NOT NULL,
    preco numeric(10,2) NOT NULL,
    quantidade integer NOT NULL,
    sub_total numeric(10,2) NOT NULL,
    CONSTRAINT pedido_itens_pkey PRIMARY KEY (id, pedido_id)
);

ALTER TABLE "pedido".pedido_itens
    ADD CONSTRAINT "FK_PEDIDO_ID" FOREIGN KEY (pedido_id)
    REFERENCES "pedido".pedidos (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    NOT VALID;

DROP TABLE IF EXISTS "pedido".pedido_endereco CASCADE;

CREATE TABLE "pedido".pedido_endereco
(
    id uuid NOT NULL,
    pedido_id uuid UNIQUE NOT NULL,
    rua character varying COLLATE pg_catalog."default" NOT NULL,
    cep character varying COLLATE pg_catalog."default" NOT NULL,
    cidade character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pedido_endereco_pkey PRIMARY KEY (id, pedido_id)
);

ALTER TABLE "pedido".pedido_endereco
    ADD CONSTRAINT "FK_PEDIDO_ID" FOREIGN KEY (pedido_id)
    REFERENCES "pedido".pedidos (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    NOT VALID;

DROP TYPE IF EXISTS saga_status;
CREATE TYPE saga_status AS ENUM ('INICIADO', 'FALHO', 'SUCESSO', 'PROCESSANDO', 'COMPENSANDO', 'COMPENSADO');

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('INICIADO', 'COMPLETO', 'FALHO');

DROP TABLE IF EXISTS "pedido".pagamento_outbox CASCADE;

CREATE TABLE "pedido".pagamento_outbox
(
    id uuid NOT NULL,
    saga_id uuid NOT NULL,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    processado_em TIMESTAMP WITH TIME ZONE,
    type character varying COLLATE pg_catalog."default" NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    saga_status saga_status NOT NULL,
    pedido_status pedido_status NOT NULL,
    version integer NOT NULL,
    CONSTRAINT pagamento_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX "pagamento_outbox_saga_status"
    ON "pedido".pagamento_outbox
    (type, outbox_status, saga_status);

--CREATE UNIQUE INDEX "pagamento_outbox_saga_id"
--    ON "pedido".pagamento_outbox
--    (type, saga_id, saga_status);

DROP TABLE IF EXISTS "pedido".restaurante_aprovacao_outbox CASCADE;

CREATE TABLE "pedido".restaurante_aprovacao_outbox
(
    id uuid NOT NULL,
    saga_id uuid NOT NULL,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    processado_em TIMESTAMP WITH TIME ZONE,
    type character varying COLLATE pg_catalog."default" NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    saga_status saga_status NOT NULL,
    pedido_status pedido_status NOT NULL,
    version integer NOT NULL,
    CONSTRAINT restaurante_aprovacao_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX "restaurante_aprovacao_outbox_saga_status"
    ON "pedido".restaurante_aprovacao_outbox
    (type, outbox_status, saga_status);

--CREATE UNIQUE INDEX "restaurante_aprovacao_outbox_saga_id"
--    ON "pedido".restaurante_aprovacao_outbox
--    (type, saga_id, saga_status);

DROP TABLE IF EXISTS "pedido".clientes CASCADE;

CREATE TABLE "pedido".clientes
(
    id uuid NOT NULL,
    username character varying COLLATE pg_catalog."default" NOT NULL,
    first_name character varying COLLATE pg_catalog."default" NOT NULL,
    last_name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT clientes_pkey PRIMARY KEY (id)
);