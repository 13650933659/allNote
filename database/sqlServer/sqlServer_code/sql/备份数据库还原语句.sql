

--���ݻ�ԭ���ݿ�
  --1���������ݿ����
  backup database test1 to disk='d:/sp.bak';
  drop database test1;
  --2����ԭ���ݿ����
  restore database test2 from disk='d:/sp.bak';
  select * from table1;
  