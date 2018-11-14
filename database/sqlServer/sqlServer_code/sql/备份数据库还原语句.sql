

--备份还原数据库
  --1、备份数据库语句
  backup database test1 to disk='d:/sp.bak';
  drop database test1;
  --2、还原数据库语句
  restore database test2 from disk='d:/sp.bak';
  select * from table1;
  