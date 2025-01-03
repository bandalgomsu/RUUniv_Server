CREATE TABLE IF NOT EXISTS users(
                                    id bigint not null AUTO_INCREMENT,
                                    email varchar(50) not null,
    password varchar(255) not null,
    role Enum('USER','GUEST','ADMIN') not null,
    created_at timestamp(6) ,
    updated_at timestamp(6) ,
    primary key (id)
);