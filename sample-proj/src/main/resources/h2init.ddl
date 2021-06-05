-- create user test_user password 'test_user';
-- grant alter any schema to test_user;
create table users (
    id numeric(10) primary key,
    login varchar2(255) not null,
    password varchar2(255) not null,
    email varchar2(255) not null
);
