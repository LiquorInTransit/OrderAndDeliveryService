insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (1, 1, 1000000, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (1, 1, 311787, 2), (2, 1, 7880, 1), (3, 1, 624544, 5);
insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (2, 1, 1000000, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (4, 2, 311787, 2), (5, 2, 7880, 1), (6, 2, 624544, 5);
insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (3, 1, 1000000, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (7, 3, 311787, 2), (8, 3, 7880, 1), (9, 3, 624544, 5);

insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (4, 2, 1000000, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (10, 4, 311787, 2), (11, 4, 7880, 1), (12, 4, 624544, 5);
insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (5, 2, 1000000, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (13, 5, 311787, 2), (14, 5, 7880, 1), (15, 5, 624544, 5);
insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (6, 2, 1000000, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (16, 6, 311787, 2), (17, 6, 7880, 1), (18, 6, 624544, 5);

insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (7, 3, 17.50, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (19, 7, 311787, 2), (20, 7, 7880, 1), (21, 7, 624544, 5);
insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (8, 3, 25.37, 1, '2017-10-05 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (22, 8, 311787, 2), (23, 8, 7880, 1), (24, 8, 624544, 5);

insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (9, 4, 1000000, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (25, 9, 311787, 2);
insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (10, 4, 1000000, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (26, 10, 311787, 2), (27, 10, 7880, 1);

insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (11, 5, 1000000, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (28, 11, 311787, 2), (29, 11, 7880, 1);
insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (12, 5, 1000000, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (30, 12, 311787, 2), (31, 12, 7880, 1), (32, 12, 624544, 5);
insert into LCBO_ORDER (id, customer_id, total, status, created_at) values (13, 5, 1000000, 1, '2017-10-10 21:11:23.539');
insert into LINE_ITEM (id, order_id, product_id, qty) values (33, 13, 311787, 2);

/*Add Quotes*/
INSERT INTO QUOTE (ID, CUSTOMER_ID, ESTIMATED_AT, DROPOFF_CUSTOMER_ID, DROPOFF_CUSTOMER_NAME, DROPOFF_DROPOFFETA, DROPOFF_LOCATION_ADDRESS, DROPOFF_LOCATION_CITY, DROPOFF_LOCATION_LATITUDE, DROPOFF_LOCATION_LONGITUDE, DROPOFF_LOCATION_POSTAL_CODE, PICKUP_PICKUPETA, PICKUP_STORE_ID, PICKUP_STORE_LOCATION_ADDRESS, PICKUP_STORE_LOCATION_CITY, PICKUP_STORE_LOCATION_LATITUDE, PICKUP_STORE_LOCATION_LONGITUDE, PICKUP_STORE_LOCATION_POSTAL_CODE, FEE, DROPOFF_ETA)
VALUES (1, 1, '2017-10-10 21:11:23.539', 1, 'Chris', 10, '23 Crestwood Dr.', 'Cambridge', 43.3440436, -80.3302874, 'N1S3N8', 5, 382, '#45 - 130 Cedar Street', 'Cambridge', 43.3527, -80.3297, 'N1S1W4', 5, 10),
	   (2, 1, '2017-10-10 21:11:23.539', 1, 'Chris', 10, '23 Crestwood Dr.', 'Cambridge', 43.3440436, -80.3302874, 'N1S3N8', 5, 382, '#45 - 130 Cedar Street', 'Cambridge', 43.3527, -80.3297, 'N1S1W4', 5, 10),
	   (3, 1, '2017-10-10 21:11:23.539', 1, 'Chris', 10, '23 Crestwood Dr.', 'Cambridge', 43.3440436, -80.3302874, 'N1S3N8', 5, 382, '#45 - 130 Cedar Street', 'Cambridge', 43.3527, -80.3297, 'N1S1W4', 5, 10),
	   (4, 2, '2017-10-10 21:11:23.539', 2, 'Phil', 10, '199 Queen Mary Dr.', 'Oakville', 43.4443912, -79.675913, 'L6K3K7', 5, 148, '270 North Service Road West', 'Oakville', 43.4459, -79.7046, 'L6M2R8', 5, 10),
	   (5, 2, '2017-10-10 21:11:23.539', 2, 'Phil', 10, '199 Queen Mary Dr.', 'Oakville', 43.4443912, -79.675913, 'L6K3K7', 5, 148, '270 North Service Road West', 'Oakville', 43.4459, -79.7046, 'L6M2R8', 5, 10),
	   (6, 2, '2017-10-10 21:11:23.539', 2, 'Phil', 10, '199 Queen Mary Dr.', 'Oakville', 43.4443912, -79.675913, 'L6K3K7', 5, 148, '270 North Service Road West', 'Oakville', 43.4459, -79.7046, 'L6M2R8', 5, 10),
	   (7, 3, '2017-10-10 21:11:23.539', 3, 'Dan', 10, '1510 Sixth Line', 'Oakville', 43.4651925, -79.7105159, 'L6H2P2', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 10),
	   (8, 3, '2017-10-10 21:11:23.539', 3, 'Dan', 10, '1510 Sixth Line', 'Oakville', 43.4651925, -79.7105159, 'L6H2P2', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 10),
	   (9, 4, '2017-10-10 21:11:23.539', 4, 'Nadine', 10, '1430 Trafalgar Rd', 'Oakville', 43.469015, -79.698624, 'L6H2L1', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 10),
	   (10, 5, '2017-10-10 21:11:23.539', 4, 'Nadine', 10, '1430 Trafalgar Rd', 'Oakville', 43.469015, -79.698624, 'L6H2L1', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 10),
	   (11, 5, '2017-10-10 21:11:23.539', 5, 'John', 10, '1070 McCraney St E,', 'Oakville', 43.4643954, -79.7023737, 'L6H2R6', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 10),
	   (12, 5, '2017-10-10 21:11:23.539', 5, 'John', 10, '1070 McCraney St E,', 'Oakville', 43.4643954, -79.7023737, 'L6H2R6', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 10),
	   (13, 5, '2017-10-10 21:11:23.539', 5, 'John', 10, '1070 McCraney St E,', 'Oakville', 43.4643954, -79.7023737, 'L6H2R6', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 10);

/*Add Deliveries*/
INSERT INTO DELIVERY (ID, ORDER_ID, QUOTE_ID, DRIVER_ID, DRIVER_HOLD, CREATED_AT, DROPOFF_CUSTOMER_ID, DROPOFF_CUSTOMER_NAME, DROPOFF_DROPOFFETA, DROPOFF_LOCATION_ADDRESS, DROPOFF_LOCATION_CITY, DROPOFF_LOCATION_LATITUDE, DROPOFF_LOCATION_LONGITUDE, DROPOFF_LOCATION_POSTAL_CODE, PICKUP_PICKUPETA, PICKUP_STORE_ID, PICKUP_STORE_LOCATION_ADDRESS, PICKUP_STORE_LOCATION_CITY, PICKUP_STORE_LOCATION_LATITUDE, PICKUP_STORE_LOCATION_LONGITUDE, PICKUP_STORE_LOCATION_POSTAL_CODE, FEE, STATUS, TRACKING_ID)
VALUES (1, 1, 1, 3, 3, '2017-10-10 21:11:23.539', 1, 'Chris', 10, '23 Crestwood Dr.', 'Cambridge', 43.3440436, -80.3302874, 'N1S3N8', 5, 382, '#45 - 130 Cedar Street', 'Cambridge', 43.3527, -80.3297, 'N1S1W4', 5, 1, '6d0df366-3aa9-4e2f-9099-7440bd47b4d9'), 
       (2, 2, 2, 2, 2, '2017-10-10 21:11:23.539', 1, 'Chris', 10, '23 Crestwood Dr.', 'Cambridge', 43.3440436, -80.3302874, 'N1S3N8', 5, 382, '#45 - 130 Cedar Street', 'Cambridge', 43.3527, -80.3297, 'N1S1W4', 5, 1, '6c10a89d-b9a5-4234-abd3-a9db8823cdd9'),
       (3, 3, 3, 3, 3, '2017-10-10 21:11:23.539', 1, 'Chris', 10, '23 Crestwood Dr.', 'Cambridge', 43.3440436, -80.3302874, 'N1S3N8', 5, 382, '#45 - 130 Cedar Street', 'Cambridge', 43.3527, -80.3297, 'N1S1W4', 5, 1, '2886f2de-983e-4d44-8664-a7bed9849139'), 
       (4, 4, 4, 1, 1, '2017-10-10 21:11:23.539', 2, 'Phil', 10, '199 Queen Mary Dr.', 'Oakville', 43.4443912, -79.675913, 'L6K3K7', 5, 148, '270 North Service Road West', 'Oakville', 43.4459, -79.7046, 'L6M2R8', 5, 1, '80f149ab-00b3-46f9-be7f-1824b426f41e'),
       (5, 5, 5, 3, 3, '2017-10-10 21:11:23.539', 2, 'Phil', 10, '199 Queen Mary Dr.', 'Oakville', 43.4443912, -79.675913, 'L6K3K7', 5, 148, '270 North Service Road West', 'Oakville', 43.4459, -79.7046, 'L6M2R8', 5, 1, '3a9fefac-42b9-4612-8263-07233ad67c55'), 
       (6, 6, 6, 1, 1, '2017-10-10 21:11:23.539', 2, 'Phil', 10, '199 Queen Mary Dr.', 'Oakville', 43.4443912, -79.675913, 'L6K3K7', 5, 148, '270 North Service Road West', 'Oakville', 43.4459, -79.7046, 'L6M2R8', 5, 1, '97e00acb-4023-4565-a5d3-326b27ee0f2e'),
       (7, 7, 7, 2, 2, '2017-10-10 21:11:23.539', 3, 'Dan', 10, '1510 Sixth Line', 'Oakville', 43.4651925, -79.7105159, 'L6H2P2', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 1, '4e62380b-fbe9-4e62-a3ad-173bb8b2243d'),
       (8, 8, 8, 2, 2, '2017-10-10 21:11:23.539', 3, 'Dan', 10, '1510 Sixth Line', 'Oakville', 43.4651925, -79.7105159, 'L6H2P2', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 1, '345661d1-6b6d-4ef1-afc0-b8397037c637'),
       (9, 9, 9, 2, 2, '2017-10-10 21:11:23.539', 4, 'Nadine', 10, '1430 Trafalgar Rd', 'Oakville', 43.469015, -79.698624, 'L6H2L1', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 1, 'e734a881-755e-4387-ae4d-c5bbebc56bfb'),
       (10, 10, 10, 2, 2, '2017-10-10 21:11:23.539', 4, 'Nadine', 10, '1430 Trafalgar Rd', 'Oakville', 43.469015, -79.698624, 'L6H2L1', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 1, '23e26b55-365e-4c99-8bdd-0218ad456153'),
       (11, 11, 11, 2, 2, '2017-10-10 21:11:23.539', 5, 'John', 10, '1070 McCraney St E,', 'Oakville', 43.4643954, -79.7023737, 'L6H2R6', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 1, '0975ddae-c7ae-4ec9-9ea8-0011336f6861'),
       (12, 12, 12, 2, 2, '2017-10-10 21:11:23.539', 5, 'John', 10, '1070 McCraney St E,', 'Oakville', 43.4643954, -79.7023737, 'L6H2R6', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 1, 'f0e072ab-0098-4fbc-8ef4-5e14ddb921c4'),
       (13, 13, 13, 1, 1, '2017-10-10 21:11:23.539', 5, 'John', 10, '1070 McCraney St E,', 'Oakville', 43.4643954, -79.7023737, 'L6H2R6', 5, 445, '251 Oak Walk Dr.', 'Oakville', 43.4851, -79.7199, 'L6H6M3', 5, 1, 'a6ebbf5d-21b0-4ecb-85d1-ee2d75defe3f');
	   
