--���ߣ��¼���
--ʱ�䣺2015-12-30
--���ܣ���Ա����ϵͳ�Ķ���ѯ

--1����ʾ�������۲���Ա������������нˮ�������ڲ��ŵ�����
select e.deptNo,empName,sal from emp "e",dept "d" where deptName='���۲�' and e.deptNo=d.deptNo;
--3����ʾ���ź�Ϊ40�ģ���������Ա��������нˮ
select d.deptNo,d.deptName,e.empName,e.sal from emp e,dept d where e.deptNo=d.deptNo and e.deptNo=40;
--4����ʾÿ��Ա�����ϼ����Լ�������<˼·��emp�������ű�worker��boss>
select b.empName �ϰ�,w.empName Ա�� from emp w,emp b where w.mgr=b.empNo;���������Ǹ�û���ϼ��Ĳ���ʾ
--�����о�һ�������Ӻ�����������,��������where,��������<left join/right join>,on
--������
select * from stu s,exam e where s.id1=e.id1;
--������
  --��������
  select * from stu s left join exam e on s.id1=e.id1;
  --��������
  select * from stu s right join exam e on s.id1=e.id1;

--5���Ӳ�ѯ
  --1�������Ӳ�ѯ,��ʾ���ľ��ͬ���ŵ�����Ա��
  select * from emp where deptNo=(select deptNo from emp where empName='��ľ��');
  --2���ҳ��Ͳ���40��ְλ��ͬ�ģ�Ա����������λ��нˮ������ ����Ҫ�ų������ŵ�����Ա����and deptNo!=40��<����in/not in>
  select empName,job,sal,deptNo from emp where job in(select distinct job from emp where deptNo=40);
  --3��form�Ӿ��е��Ӳ�ѯ,��ʾ�����ڲ���ƽ�����ʵ�Ա���ģ�������нˮ������ƽ�����ʣ��Ͳ��ű��
  select e.empName,e.sal,tem.myavg,e.deptNo from emp "e",(select avg(sal) "myavg",deptNo from emp group by deptNo) "tem" 
  where e.deptNo=tem.deptNo and e.sal>tem.myavg;
--6����ҳ��ѯ
  --1���ҳ�����������������Ա��������ְʱ���Ⱥ�������������
  select top 4 * from emp where empNo not in(select top 2 empNo from emp order by hiredate) order by hiredate;




--7�������⣬���ɾ��һ�����ظ��ļ�¼��,˼·
���ʮ����ǰ���빫˾��Ա��
select * from emp where datediff(year,hireDate,getdate())>12;
  --��1�� �Ȱ��ظ��ļ�¼�ϲ��ٷŵ�һ����ʱ��,��2�� ��ɾ����������м�¼Ҫ��delete,
  --��3�� �ñ�����ȥ������ʱ��ļ�¼����4�� ��ȥɾ����ʱ������Ҫ��drop��
create table cat
(
catId int,
catName nvarchar(30)
)
insert into cat values (2,'rmimi');
select * from cat;

--�����£�
select distinct * into #tem from cat;
delete from cat;
insert into cat select * from #tem;
drop table #tem;
--����Ʊ�������У��ǵ�ʵ�ʵ�Ҫ��ʵ��Ӱ�����*2����������Ч������ߵ���
create table test
(
testId int primary key identity(1,1),
testName nvarchar(30),
testPW nvarchar(30)
);
drop table test;
insert into test (testName,testPW) values ('hiword','hi');
insert into test (testName,testPW) select testName,testPW from test;
select count(*) from test;  --0��
select testId from test;    --2��
select * from test;         --4��
select top 100000 * from test where testId not in(select top 200000 testId from test order by testId) order by testId;