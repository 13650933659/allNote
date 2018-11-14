--作者：陈家儒
--时间：2016-1-2
--浅谈：java对 sql server 2000的操作
--1.jdbc-odbc的桥连接（Statement/PreparedStatement）
--2.jdbc驱动程序直接操作 sql server 2000
select * from dept;
select * from emp;

select * from dept where deptNo=10 and 1='1';
--这里的e.empNo在java用字典取时用用empNo而不是e.empNo
select e.empNo,e.empName,d.deptName from emp e,dept d where e.deptNo=d.deptNo;

--学校学生管理系统
select * from stus;
delete from stus where stuId in('5','6');

--满汉楼餐饮管理系统
create database Restaurant;

--创建人事资料表《PersonnelFiles》
create table PersonnelFiles
(
empNo nvarchar(32) primary key,
p_name nvarchar(32),
pictureUrl nvarchar(64),
sex nchar(1) check(sex in('男','女')) default('男'),
address nvarchar(32),
hireDate datetime,
idCard nvarchar(32),
backgroud nvarchar(16),
post nvarchar(16),
married nchar(1) check(married in('是','否')) default('否')
);
--创建登录表《Login表》
create table Login
(
un nvarchar(32),
pw nvarchar(32),
empNo nvarchar(32) primary key foreign key references PersonnelFiles(empNo)
);
insert into PersonnelFiles values ('MHL001','刘备','d:/images/lb.jpg','男','蜀国','2000-06-15','44151','本科','主管','是');
insert into PersonnelFiles values ('MHL002','关羽','d:/images/gy.jpg','男','蜀国','2010-05-12','44152','专科','经理','是');
insert into PersonnelFiles values ('MHL003','张飞','d:/images/zf.jpg','男','蜀国','1990-01-10','44153','高中','厨师','否');
insert into PersonnelFiles values ('MHL004','赵云','d:/images/zy.jpg','男','蜀国','1993-01-05','44154','初中','服务员','否');
insert into PersonnelFiles values ('MHL005','黄忠','d:/images/hz.jpg','男','蜀国','1902-04-21','44155','小学','服务员','否');


insert into Login values ('lb','123','MHL001');
insert into Login values ('gy','123','MHL002');
insert into Login values ('zs','123','MHL003');
insert into Login values ('zy','123','MHL004');
insert into Login values ('hz','123','MHL006');
delete from Login where empNo='MHL006';


--创建菜谱数据表
create table Menu
(
m_no nvarchar(32) primary key,
m_name nvarchar(32),
m_price numeric(10,2) check(m_price>0),
m_classes nvarchar(16)
);
insert into Menu values ('M001','番茄炒鸡蛋','50','炒菜类');
insert into Menu values ('M002','炒饭','60','主食类');
insert into Menu values ('M003','八宝粥','70','主食类');
insert into Menu values ('M004','宫保鸡丁','80','热菜类');
insert into Menu values ('M005','叉烧包','90','主食类');

select * from PersonnelFiles;
select * from Login;
select * from Menu;


