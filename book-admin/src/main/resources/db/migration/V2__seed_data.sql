insert into roles (id, name, code, created_at, updated_at, deleted_at, created_by, updated_by, deleted_by)
values (1, 'admin', 'ADMIN', current_timestamp, null, null, 'admin', null, null),
       (2, 'user', 'USER', current_timestamp, null, null, 'admin', null, null),
       (3, 'super-admin', 'SUPERADMIN', current_timestamp, null, null, 'super-admin', null, null);

insert into permissions (id, name)
values (1, 'VIEW_ADMIN'),
       (2, 'LIST_ADMIN'),
       (3, 'ADD_ADMIN'),
       (4, 'UPDATE_ADMIN'),
       (5, 'DELETE_ADMIN'),
       (6, 'VIEW_USER'),
       (7, 'LIST_USER'),
       (8, 'CREATE_USER'),
       (9, 'UPDATE_USER'),
       (10, 'DELETE_USER'),
       (11, 'VIEW_SUPERADMIN'),
       (12, 'LIST_SUPERADMIN'),
       (13, 'CREATE_SUPERADMIN'),
       (14, 'UPDATE_SUPERADMIN'),
       (15, 'DELETE_SUPERADMIN');

insert into role_has_permissions (role_id, permission_id)
values (1, 1)
     , (1, 2)
     , (1, 3)
     , (1, 4)
     , (1, 5)
     , (1, 6)
     , (1, 7)
     , (1, 8)
     , (1, 9)
     , (1, 10)
     , (2, 6)
     , (2, 7)
     , (2, 8)
     , (2, 9)
     , (2, 10)
     , (3, 1)
     , (3, 2)
     , (3, 3)
     , (3, 4)
     , (3, 5)
     , (3, 6)
     , (3, 7)
     , (3, 8)
     , (3, 9)
     , (3, 10)
     , (3, 11)
     , (3, 12)
     , (3, 13)
     , (3, 14)
     , (3, 15);

insert into `book-review`.users (id, username, email, password, gender, phone, date_of_birth, role_id, status,
                                 created_at, updated_at, deleted_at, created_by, updated_by, deleted_by)
values (1, 'admin', 'admin123@gmail.com', '', 'Male', '096990789', '2001-04-15', 1, true,
        current_timestamp, null, null, 'admin', null, null);