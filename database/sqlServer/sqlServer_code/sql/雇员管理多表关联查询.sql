--作者：陈家儒
--时间：2015-12-30
--功能：雇员管理系统的多表查询

--1、显示所有销售部的员工，的姓名，薪水，及所在部门的名称
select e.deptNo,empName,sal from emp "e",dept "d" where deptName='销售部' and e.deptNo=d.deptNo;
--3、显示部门号为40的，部门名，员工姓名和薪水
select d.deptNo,d.deptName,e.empName,e.sal from emp e,dept d where e.deptNo=d.deptNo and e.deptNo=40;
--4、显示每个员工的上级和自己的名字<思路把emp表看成两张表worker和boss>
select b.empName 老板,w.empName 员工 from emp w,emp b where w.mgr=b.empNo;这样不好那个没有上级的不显示
--这里研究一下内连接和左右外链接,内连接用where,外链接用<left join/right join>,on
--内连接
select * from stu s,exam e where s.id1=e.id1;
--外链接
  --左外链接
  select * from stu s left join exam e on s.id1=e.id1;
  --右外链接
  select * from stu s right join exam e on s.id1=e.id1;

--5、子查询
  --1、单行子查询,显示与陈木生同部门的所有员工
  select * from emp where deptNo=(select deptNo from emp where empName='陈木生');
  --2、找出和部门40的职位相同的，员工姓名，岗位，薪水，部门 并且要排除本部门的所有员工《and deptNo!=40》<巧用in/not in>
  select empName,job,sal,deptNo from emp where job in(select distinct job from emp where deptNo=40);
  --3、form子句中的子查询,显示出高于部门平均工资的员工的，姓名，薪水，部门平均工资，和部门编号
  select e.empName,e.sal,tem.myavg,e.deptNo from emp "e",(select avg(sal) "myavg",deptNo from emp group by deptNo) "tem" 
  where e.deptNo=tem.deptNo and e.sal>tem.myavg;
--6、分页查询
  --1、找出第三个到第六个雇员，（按入职时间先后排序）两次排序
  select top 4 * from emp where empNo not in(select top 2 empNo from emp order by hiredate) order by hiredate;




--7、面试题，如何删除一个表重复的记录行,思路
查出十二年前进入公司的员工
select * from emp where datediff(year,hireDate,getdate())>12;
  --《1》 先把重复的记录合并再放到一个临时表,《2》 再删除本表的所有记录要用delete,
  --《3》 用本表再去复制零时表的记录，《4》 再去删掉零时表这是要用drop了
create table cat
(
catId int,
catName nvarchar(30)
)
insert into cat values (2,'rmimi');
select * from cat;

--答案如下：
select distinct * into #tem from cat;
delete from cat;
insert into cat select * from #tem;
drop table #tem;
--疯狂复制表的所有行，记得实际的要（实际影响的行*2）测试语句的效率用提高档次
create table test
(
testId int primary key identity(1,1),
testName nvarchar(30),
testPW nvarchar(30)
);
drop table test;
insert into test (testName,testPW) values ('hiword','hi');
insert into test (testName,testPW) select testName,testPW from test;
select count(*) from test;  --0秒
select testId from test;    --2秒
select * from test;         --4秒
select top 100000 * from test where testId not in(select top 200000 testId from test order by testId) order by testId;