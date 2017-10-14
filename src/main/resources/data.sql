insert into LCBO_ORDER (id, customer_id, total, status) values (1, 1, 1000000, 1);
insert into LINE_ITEM (id, order_id, product_id, qty) values (1, 1, 1, 2), (2, 1, 2, 1), (3, 1, 3, 5);
insert into LCBO_ORDER (id, customer_id, total, status) values (2, 1, 1000000, 1);
insert into LINE_ITEM (id, order_id, product_id, qty) values (4, 2, 1, 2), (5, 2, 2, 1), (6, 2, 3, 5);
insert into LCBO_ORDER (id, customer_id, total, status) values (3, 1, 1000000, 1);
insert into LINE_ITEM (id, order_id, product_id, qty) values (7, 3, 1, 2), (8, 3, 2, 1), (9, 3, 3, 5);

insert into LCBO_ORDER (id, customer_id, total, status) values (4, 2, 1000000, 1);
insert into LINE_ITEM (id, order_id, product_id, qty) values (10, 4, 1, 2), (11, 4, 2, 1), (12, 4, 3, 5);
insert into LCBO_ORDER (id, customer_id, total, status) values (5, 2, 1000000, 1);
insert into LINE_ITEM (id, order_id, product_id, qty) values (13, 5, 1, 2), (14, 5, 2, 1), (15, 5, 3, 5);
insert into LCBO_ORDER (id, customer_id, total, status) values (6, 2, 1000000, 1);
insert into LINE_ITEM (id, order_id, product_id, qty) values (16, 6, 1, 2), (17, 6, 2, 1), (18, 6, 3, 5);

insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (7, 3, 17.50, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (19, 7, 1, 2), (20, 7, 2, 1), (21, 7, 3, 5);
insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (8, 3, 25.37, 1, '2017-10-05 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (22, 8, 1, 2), (23, 8, 2, 1), (24, 8, 3, 5);

insert into LCBO_ORDER (id, customer_id, total, status) values (9, 4, 1000000, 1);
insert into LINE_ITEM (id, order_id, product_id, qty) values (25, 9, 1, 2);
insert into LCBO_ORDER (id, customer_id, total, status) values (10, 4, 1000000, 1);
insert into LINE_ITEM (id, order_id, product_id, qty) values (26, 10, 1, 2), (27, 10, 2, 1);

insert into LCBO_ORDER (id, customer_id, total, status) values (11, 5, 1000000, 1);
insert into LINE_ITEM (id, order_id, product_id, qty) values (28, 11, 1, 2), (29, 11, 2, 1);
insert into LCBO_ORDER (id, customer_id, total, status) values (12, 5, 1000000, 1);
insert into LINE_ITEM (id, order_id, product_id, qty) values (30, 12, 1, 2), (31, 12, 2, 1), (32, 12, 3, 5);
insert into LCBO_ORDER (id, customer_id, total, status) values (13, 5, 1000000, 1);
insert into LINE_ITEM (id, order_id, product_id, qty) values (33, 13, 1, 2);
