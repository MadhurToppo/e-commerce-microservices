CREATE SEQUENCE  IF NOT EXISTS primary_sequence START WITH 10000 INCREMENT BY 1;

CREATE TABLE inventories (
    id BIGINT NOT NULL,
    sku_code VARCHAR(255),
    quantity INTEGER,
    CONSTRAINT inventories_pkey PRIMARY KEY (id)
);
