DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
    (100000, '2020-01-30 10:00:00.000000', 'Завтрак', 350),
    (100000, '2020-01-30 12:00:00.000000', 'Обед', 1350),
    (100000, '2020-01-30 19:00:00.000000', 'Ужин', 350),
    (100001, '2020-03-30 10:30:00.000000', 'Завтрак', 600),
    (100001, '2020-03-30 13:00:00.000000', 'Обед', 1250),
    (100001, '2020-04-15 18:00:00.000000', 'Ужин', 1450),
    (100000, '2020-04-15 10:12:00.000000', 'Завтрак', 3350);
