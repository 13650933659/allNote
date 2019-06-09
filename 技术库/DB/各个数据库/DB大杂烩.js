



1、sqlserver数据库
	1、//sqlserver游标
		declare @userId numeric(19,0) 
		declare mycursor cursor for select id from ss_users

		open mycursor
		fetch next from mycursor into @userId      
		while (@@fetch_status=0)  
		begin      
			insert into ss_user_role(user_id,role_id) values(@userId,2)
			fetch next from mycursor into @userId  
		end
		close mycursor  
		deallocate mycursor


2、mysql 查看最大允许的包
	show VARIABLES like '%max_allowed_packet%';