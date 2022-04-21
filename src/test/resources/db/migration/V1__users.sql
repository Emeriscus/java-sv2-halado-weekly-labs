create table users(
user_id bigint auto_increment,
email varchar(255),
salt varchar(255),
secure_password varchar(255),
constraint pk_users primary key(user_id)
);