CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6)          DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id      BINARY(16) PRIMARY KEY,
    type            varchar(1) CHECK ( type IN ('1', '2') ) NOT NULL,
    discount_degree INT UNSIGNED                            NOT NULL,
    owner_id        BINARY(16) REFERENCES customers (customer_id)
);
