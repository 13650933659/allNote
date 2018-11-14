


1、关系映射（铁律：规律：凡是双向关联，要设mappedBy，不然在items上面也加@JoinColumn(name = "group1_id")） 
	1、一对一关联:		
		1、一对一单向外键关联：在Husband类中的getWife配置
			@OneToOne
			@JoinColumn(name="wifeid") //指定生成的数据库字段名，不用次注解默认是wife_id
			public Wife getWife() {
				return wife;
			}
		2、一对一双向外键关联（双向关联是指java程序的关系，数据库还是一个外键）
			1、在Husband类中的getWife配置
				@OneToOne
				@JoinColumn(name="wifeid")
				public Wife getWife() {
					return wife;
				}
			2、并在Wife类中的getHusband配置
				@OneToOne(mappedBy="wife")
				public Husband getHusband() {
					return husband;
				}
		3、一对一单向主键关联（不重要，可以不用看了）
			1、@primaryKeyJoinColumn  //好像是bug，他没把我们建立主键关联（不管）
		4、一对一双向主键关联（不重要，可以不用看了）
			1、@primaryKeyJoinColumn
	2、多对一与一对多
		1、多对一单向关联：只需要在多的一端User的getGroup配置
			@ManyToOne
			@JoinColumn(name="groupid")
			public Group getGroup() {
				return group;
			}
		2、一对多单向关联：只需要在一的这一端Group的getUsers配置
			@OneToMany
			@JoinColumn(name="groupid") //Hibernate默认将OneToMany理解为ManyToMany的特殊形式，如果不指定生成的外键列，则会默认生成多对多的关系,产生一张中间表。
			public List<User> getUsers() {
				return users;
			}
		3、一对多（多对一）双向关联
			1、一般以多的一端为主,先配置多的一端,在多的一端User的getGroup配置
				@ManyToOne
				@JoinColumn(name="groupid")
				public Group getGroup() {
					return group;
				}
			2、在一的一端Group的getUsers配置
				@OneToMany(mappedBy="group")
				public List<User> getUsers() {
					return users;
				}
	3、多对多
		1、单向关联：例如：老师和学生的关系，老师需要知道自己教了哪些学生，只需要在Teacher类中的getStudents配置：
			@ManyToMany //多对多关联 Teacher是主的一方 Student是附属的一方 
			@JoinTable(
				name="t_s", //指定中间表表名
				joinColumns={@JoinColumn(name="teacherid")},//本类在中间表生成的外键
				inverseJoinColumns={@JoinColumn(name="studentid")}//对方类主键在中间表生成的对应字段名
			)
			public Set<Student> getStudents(){
				return students;
			}

		2、双向关联：老师知道自己教了哪些学生，学生也知道教自己的有哪些老师
			1、在Teacher这一端的students上配置
				@JoinTable(
					name="t_s",
					joinColumns={@JoinColumn(name="teacherid")},
					inverseJoinColumns={@JoinColumn(name="studentid")}
				)
				public Set<Student> getStudents(){
					return students;
				}	
			2、在Student一端的teachers只需要配置
				@ManyToMany(mappedBy="students")
				public Set<Teacher> getTeachers(){
					return teachers;
				}








2、关联关系中的CRUD：@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
	1、CUD归cascade管，R归fetch管
	2、fetch（如果是eager单个对象会立即加载关联对象）
		1、铁律：双向不要两边设置Eager(会有多余的査询语句发出)
		2、一般默认就好，@ManyToOne的fetch默认FetchType.EAGER，@OneToMany是FetchType.LAZY
		3、对多方设置fetch的时候要谨慎，结合具体应用，一般用Lazy不用eager，特殊情况（多方数量不多的时候可以考虑，提高效率的时候可以考虑）
	3、Delete和Update时@ManyToOne(cascade={CascadeType.ALL})这里即可关联更新
	4、三种方法可避免全部删除的情况：
		1、去掉@ManyToOne(cascade={CascadeType.All})设置；
		2、直接写Hql语句执行删除；
		3、将他们关联的对象设置为空，打断关系

3、继承映射（不太重要，他的CRUD有空再去研究）
	1、三种方式(如果非用不可，请考虑用1,3方法，不要用多态去load，他会连接去查询造成浪费，最好能用具体的某一个子类去load)
		1、一张总表SINGLE_TABLE(只会生成一张表)，请参考hibernate_1900_lnheritence_Mapping_Single_Table
		2、每个类分别一张表(包括父类和子类)TABLE_PER_CLASS，请参考hibernate_2000_lnheritence_Mapping_Table_Per_Class
		3、每个子类一张表jOINED(包括父类生成3张表)，请参考hibernate_2100_lnheritence_Mapping_JOINED


