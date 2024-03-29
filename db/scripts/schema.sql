CREATE SEQUENCE IF NOT EXISTS users_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS users (
   id INTEGER NOT NULL DEFAULT nextval('users_sq'),
   user_name VARCHAR(200) NOT NULL UNIQUE,
   password VARCHAR(200) NOT NULL,
   CONSTRAINT pk_users PRIMARY KEY(id)
);

CREATE SEQUENCE IF NOT EXISTS item_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS item (
   id INTEGER NOT NULL DEFAULT nextval('item_sq'),
   description TEXT NOT NULL,
   created TIMESTAMP DEFAULT now(),
   done BOOLEAN NOT NULL DEFAULT FALSE,
   user_id INTEGER NOT NULL,
   CONSTRAINT pk_item PRIMARY KEY(id),
   CONSTRAINT fk_item_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
