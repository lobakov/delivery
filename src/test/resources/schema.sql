CREATE SCHEMA IF NOT EXISTS delivery;

CREATE TABLE IF NOT EXISTS delivery.t_courier (
    id                  UUID    NOT NULL PRIMARY KEY,
    version             BIGINT  NOT NULL,
    name                TEXT    NOT NULL,
    status              TEXT    NOT NULL,
    current_location    TEXT    NOT NULL,
    transport           INT     NOT NULL
);

CREATE TABLE IF NOT EXISTS delivery.t_order (
    id          UUID    NOT NULL PRIMARY KEY,
    version     BIGINT  NOT NULL,
    deliver_to  TEXT    NOT NULL,
    status      TEXT    NOT NULL,
    weight      INT     NOT NULL,
    courier_id  UUID
);
