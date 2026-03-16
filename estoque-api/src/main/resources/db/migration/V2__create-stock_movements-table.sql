CREATE TABLE stock_movements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    movement_type VARCHAR(20) NOT NULL,
    sale_price DECIMAL(10, 2),
    movement_date TIMESTAMP NOT NULL,
    movement_quantity INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_stock_movements_products FOREIGN KEY (product_id) REFERENCES products(id)
);