--���ߣ��¼���
--ʱ�䣺2015-12-15
--���ܣ�һ���̵�����ݿ�
create database shoop;

--goods��
create table goods
(
goodsId   nvarchar(50) primary key,
goodsName nvarchar(30) not null,
unitprice numeric(10,2) check(unitprice>0),
category  nvarchar(10) check(category in('����Ʒ','ʳƷ')),--
provider  nvarchar(50)--
);
insert into goods values (3,'ׯ����������������',10.99,'�ػ�','����');
drop table goods;
--customer��
create table customer
(
customerId nvarchar(50) primary key,
cstname    nvarchar(30) not null,
address    nvarchar(50),
email      nvarchar(30) unique,
sex        nchar(1) check(sex in('��','Ů')) default('��'),
cardId     nvarchar(20)
);
--purchse��
create table purchse
(
customerId nvarchar(50) foreign key references customer(customerId),
goodsId    nvarchar(50) foreign key references goods(goodsId),
nums       int check(nums>0)
);