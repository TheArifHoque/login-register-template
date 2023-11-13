INSERT INTO authority_table
VALUES(1, 'ROLE_ADMIN');

INSERT INTO authority_table
VALUES(2, 'ROLE_USER');

INSERT INTO user_table (user_id, first_name, last_name, username, password)
VALUES(101L, 'Arif', 'Hoque', 'ArifHoque', '$2a$10$TUPqUidYreC1GmXpE6uNFevYLnNatn648J9Ijl4E7qp.NOevTKhYq');
-- password: iamadmin

INSERT INTO user_table (user_id, first_name, last_name, username, password)
VALUES(102L, 'Sad', 'Man', 'sadman', '$2a$10$lutvveNDlDKfLY9EAmunf.XJjIptyomoYooPy5PeKhvttj04BZ3qu');
-- password: iamuser

INSERT INTO user_authority (user_id, authority_id)
VALUES(101L, 1);

INSERT INTO user_authority (user_id, authority_id)
VALUES(101L, 2);

INSERT INTO user_authority (user_id, authority_id)
VALUES(102L, 2);