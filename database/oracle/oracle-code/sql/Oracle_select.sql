--oracle��ѯ
  --1.�򵥲�ѯ
  --����һ��ÿ��Ա������н
  select ename, (sal+nvl(comm,0))*13 from emp;
  --����������1982-1-1�Ժ���ְ��Ա��
  select * from emp where to_char(hiredate,'yyyy-mm-dd')>'1982-1-1';
  --��������������2000��2500��
  select * from emp where sal between 2000 and 2500;
  --�����ģ���ʾ��������ĸΪA��Ա��
  select * from emp where ename like 'A%';
  --�����壺�ҳ������� 123,124,156��Ա��
  select * from emp where empno in (123,124,156);
  --����������ʾû���ϼ���Ա��
  select * from emp where mgr is null;
  --�����ߣ�ȫ��Ա����нˮ����Ĭ�ϵ�
  select * from emp order by sal asc;
  --�����ˣ���нˮ������ְʱ�併��
  select * from emp order by sal asc,hiredate desc;
  
  --2.���Ӳ�ѯ
  --����һ����͹��ʣ���߹��ʣ�ƽ�����ʣ��ܹ��ʣ�������ƽ�����������ú�һ�㣺sum(sal)/count(*)=avg(sal)��
  select min(sal) mi,max(sal) ma,avg(sal) av,sum(sal) su where emp;
  --����������߹��ʵ��Ǹ�Ա��
  select * from where sal=(select max(sal) from emp);
  --��������ÿ�����ŵ�ƽ������
  select avg(sal),deptno from emp group by deptno;
  --�����ģ�ƽ�����ʵ���2000�Ĳ��ţ����ű�ź�����ƽ������,��������oracle�������ñ���
  select avg(sal),deptno from emp group by deptno having avg(sal)<2000;
  
  --3.����ѯ
  --����һ��10�Ų��ŵ�ȫ��Ա��
  select e.ename,d.dname from emp e,dept d where e.deptno=d.deptno and e.deptno=10;
  
  --4.�����Ӳ�ѯ�������ӣ�������������������ȫ������
  --����һ����ʾ����Ա�����ϼ����������Լ�������
  select w.ename,b.ename from emp w,emp b  where w.mgr=b.empno;
  --����������ʾ����Ա�����ϼ����������Լ�������,��û���ϼ���ҲҪ��ʾ�����������ȫ����ʾ��û�о�null����
  select w.ename,b.ename from emp w left join emp b  on w.mgr=b.empno;/select w.ename,b.ename from emp w,emp b  where w.mgr=b.empno(+);
  --����������ʾ����Ա�����ϼ����������Լ�������,��û���¼���ҲҪ��ʾ���������ұ�ȫ����ʾ��û�о�null����
  select w.ename,b.ename from emp w right join emp b  on w.mgr=b.empno;/select w.ename,b.ename from emp w,emp b  where w.mgr(+)=b.empno;   
  --�����ģ���ʾ����Ա�����ϼ����������Լ�������,��û���ϼ�û���¼��Ķ�Ҫ��ʾ����ȫ��������ȫ����ʾ��ƥ�䲻����null����
  select w.ename,b.ename from emp w full outer join emp b  on w.mgr=b.empno;
  
  --5.�Ӳ�ѯ����һ��֪ʶ�㲻ѧ�ˣ�all=max(sal)/any=min(sal)��
  --����һ���������Ӳ�ѯ����ʾԪSYITHͬ���ŵ�Ա��
  select * from emp where deptno=(select deptno from emp where ename='SMITH');
  --���������������Ӳ�ѯ-��ͨ����ʾ��10�Ų�����ͬ������λ��Ա��
  select * from emp where job=(select distinct job from emp where deptno=10);
  --�����ģ��������Ӳ�ѯ-��ͨ����ʾ��SMITH����,������λ��ͬ��Ա��������oracle���е�,insert ʱҲ���������ɡ�
  select * from emp where (deptno,job)=(select deptno,job from emp where ename='SMITH');
  --�����壺�������Ӳ�ѯ-from�Ӿ䡷��ʾ�����Լ�����ƽ�����ʵ�Ա��
  select * from emp t1,(select avg(sal) sa,deptno from emp group by deptno) t2 where t1.deptno=t2.deptno and t1.sal>t2.sa;
  --���������������Ӳ�ѯ-from�Ӿ䡷��ʾ�����Լ�������߹��ʵ�Ա��
  select * from emp t1,(select max(sal) ma,deptno from emp group by deptno) t2 where t1.deptno=t2.deptno and t1.sal>t2.ma;
  --�����ߣ��������Ӳ�ѯ-from�Ӿ�-��ҳ��ѯ������嵽��ʮ����¼���á�create table test as (select* from emp)/insert into test (select* from emp);����Ч�ʡ�
  select t2.* from (select t1.*,rownum rn from (select * from emp) t1 where rownum<=10) t2 where rn>=5;
  
  
  
