CREATE TABLE transacoes
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    tipo_transacao   VARCHAR(30),
    valor            DECIMAL(19, 2)                          NOT NULL,
    data             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    id_conta         BIGINT                                  NOT NULL,
    id_conta_destino BIGINT,
    CONSTRAINT pk_transacoes PRIMARY KEY (id)
);

ALTER TABLE transacoes
    ADD CONSTRAINT FK_TRANSACOES_ON_ID_CONTA FOREIGN KEY (id_conta) REFERENCES contas (id);

ALTER TABLE transacoes
    ADD CONSTRAINT FK_TRANSACOES_ON_ID_CONTA_DESTINO FOREIGN KEY (id_conta_destino) REFERENCES contas (id);