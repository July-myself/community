create table tbl_user
(
    id            int auto_increment
        primary key,
    account_id    varchar(100) ,
    name          varchar(50)  ,
    token         char(36)     ,
    time_create   bigint       ,
    time_modified bigint
);
