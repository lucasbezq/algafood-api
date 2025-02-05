CREATE TABLE foto_produto (
    produto_id BIGINT NOT NULL,
    nome_arquivo VARCHAR(150) NOT NULL,
    descricao VARCHAR(150),
    content_type VARCHAR(80) NOT NULL,
    tamanho INT NOT NULL,

    primary key (produto_id),
    constraint fk_foto_produto_produto foreign key (produto_id) references produto (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8