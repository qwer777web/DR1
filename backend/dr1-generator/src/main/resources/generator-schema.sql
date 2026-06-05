DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    nickname VARCHAR(64) NOT NULL,
    email VARCHAR(128),
    phone VARCHAR(32),
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP,
    update_time TIMESTAMP
);

