create table LCBO_ORDER (
	id INTEGER PRIMARY KEY,
	customer_id INTEGER,
	status VARCHAR(60),
	total INTEGER,
	created_at TIMESTAMP
);

create table LINE_ITEM (
	id INTEGER PRIMARY KEY,
	order_id INTEGER,
	product_id INTEGER,
	product_name VARCHAR(255),
	image_thumb_url VARCHAR(300),
	price DECIMAL(6,2),
	qty INTEGER
);