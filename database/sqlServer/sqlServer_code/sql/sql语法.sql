--作者：陈家儒
--功能：雇员管理系统

--创建雇员系统的数据库
create database helloWord;

--创建部门表
create table dept
(
deptNo int primary key,
deptName nvarchar(30),
loc nvarchar(30)
);
--创建雇员表
create table emp
(
empNo int primary key,                           --1、编号主键
empName nvarchar(30),                            --2、雇员名字
job nvarchar(30),                                --3、雇员职位
mgr int,                                         --4、雇员上级
hireDate datetime,                               --5、雇员入职时间
sal numeric(10,2),                               --6、雇员薪水
comm numeric(10,2),                              --7、雇员奖金
deptNo int foreign key references dept(deptNo)   --8、所在部门，根据需要做成外键
);
drop table emp;

        --往dept表添加记录行
		insert into dept values (10,'财务部','北京');
		insert into dept values (20,'市场部','天津');
		insert into dept values (30,'生产部','广州');
		insert into dept values (40,'销售部','汕尾');
        --往emp表添加数据
		insert into emp values (1,'辜俊凯','财务部长',5,'2015-08-30',1400.13,400.14,10);
		insert into emp values (2,'辜俊杰','市场部长',5,'2015-09-30',1300.13,300.14,20);
		insert into emp values (3,'庄庆望','生产部长',5,'2015-10-30',1200.13,200.14,30);
		insert into emp values (4,'林映莎','销售部长',5,'2015-11-30',1100.13,100.14,40);
		insert into emp values (5,'陈家儒','销售组长',5,'2015-12-30',1000.13,100.14,40);
		insert into emp values (6,'陈家智','销售组长',5,'2015-12-30',1000,100,40);
		insert into emp values (7,'陈木生','销售组长',5,'2015-12-30',1000,null,40);
		insert into emp values (8,'陈启意','销售组长',null,'2015-12-30',1000,null,40);

--1、查看庄庆望的：薪水，职位，所在部门  <原则：能不用*就不要用提高效率>
		select sal,job,deptNo from emp where empName='庄庆望';
--2、查看总共有多少部门 <这里学到了一个不重复的知识点：distinct 字段1,字段2...>
		select distinct deptNo from emp; 
--3、计算所有的雇员的年薪<包含奖金用到一个函数如果奖金是null就让他等于0>
		select empName,sal*13+isnull(comm,0)*13 "年薪" from emp;
--4、查找入职时间在2015-12-2之前的雇员<神奇的时间比较>
		select * from emp where hiredate<'2015-12-2';
--5、查看薪水在1000到1200的雇员情况<sal>=1000 and sal<=1200 相当于 sal between 1000 and 1200>
		select * from emp where sal>=1000 and sal<=1200;
		select * from emp where sal between 1000 and 1200;
--6、模糊查询<like>
		  --1、查找全部姓辜的雇员的姓名和薪水
		  select empName,sal from emp where empName like '__凯%';
--7、批量查询<in==or>
  		  --1、查找编号为1,3的雇员的情况
		  select * from emp where empNo=1 or empNo=3;
		  select * from emp where empNo in(1,3);
		  --2、显示没有上级的人
		  select * from emp where mgr is null;
--8、升序降序<orday by [aso/desc]默认为asc-->>可以按时间，数字，和字符排序>
		  --1、一个条件的升降
		  select * from emp order by sal desc;
		  --2、多个条件的升降，部门升序 and 薪水降序
		  select * from emp order by deptNo,sal desc;
		  --3、统计每人的年薪从高到低<降序>
		select empName,(sal+isnull(comm,0))*13 from emp order by (sal+isnull(comm,0))*13 desc; --这样不好，重复计算了 
		select empName,(sal+isnull(comm,0))*13 "年薪" from emp order by "年薪" desc;
--9、子查询的应用<sql语句是从右左执行的>
		  --1、找到工资最低的那个人的工资和姓名
		  select empName,sal from emp where sal=(select min(sal) from emp);
		  --2、计算出本雇员的总工资和平均工资
		  select sum(sal) "总工资",avg(sal) "平均工资" from emp;
		  --3、把高于平均工资的雇员找出来
		  select empName,sal,(select avg(sal) from emp) "平均工资" from emp where sal>(select avg(sal) from emp);
		  --4、共有多少员工
		  select count(*) "全部员工" from emp;
--10、分组管理<group by、having合用>
		  --1、计算每个部门的平均工资,和本部门的最高工资是谁
		  select deptNo,avg(sal) "平均工资",max(sal) "最高工资" from emp group by deptNo;
		  --2、计算每个部门的每个职位的平均工资 和本职位的最低工资是谁,并排序
		  select deptNo,job,avg(sal) "平均工资",min(sal) "最低工资" from emp group by deptNo,job order by deptNo desc;
		  --3、显示平均工资低于1300的部门和他的平均工资<having是筛选group by分组后的结果>
		  select deptNo,avg(sal) "平均工资",min(sal) "最低工资" from emp group by deptNo having(avg(sal)<1300) order by avg(sal) desc;
		  
		--查看数据表
		select * from dept;
		select * from emp;
		
		
		
		
