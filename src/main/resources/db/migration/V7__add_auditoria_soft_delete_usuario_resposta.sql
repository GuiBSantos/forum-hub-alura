ALTER TABLE respostas
    ADD COLUMN data_atualizacao TIMESTAMP NULL;

ALTER TABLE respostas
    ADD COLUMN data_inativacao TIMESTAMP NULL;

ALTER TABLE respostas
    ADD COLUMN inativado_por BIGINT NULL;

ALTER TABLE respostas
    ADD CONSTRAINT fk_respostas_inativado_por FOREIGN KEY (inativado_por) REFERENCES usuario(id);