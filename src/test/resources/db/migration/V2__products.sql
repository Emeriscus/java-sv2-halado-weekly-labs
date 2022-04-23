create table products(
product_id bigint auto_increment,
product_name varchar(255),
price bigint,
stock bigint default 0,
constraint pk_products primary key(product_id)
);