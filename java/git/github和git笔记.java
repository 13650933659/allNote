
1、git和github是什么
	1、git是分布式的代码版本控制工具(即：每一台客户端都有一个本地库，这样就很好的避免了单点故障)
	2、github是全球最大的编程社区和代码托管网站，借助git来管理项目代码
	3、我们可以使用github和码云两个远程仓库
2、github的一些基本概念
	1、仓库：用来存放项目代码的，每个项目对应一个仓库
	2、收藏：收藏该项目，方便下次查看
	3、克隆项目(fork)：比如张三的项目被我从github中点击fork，fork之后我们就会多一个仓库独立的，下次我们要提交代码需要pull request(区别于git clone)
	4、pull request：我们发起pull request请求，等待主人同意，就会把我们的代码同步到仓库中
	5、关注(watch)：关注之后，该项目有变化可以接收到通知
	6、事务卡片(Issue)：发现代码BUG，但是目前没有成型代码，需要讨论时用
3、git的基本原理
	1、我们先了解一下哈希算法
		1、哈希算法是一系列的加密算法，git底层是使用SHA-1的哈希算法
	2、文件管理原理
		1、集中式：增量式的保存，每次只保留修改的部分，这样很好了节省存储空间
		2、分布式：以快照流的形式，每次修改git都会把全部文件制作一个快照并且创建对应的索引，但是那些没被修改的文件不会重新保存，而是创建一个引用指向原来的文件
	3、分支管理原理
		1、集中式：他是把全部文件复制一份
		2、分布式：他是创建指针指向原来的文件，每一个指针可以理解成分支，互相独立，这样效率很高，切换分支就很快了


4、git工具的下载安装
	1、下载地址：https:\\www.git-scm.com/download/win
	2、安装注意以下几点，其他默认
		1、至少要选择：Git bash Here 和 Git GUI Here
		2、到Adjusting your PATH environment选择：Use Git from Git Bash only
	3、检验是否安装成功：到桌面鼠标右击看到：Git Bash Here即安装成功
5、git的使用
	1、git的工作流程
		1、工作区：即我们项目的本地工作空间
		2、暂存区：暂存已经修改的文件，等待最后统一提交到git仓库
		3、git仓库：最终文件保存到仓库中（本地库和远程库）
	2、设置签名(区分不同的用户可以乱设置，如果和github账号名一样，提交日志可以看对对应账号的图片)：项目级别签名>系统级别签名
		1、项目级别签名(用户名)：git config user.name '13650933659'(如果要设置系统级别的加上参数：--global )
		2、项目级别签名(邮箱)：git config user.email '1282654205@qq.com'(如果要设置系统级别的加上参数：--global )
		3、查看基本信息：git config --list
		4、项目级别的信息保存到.git/config文件，系统级别的保存到~/.gitconfig文件
		5、为了省事直接设置系统级别的就行了
	3、管理本地仓库案例
		1、创建test01目录：mkdir test01
		2、cd到test01初始化：git init，看到隐藏的目录.git，即成功
		3、添加修改文件，如果是修改直接可以提交(全程可以使用git status查看仓库的状态)
			1、工作区：touch a.java
			2、添加到暂存区：git add a.java
			3、提交到本地仓库：git conmmit -m '提交描述'
		4、删除文件
			1、工作空间删除：rm -rf a.java
			2、缓存区删除：git rm a.java
			3、提交到本地仓库：git conmmit -m '提交描述'
	4、本地仓库和远程仓库的交互
		1、李岳群去github注册一个账号，并且创建仓库命名为：huashan
		2、设置系统/项目的签名
		3、创建huashan目录，进入huashan目录参数本地仓库： git init
		4、创建文件提交到本地仓库：vim a.txt -> git add a.txt -> git commit "描述"
		5、由于远程仓库地址长，所以我们记一下(会记录到.git/config文件)
			1、查看当前所有的远程地址：git remote -v
			2、添加远程仓库地址：git remote add [名称] [远程地址]
				1、例子：git remote add origin http://192.168.2.170:3000/bxkc/zhongzhao-frontend.git
			3、删除远程仓库地址：git remote remove 名称
		6、把远程库拉下来：git pull origin master
		7、如果6报错使用：git pull origin master --allow-unrelated-histories(建立他们的关联，并且会把新代码拉下来，)
		8、把我们本地仓库的代码推送到远程仓库：git push origin master(如果报错，要先执行6,7，或者要把文件提交到本地仓库)
		9、李岳群自己玩没意思，邀请令狐冲来参与项目
			1、进入github指定的仓库->setting->collaborators->add collaborator->然后把邀请链接发给令狐冲让他同意邀请既可
			2、然后创建lhc目录给令狐冲用，进入lhc目录启动git bash
			3、令狐冲把项目克隆下来：git clone https://github.com/13650933659/huashan.git
				1、克隆：完成了本地仓库的初始化，远程仓库地址的添加，得到一个项目目录huashan，令狐冲进入lhc/huashan/目录就可以协同开发了
			4、令狐冲进入lhc/设置自己的项目签名：git config user.name "1765050742" -> git config user.email "1765050742@qq.com"
			5、令狐冲使用：git push origin master提交代码，但是这次要使用自己的github账号(区别签名)
		10、岳不群同步令狐冲提交的代码(pull=fetch+merge)
			1、fetch+merge的方式同步代码（这样比较安全，冲突多了就使用这种方式）
				1、git fetch [远程库地址别名] [远程库分支]（这样是把远程仓库下载下来得到一个origin/master但是他存在在哪里呢？可以能在远程吧）
				2、切换到需要合并的分支上：git merge 远程库地址别名/远程库分支
			2、直接使用pull（不太安全）
		11、如果有冲突(修改的是同一行)：
			1、第一种解决：执行合并之后，一般他会自动进入编辑冲突的状态，给我们自己手动合并，再次提交
			2、第二种解决(冲突难解决)：使用：git merge –abort，可以回滚到刚刚没合并之前的状态
		12、团队外的合作，比如：岳不群叫令狐冲去开发葵花宝典，lhc求助东方不败做，这时dfbd先去岳不群那里fork项目到自己的github
			-> clone到本地 -> 再提交代码到github -> 再发送pull request->岳不群处理pull request手动合并，也可以聊天
		13、免密的登录(解决每次登录都要输入密码，麻烦)
			1、使用http的方式：修改。git/config文件一下内容既可
				[remote "origin"]  
					url = https://github.com/用户名/仓库名.git => url = https://用户名:密码@github.com/用户名/仓库名.git
			2、使用ssh的方式
				1、设置ssh库的地址：git remote origin_ssh ssh地址
				2、使用命令创建私钥和秘钥： ssh-keygen -t rsa -C 1282654205@qq.com
				3、这时会产生~/.ssh/id_rsa.pub文件是公钥，把里边的内容复制，id-rsa是你的私钥
				4、github -> settings -> SSH and GPG keys -> 点击“add SSH key”按钮 -> 把id_rsa.pub(公钥)的全部内容复制到这里即可
				5、提交代码到远程(ssh)：git push origin_ssh master
	5、git log：查看提交日志，默认以more命令查看
		--pretty=oneline/--oneline：一行显示
		--reflog：查看版本的head，可以用于版本前进后退
	6、本地版本的回退前进：git reset会删除之前的本地库的提交，你git push可能导致冲突(如果本地版本落后于远程)
		1、语法
			1、基于索引(推荐)
				1、git reset --hard 索引值
			2、使用^(只能回退)
				2、git reset --hard HEAD^^^退回三个版本
			3、使用~(只能回退)
				2、git reset --hard HEAD~3退回三个版本
		2、参数的说明
			1、--hard：移动本地仓库，重置暂存区，重置工作区
			2、--mixed：移动本地库，重置暂存区(默认)
			3、--soft：仅仅移动本地库
	7、比较
		1、git diff a.txt：和暂存区比较（如果不指定文件，就比较全部）
		2、git diff [历史版本] a.txt：和本地库比较（这应该可以指定版本的）
		3、本地库和远程库怎么比较呢？拉下来比较吗？还是说直接远程比较？
	8、版本分支管理
		1、概念：版本控制中使用多条线同时推进多个任务，他创建分支只是创建一个指针指向主分支，这样性能很高(区别svn)
		2、git branch -v：查看有多少分支
		3、git branch hit_fix：创建分支
		4、git checkout hot_fix：切换分支
		5、合并分支合并hot_fix
			1、切换到需要合并的分支上使用：git merge hot_fix：
		6、解决冲突
			1、如果有冲突他会自动进入合并的状态，自己去修改编辑冲突
			2、编辑好冲突之后使用git add a.txt -> git commit(这里不要带文件名)
	9、eclipse使用git：Team->Add to Index(相当于git add)(在没有冲突的情况下可以直接使用Team->Commit他会自动帮我们git add)
		1、eclipse初始化为本地仓库
			1、创建maven的的web项目(自己去弄)
			2、工程->右键->Team->Share Project->Git-选中Use or crete repository in parent folder of project
				->选中我们的项目->Create Repository->Finish
		2、忽略文件
			1、eclipse的特定文件(.classpath、.project、.settings目录)
			2、去官网找对应语言的忽略描述：https://github.com/github/gitignore
			3、和~/.gitconfig同级创建一个Java.gitignore文件(即：针对java项目需要忽略的文件)
			4、在~/.gitconfig文件引用上述文件
				[core]
					excludesfile=~/Java.gitignore
			5、重启eclipse既可
		3、把项目对应的本地仓库和远程仓库建立关系
			1、先创建远程库地址(也可以使用eclipse直接编辑.git/config文件)：git remote add origin 远程库地址
			2、一定要先拉取：Team->pull
			3、Team->remote->push->远程库地址填上->next->Add All branchs Spec(检测可以push的分支，也可以直接指定)->next/finish
		4、从远程库克隆成为本地的maven项目
			1、import->projects from Git->Clone URI->填写远程库地址->选远程库的那个分支->Directory(选择我们拉下来的代码存放目录)
				->指定工程导入方式，这里只能用：Import as general project->默认和远程库一样的名称(可以改的)->Configure
				->Convert to Maven Project->完成
		5、解决冲突(最好不要直接使用pull，如冲突多了就麻烦了，使用fetch+merge，可能还可以把本地库和远程库直接比较？？？)
			1、pull之后可以使用Team->Merger Tool来编辑冲突
			2、也可以直接Team->Commit->添加到缓存区冲突就会消失(老师的eclipse可以直接在这里拖拽，我这里不行)->push到远程库
		6、分支工作流
			1、令狐冲创建本地库的分支hot_fix->Team->Switch To->New Branch->写上名称hot_fix，然后在此分支修改代码
				->push到远程库，这时远程库会创建一个分支hot_fix
			2、李岳群需要合并那个hos_fix，...->Switch To->Other(因为那个hot_fix在远程)->Remote Tracking->选择origin/hot_fix->Check Out
				->Check out as New Branch->创建完成	//我的eclipse在other->remote tracking找不到远程的hot_fix
				在本地真正的合并hot_fix：切换为master分支->Team->Merge->选择Local下面的hot_fix分支->合并完成
6、gitlab服务器：就像github那样可以在我们局域网搭建提高访问速度
	1、安装说明，安装过程很慢请耐心等待：https://about.gitlab.com/installation/
		1、官网推荐安装(ee版)，(网速好可以这样，最好使用第二种安装)
			1、新建一个gitlabInstall.sh文件添加一下命令
				sudo yum install -y curl policycoreutils-python openssh-server cronie
				sudo lokkit -s http -s ssh
				sudo yum install postfix
				sudo service postfix start
				sudo chkconfig postfix on
				curl https://packages.gitlab.com/install/repositories/gitlab/gitlab-ee/script.rpm.sh | sudo bash
				sudo EXTERNAL_URL="http://gitlab.example.com" yum -y install gitlab-ee
			2、改成可以执行的文件：chmod 755 gitlabInstall.sh
			3、执行文件(安装)：./gitlabInstall
		2、去下载rpm包到/opt/目录下（ce版的）：https://packages.gitlab.com/gitlab/gitlab-ce/packages/el/7/gitlab-ce-10.8.2-ce.0.el7.x86_64.rpm
			1、新建一个gitlabInstall.sh文件添加一下命令
				sudo rpm -ivh /opt/gitlab-ce-10.8.2-ce.0.el7.x86_64.rpm
				sudo yum install -y curl policycoreutils-python openssh-server cronie
				sudo lokkit -s http -s ssh
				sudo yum install postfix
				sudo service postfix start
				sudo chkconfig postfix on
				curl https://packages.gitlab.com/install/repositories/gitlab/gitlab-ce/script.rpm.sh | sudo bash
				sudo EXTERNAL_URL="http://gitlab.example.com" yum -y install gitlab-ce
			2、改成可以执行的文件：chmod 755 gitlabInstall.sh
			3、执行文件(安装)：./gitlabInstall
	2、初始化配置gitlab：gitlab-ctl reconfigure
	3、使用 
		1、启动gitlab 服务：gitlab-ctl start
		2、停止gitlab 服务：gitlab-ctl stop
		3、linux这边开放80端口
		4、其他成员访问gitlab首页：访问Linux服务器IP地址即可，
		5、初次登录时需要为gitlab 的root 用户设置密码
		6、后续的使用和github一样
5、Github Pages 搭建网站(以后有空再去弄)

问题
	1、回滚此次的push
		1、第一种：你直接在本地删除错误的代码，再次提交(这种如果文件多就麻烦了)
		2、第二种：git revert是用一次新的commit来回滚之前的commit，再次提交(类似第一种了)
		3、第三种：git reset但是会删除之前的本地库的提交，你git push可能导致冲突(如果本地版本落后于远程)
	2、git rebase(衍合操作)，一般我使用git merge最终效果是一样的，整洁的提交历史
	3、修改提交信息
		1、 已经提交到git仓库的 git 修改提交信息 https://www.cnblogs.com/dudu/p/4705247.html
		2、 还未提交到git仓库的
			git commit --amend	// 然后进入vim模式编辑既可以(但是这是最后一次的提交)

	


	mapZhongbiao = neo4jUtils.finOrgZhongbiaoOrg(    // 245 就是上一次版本的， 370才是当前版本的