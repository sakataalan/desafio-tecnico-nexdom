CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    product_type VARCHAR(255),
    supplier_price DECIMAL(10, 2),
    stock_quantity INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);