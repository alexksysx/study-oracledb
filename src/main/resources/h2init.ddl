-- create bus test_user password 'test_user';
-- grant alter any schema to test_user;
create schema test_user;
create table test_user.buses (
    id numeric(10) primary key,
    bus_name varchar2(255) not null,
    bus_number varchar2(255) not null,
    bus_model varchar2(255) not null
);
