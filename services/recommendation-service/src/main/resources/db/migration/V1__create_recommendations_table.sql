CREATE SEQUENCE recommendations_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE recommendations (
    id BIGINT PRIMARY KEY DEFAULT nextval('recommendations_seq'),
    user_id VARCHAR(255),
    product_id VARCHAR(255),
    score DOUBLE PRECISION,
    action VARCHAR(255)
);