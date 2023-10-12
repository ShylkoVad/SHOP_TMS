CREATE TABLE IF NOT EXISTS roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS users_roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT FK_USERS_ROLES_USER_ID_USERS_ID FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FK_USERS_ROLES_ROLE_ID_ROLES_ID FOREIGN KEY (role_id) REFERENCES roles (id)
    );

INSERT INTO shop.roles(name) VALUES ('ADMIN');
INSERT INTO shop.roles(name) VALUES ('USER');

INSERT INTO shop.users_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO shop.users_roles(user_id, role_id) VALUES (2, 2);