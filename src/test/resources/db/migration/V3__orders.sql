create table orders(
order_id bigint auto_increment,
user_id bigint,
product_id bigint,
product_amount bigint,
order_date datetime,
constraint pk_orders primary key(order_id)
);