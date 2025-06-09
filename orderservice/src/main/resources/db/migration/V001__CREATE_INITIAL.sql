CREATE TABLE orders (
    id char(36) NOT NULL,
    order_number VARCHAR(255) NULL,
    sku_code VARCHAR(255) NULL,
    price numeric(10, 2) NULL,
    quantity INT NULL,
    CONSTRAINT PK_ORDERS PRIMARY KEY (id)
);
