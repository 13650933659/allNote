--���ߣ��¼���
--���ܣ���Ա����ϵͳ

--������Աϵͳ�����ݿ�
create database helloWord;

--�������ű�
create table dept
(
deptNo int primary key,
deptName nvarchar(30),
loc nvarchar(30)
);
--������Ա��
create table emp
(
empNo int primary key,                           --1���������
empName nvarchar(30),                            --2����Ա����
job nvarchar(30),                                --3����Աְλ
mgr int,                                         --4����Ա�ϼ�
hireDate datetime,                               --5����Ա��ְʱ��
sal numeric(10,2),                               --6����Աнˮ
comm numeric(10,2),                              --7����Ա����
deptNo int foreign key references dept(deptNo)   --8�����ڲ��ţ�������Ҫ�������
);
drop table emp;

        --��dept����Ӽ�¼��
		insert into dept values (10,'����','����');
		insert into dept values (20,'�г���','���');
		insert into dept values (30,'������','����');
		insert into dept values (40,'���۲�','��β');
        --��emp���������
		insert into emp values (1,'������','���񲿳�',5,'2015-08-30',1400.13,400.14,10);
		insert into emp values (2,'������','�г�����',5,'2015-09-30',1300.13,300.14,20);
		insert into emp values (3,'ׯ����','��������',5,'2015-10-30',1200.13,200.14,30);
		insert into emp values (4,'��ӳɯ','���۲���',5,'2015-11-30',1100.13,100.14,40);
		insert into emp values (5,'�¼���','�����鳤',5,'2015-12-30',1000.13,100.14,40);
		insert into emp values (6,'�¼���','�����鳤',5,'2015-12-30',1000,100,40);
		insert into emp values (7,'��ľ��','�����鳤',5,'2015-12-30',1000,null,40);
		insert into emp values (8,'������','�����鳤',null,'2015-12-30',1000,null,40);

--1���鿴ׯ�����ģ�нˮ��ְλ�����ڲ���  <ԭ���ܲ���*�Ͳ�Ҫ�����Ч��>
		select sal,job,deptNo from emp where empName='ׯ����';
--2���鿴�ܹ��ж��ٲ��� <����ѧ����һ�����ظ���֪ʶ�㣺distinct �ֶ�1,�ֶ�2...>
		select distinct deptNo from emp; 
--3���������еĹ�Ա����н<���������õ�һ���������������null����������0>
		select empName,sal*13+isnull(comm,0)*13 "��н" from emp;
--4��������ְʱ����2015-12-2֮ǰ�Ĺ�Ա<�����ʱ��Ƚ�>
		select * from emp where hiredate<'2015-12-2';
--5���鿴нˮ��1000��1200�Ĺ�Ա���<sal>=1000 and sal<=1200 �൱�� sal between 1000 and 1200>
		select * from emp where sal>=1000 and sal<=1200;
		select * from emp where sal between 1000 and 1200;
--6��ģ����ѯ<like>
		  --1������ȫ���չ��Ĺ�Ա��������нˮ
		  select empName,sal from emp where empName like '__��%';
--7��������ѯ<in==or>
  		  --1�����ұ��Ϊ1,3�Ĺ�Ա�������С�᣺������ڲ�������
		  select * from emp where empNo=1 or empNo=3;
		  select * from emp where empNo in(1,3);
		  --2����ʾû���ϼ�����
		  select * from emp where mgr is null;
--8��������<orday by [asc/desc]Ĭ��Ϊasc-->>���԰�ʱ�䣬���֣����ַ�����>
		  --1��һ������������
		  select * from emp order by sal desc;
		  --2������������������������� and нˮ����
		  select * from emp order by deptNo,sal desc;
		  --3��ͳ��ÿ�˵���н�Ӹߵ���<����>
		select empName,(sal+isnull(comm,0))*13 from emp order by (sal+isnull(comm,0))*13 desc; --�������ã��ظ������� 
		select empName,(sal+isnull(comm,0))*13 "��н" from emp order by "��н" desc;
--9���Ӳ�ѯ��Ӧ��<sql����Ǵ�����ִ�е�>
		  --1���ҵ�������͵��Ǹ��˵Ĺ��ʺ�����
		  select empName,sal from emp where sal=(select min(sal) from emp);
		  --2�����������Ա���ܹ��ʺ�ƽ������
		  select sum(sal) "�ܹ���",avg(sal) "ƽ������" from emp;
		  --3���Ѹ���ƽ�����ʵĹ�Ա�ҳ���
		  select empName,sal,(select avg(sal) from emp) "ƽ������" from emp where sal>(select avg(sal) from emp);
		  --4�����ж���Ա��
		  select count(*) "ȫ��Ա��" from emp;
--10���������<group by��having����>
		  --1������ÿ�����ŵ�ƽ������,�ͱ����ŵ���߹�����˭
		  select deptNo,avg(sal) "ƽ������",max(sal) "��߹���" from emp group by deptNo;
		  --2������ÿ�����ŵ�ÿ��ְλ��ƽ������ �ͱ�ְλ����͹�����˭,������
		  select deptNo,job,avg(sal) "ƽ������",min(sal) "��͹���" from emp group by deptNo,job order by deptNo desc;
		  --3����ʾƽ�����ʵ���1300�Ĳ��ź�����ƽ������<having��ɸѡgroup by�����Ľ��>С�ᣬgroup by��having��һ��
		  select deptNo,avg(sal) "ƽ������",min(sal) "��͹���" from emp group by deptNo having(avg(sal)<1300) order by avg(sal) desc;
		  
		--�鿴���ݱ�
		select * from dept;
		select * from emp;
		
		
		
		
