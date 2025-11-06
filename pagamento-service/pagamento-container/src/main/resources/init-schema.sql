DROP SCHEMA IF EXISTS pagamento CASCADE;

CREATE SCHEMA pagamento;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS pagamento_status;

CREATE TYPE pagamento_status AS ENUM ('COMPLETO', 'CANCELLED', 'PAGAMENTO');

DROP TABLE IF EXISTS "pagamento".pagamentos CASCADE;

CREATE TABLE "pagamento".pagamentos
(
    id uuid NOT NULL,
    cliente_id uuid NOT NULL,
    pedido_id uuid NOT NULL,
    preco numeric(10,2) NOT NULL,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    status pagamento_status NOT NULL,
    CONSTRAINT pagamentos_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "pagamento".credit_entry CASCADE;

CREATE TABLE "pagamento".credit_entry
(
    id uuid NOT NULL,
    cliente_id uuid NOT NULL,
    total_credit_amount numeric(10,2) NOT NULL,
    CONSTRAINT credit_entry_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS tipo_transacao;

CREATE TYPE tipo_transacao AS ENUM ('DEBITO', 'CREDITO');

DROP TABLE IF EXISTS "pagamento".credit_history CASCADE;

CREATE TABLE "pagamento".credit_history
(
    id uuid NOT NULL,
    cliente_id uuid NOT NULL,
    quantia numeric(10,2) NOT NULL,
    tipo tipo_transacao NOT NULL,
    CONSTRAINT credit_history_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('INICIADO', 'COMPLETO', 'PAGAMENTO');

DROP TABLE IF EXISTS "pagamento".pedido_outbox CASCADE;

CREATE TABLE "pagamento".pedido_outbox
(
    id uuid NOT NULL,
    saga_id uuid NOT NULL,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    processado_em TIMESTAMP WITH TIME ZONE,
    tipo character varying COLLATE pg_catalog."default" NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    pagamento_status pagamento_status NOT NULL,
    versao integer NOT NULL,
    CONSTRAINT pedido_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX "pagamento_pedido_outbox_saga_status"
    ON "pagamento".pedido_outbox
    (tipo, pagamento_status);

CREATE UNIQUE INDEX "pagamento_pedido_outbox_saga_id_pagamento_status_outbox_status"
    ON "pagamento".pedido_outbox
    (tipo, saga_id, pagamento_status, outbox_status);