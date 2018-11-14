

1、通用的case when then写法
	1、case when statusSequence is null then 0 else statusSequence end ASC,time DESC  //把null转成0,好像还有一个方法可以把null转成0但是不知道会不会通用（要注意在排序时o的null是最大的数字，s的null是最小的数字）
	2、select * from aa a where a.name=case when a.name='a' then 'a' else 'b' end;
	3、select * from aa a where case when a.name is not null then a.name else a.alias end='a';

2、的数值型如果是'' （sqlServer他是0，mysql他报错，oracle他已经把''直接当成null处理了）

3、select * from aa a where a.name not in(null,'cjr');  //这样查不出数据来的，用时注意

4、各个数据库的判空（即：''和null）（oracle和mysql用length,sqlserver用len）


4、各个数据库的修改字段长度