show databases;
create database javaproject2024;
use javaproject2024;
create table customers(mobile varchar(10) primary key,cname varchar(40),address varchar(100),city varchar(20),gender varchar(20),dob date,doenroll date);
select * from customers;
create table workers(wname varchar(40) primary key,address varchar(100),mobile varchar(10),splz varchar(200));
select * from workers;
create table measurements(orderid int primary key auto_increment,mobile varchar(10),email varchar(50),dress varchar(100),pic varchar(400),dodel date,qty int,price int,bill int,adv int,pending int,measurements varchar(500),requirements varchar(500),worker varchar(40),doorder date);
select * from measurements;
drop table measurements;
alter table measurements
add status int;