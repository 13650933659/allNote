--oracle查询
  --1.简单查询
  --案例一：每个员工的年薪
  select ename, (sal+nvl(comm,0))*13 from emp;
  --案例二：在1982-1-1以后入职的员工
  select * from emp where to_char(hiredate,'yyyy-mm-dd')>'1982-1-1';
  --案例三：工资在2000到2500的
  select * from emp where sal between 2000 and 2500;
  --案例四：显示名字首字母为A的员工
  select * from emp where ename like 'A%';
  --案例五：找出部门是 123,124,156的员工
  select * from emp where empno in (123,124,156);
  --案例六：显示没有上级的员工
  select * from emp where mgr is null;
  --案例七：全部员工按薪水升序默认的
  select * from emp order by sal asc;
  --案例八：按薪水升序，入职时间降序
  select * from emp order by sal asc,hiredate desc;
  
  --2.复杂查询
  --案例一：最低工资，最高工资，平均工资，总工资，《慎用平均工资这样用好一点：sum(sal)/count(*)=avg(sal)》
  select min(sal) mi,max(sal) ma,avg(sal) av,sum(sal) su where emp;
  --案例二：最高工资的那个员工
  select * from where sal=(select max(sal) from emp);
  --案例三：每个部门的平均工资
  select avg(sal),deptno from emp group by deptno;
  --案例四：平均工资低于2000的部门，部门编号和他的平均工资,好像这里oracle不可以用别名
  select avg(sal),deptno from emp group by deptno having avg(sal)<2000;
  
  --3.多表查询
  --案例一：10号部门的全部员工
  select e.ename,d.dname from emp e,dept d where e.deptno=d.deptno and e.deptno=10;
  
  --4.自连接查询《内连接，左外莲，右外连，完全外连》
  --案例一：显示所有员工的上级的姓名和自己的姓名
  select w.ename,b.ename from emp w,emp b  where w.mgr=b.empno;
  --案例二：显示所有员工的上级的姓名和自己的姓名,《没有上级的也要显示，左外莲左表全部显示，没有就null补》
  select w.ename,b.ename from emp w left join emp b  on w.mgr=b.empno;/select w.ename,b.ename from emp w,emp b  where w.mgr=b.empno(+);
  --案例三：显示所有员工的上级的姓名和自己的姓名,《没有下级的也要显示，右外莲右表全部显示，没有就null补》
  select w.ename,b.ename from emp w right join emp b  on w.mgr=b.empno;/select w.ename,b.ename from emp w,emp b  where w.mgr(+)=b.empno;   
  --案例四：显示所有员工的上级的姓名和自己的姓名,《没有上级没有下级的都要显示，完全外连两表全部显示，匹配不到就null补》
  select w.ename,b.ename from emp w full outer join emp b  on w.mgr=b.empno;
  
  --5.子查询《有一个知识点不学了：all=max(sal)/any=min(sal)》
  --案例一：《单行子查询》显示元SYITH同部门的员工
  select * from emp where deptno=(select deptno from emp where ename='SMITH');
  --案例三：《多行子查询-普通》显示与10号部门相同工作岗位的员工
  select * from emp where job=(select distinct job from emp where deptno=10);
  --案例四：《多行子查询-普通》显示与SMITH部门,工作岗位相同的员工《可能oracle特有的,insert 时也可以这样吧》
  select * from emp where (deptno,job)=(select deptno,job from emp where ename='SMITH');
  --案例五：《多行子查询-from子句》显示高于自己部门平均工资的员工
  select * from emp t1,(select avg(sal) sa,deptno from emp group by deptno) t2 where t1.deptno=t2.deptno and t1.sal>t2.sa;
  --案例六：《多行子查询-from子句》显示等于自己部门最高工资的员工
  select * from emp t1,(select max(sal) ma,deptno from emp group by deptno) t2 where t1.deptno=t2.deptno and t1.sal>t2.ma;
  --案例七：《多行子查询-from子句-分页查询》查第五到第十条记录，用《create table test as (select* from emp)/insert into test (select* from emp);测试效率》
  select t2.* from (select t1.*,rownum rn from (select * from emp) t1 where rownum<=10) t2 where rn>=5;
  
  
  
