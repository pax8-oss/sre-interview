CREATE TABLE products
(
    id          UUID           NOT NULL,
    name        VARCHAR(255)   NOT NULL,
    description TEXT           NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    created     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE products
    ADD CONSTRAINT uc_products_name UNIQUE (name);

DO
$$
BEGIN
FOR i IN 1..500 LOOP
        INSERT INTO products (id, name, description, price, created, updated)
        VALUES (
            gen_random_uuid(),
            'Product ' || i,
            'Description for Product ' || i,
            10 + random() * 90,
            now(),
            now()
        );
END LOOP;
END
$$;
