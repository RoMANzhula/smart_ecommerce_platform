CREATE TABLE payments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id VARCHAR(255),
    payment_status VARCHAR(255),
    amount DOUBLE
);
