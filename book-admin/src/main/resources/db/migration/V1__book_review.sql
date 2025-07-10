create table if not exists users
(
    id            bigint unsigned auto_increment  not null,
    username      varchar(75)                     not null,
    email         varchar(75)                     null,
    password      varchar(255)                    not null,
    gender        enum ('Male', 'Female','Other') not null default 'Other',
    phone         varchar(50) unique              not null,
    date_of_birth varchar(160)                    null,
    role_id       bigint unsigned                 not null,
    status        boolean                         not null default 0,
    created_at    timestamp                       not null default current_timestamp,
    updated_at    timestamp                       null     default current_timestamp on update current_timestamp,
    deleted_at    timestamp                       null,
    created_by    varchar(50)                     null,
    updated_by    varchar(50)                     null,
    deleted_by    varchar(50)                     null,

    constraint pk_users primary key (id)
);
create table if not exists roles
(
    id         bigint unsigned auto_increment not null,
    name       varchar(75)                    not null,
    code       varchar(75)                    not null,
    created_at timestamp                      not null default current_timestamp,
    updated_at timestamp                      null     default current_timestamp on update current_timestamp,
    deleted_at timestamp                      null,
    created_by varchar(50)                    null,
    updated_by varchar(50)                    null,
    deleted_by varchar(50)                    null,

    constraint pk_roles primary key (id)
);

create table if not exists permissions
(
    id   bigint unsigned auto_increment not null,
    name varchar(75)                    not null,
    constraint pk_permissions primary key (id)
);

create table if not exists role_has_permissions
(
    role_id       bigint unsigned not null,
    permission_id bigint unsigned not null,

    constraint pk_role_has_permission primary key (role_id, permission_id)
);

alter table users
    add constraint fk_users_on_roles foreign key (role_id) references roles (id);
alter table role_has_permissions
    add constraint fk_rhp_on_permissions foreign key (permission_id) references permissions (id);
alter table role_has_permissions
    add constraint fk_rhp_on_roles foreign key (role_id) references roles (id);