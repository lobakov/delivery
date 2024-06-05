CREATE SCHEMA IF NOT EXISTS delivery;

CREATE TABLE IF NOT EXISTS delivery.t_courier (
    id                  UUID            NOT NULL PRIMARY KEY,
    version             INT             DEFAULT 0 NOT NULL,
    name                VARCHAR(128)    NOT NULL,
    status              VARCHAR(64)     NOT NULL,
    current_location    VARCHAR(64)     NOT NULL,
    transport           INT             NOT NULL
);

CREATE TABLE IF NOT EXISTS delivery.t_order (
    id          UUID           NOT NULL PRIMARY KEY,
    version     INT            DEFAULT 0 NOT NULL,
    deliver_to  VARCHAR(64)    NOT NULL,
    status      VARCHAR(64)    NOT NULL,
    weight      INT            NOT NULL,
    courier_id  UUID           REFERENCES delivery.t_courier (id);
);

CREATE INDEX courier_fk_idx ON delivery.t_order (courier_id) WHERE courier_id IS NOT NULL;

INSERT INTO delivery.t_courier(
    id, name, transport, current_location, status)
    VALUES ('bf79a004-56d7-4e5f-a21c-0a9e5e08d10d', 'Петя', 1, 1, 3, 'READY');

INSERT INTO delivery.t_courier(
    id, name, transport, current_location, status)
    VALUES ('a9f7e4aa-becc-40ff-b691-f063c5d04015', 'Оля', 1, '3, 2', 'READY');

INSERT INTO delivery.t_courier(
    id, name, transport, current_location, status)
    VALUES ('db18375d-59a7-49d1-bd96-a1738adcee93', 'Ваня', 2, '4, 5', 'READY');

INSERT INTO delivery.t_courier(
    id, name, transport, current_location, status)
    VALUES ('e7c84de4-3261-476a-9481-fb6be211de75', 'Маша', 2, '1, 8', 'READY');

INSERT INTO delivery.t_courier(
    id, name, transport, current_location, status)
    VALUES ('407f68be-5adf-4e72-81bc-b1d8e9574cf8', 'Игорь', 3, '7, 9', 'READY');

INSERT INTO delivery.t_courier(
    id, name, transport, current_location, status)
    VALUES ('006e6c66-087e-4a27-aa59-3c0a2bc945c5', 'Даша', 3, '5, 5', 'READY');

INSERT INTO delivery.t_courier(
    id, name, transport, current_location, status)
    VALUES ('40d50b82-ce79-4cde-8ce1-21883f466038', 'Сережа', 4, '7, 3', 'READY');

INSERT INTO delivery.t_courier(
    id, name, transport, current_location, status)
    VALUES ('18e5ba41-6710-4143-9808-704e88e94bd9', 'Катя', 4, '6, 9', 'READY');
