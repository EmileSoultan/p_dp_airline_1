-- changeset olga_alikulieva:1.14.3

CREATE TABLE account
(
    id            BIGSERIAL PRIMARY KEY,
    first_name               VARCHAR(128)    NOT NULL,
    last_name                VARCHAR(128)    NOT NULL,
    birth_date               date            NOT NULL,
    phone_number             VARCHAR(64)     NOT NULL,
    email                     VARCHAR(255)   NOT NULL,
    password                  VARCHAR(255)   NOT NULL,
    security_question         VARCHAR(255)   NOT NULL,
    answer_question           VARCHAR(255)   NOT NULL
);
