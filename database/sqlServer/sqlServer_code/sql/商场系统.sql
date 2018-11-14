--作者：陈家儒
--时间：2015-12-15
--功能：一个商店的数据库
create database shoop;

--goods表
create table goods
(
goodsId   nvarchar(50) primary key,
goodsName nvarchar(30) not null,
unitprice numeric(10,2) check(unitprice>0),
category  nvarchar(10) check(category in('日用品','食品')),--
provider  nvarchar(50)--
);
insert into goods values (3,'庄庆望发反反复复芳',10.99,'回火','甜美');
drop table goods;
--customer表
create table customer
(
customerId nvarchar(50) primary key,
cstname    nvarchar(30) not null,
address    nvarchar(50),
email      nvarchar(30) unique,
sex        nchar(1) check(sex in('男','女')) default('男'),
cardId     nvarchar(20)
);
--purchse表
create table purchse
(
customerId nvarchar(50) foreign key references customer(customerId),
goodsId    nvarchar(50) foreign key references goods(goodsId),
nums       int check(nums>0)
);