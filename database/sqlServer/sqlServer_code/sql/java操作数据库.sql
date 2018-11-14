--���ߣ��¼���
--ʱ�䣺2016-1-2
--ǳ̸��java�� sql server 2000�Ĳ���
--1.jdbc-odbc�������ӣ�Statement/PreparedStatement��
--2.jdbc��������ֱ�Ӳ��� sql server 2000
select * from dept;
select * from emp;

select * from dept where deptNo=10 and 1='1';
--�����e.empNo��java���ֵ�ȡʱ����empNo������e.empNo
select e.empNo,e.empName,d.deptName from emp e,dept d where e.deptNo=d.deptNo;

--ѧУѧ������ϵͳ
select * from stus;
delete from stus where stuId in('5','6');

--����¥��������ϵͳ
create database Restaurant;

--�����������ϱ�PersonnelFiles��
create table PersonnelFiles
(
empNo nvarchar(32) primary key,
p_name nvarchar(32),
pictureUrl nvarchar(64),
sex nchar(1) check(sex in('��','Ů')) default('��'),
address nvarchar(32),
hireDate datetime,
idCard nvarchar(32),
backgroud nvarchar(16),
post nvarchar(16),
married nchar(1) check(married in('��','��')) default('��')
);
--������¼��Login��
create table Login
(
un nvarchar(32),
pw nvarchar(32),
empNo nvarchar(32) primary key foreign key references PersonnelFiles(empNo)
);
insert into PersonnelFiles values ('MHL001','����','d:/images/lb.jpg','��','���','2000-06-15','44151','����','����','��');
insert into PersonnelFiles values ('MHL002','����','d:/images/gy.jpg','��','���','2010-05-12','44152','ר��','����','��');
insert into PersonnelFiles values ('MHL003','�ŷ�','d:/images/zf.jpg','��','���','1990-01-10','44153','����','��ʦ','��');
insert into PersonnelFiles values ('MHL004','����','d:/images/zy.jpg','��','���','1993-01-05','44154','����','����Ա','��');
insert into PersonnelFiles values ('MHL005','����','d:/images/hz.jpg','��','���','1902-04-21','44155','Сѧ','����Ա','��');


insert into Login values ('lb','123','MHL001');
insert into Login values ('gy','123','MHL002');
insert into Login values ('zs','123','MHL003');
insert into Login values ('zy','123','MHL004');
insert into Login values ('hz','123','MHL006');
delete from Login where empNo='MHL006';


--�����������ݱ�
create table Menu
(
m_no nvarchar(32) primary key,
m_name nvarchar(32),
m_price numeric(10,2) check(m_price>0),
m_classes nvarchar(16)
);
insert into Menu values ('M001','���ѳ�����','50','������');
insert into Menu values ('M002','����','60','��ʳ��');
insert into Menu values ('M003','�˱���','70','��ʳ��');
insert into Menu values ('M004','��������','80','�Ȳ���');
insert into Menu values ('M005','���հ�','90','��ʳ��');

select * from PersonnelFiles;
select * from Login;
select * from Menu;


