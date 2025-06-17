ALTER TABLE topicos
    ADD COLUMN data_atualizacao TIMESTAMP NULL;

ALTER TABLE topicos
    ADD COLUMN data_inativacao TIMESTAMP NULL;

ALTER TABLE topicos
    ADD COLUMN inativado_por BIGINT NULL;

ALTER TABLE topicos
    ADD CONSTRAINT fk_topico_inativado_por FOREIGN KEY (inativado_por) REFERENCES usuario(id);