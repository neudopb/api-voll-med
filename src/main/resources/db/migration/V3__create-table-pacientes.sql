create table pacientes(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    cpf varchar(14) not null unique,
    telefone varchar(20) not null,
    endereco_id bigint not null,

    primary key(id),
    foreign key(endereco_id) references enderecos(id)

);