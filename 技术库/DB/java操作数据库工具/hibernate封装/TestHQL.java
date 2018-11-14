package com.test;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dao.BasePoManager;
import com.dao.HibernateDBUtil;
import com.entity.Category;
import com.entity.Msg;
import com.entity.Topic;
import com.vo.MsgInfo;

public class TestHQL {

	@BeforeClass
	public static void beforeClass() {
		if (HibernateDBUtil.sessionFactory == null) {
			HibernateDBUtil.sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		}
	}
	
	@AfterClass
	public static void afterClass() {
		if (HibernateDBUtil.sessionFactory != null && !HibernateDBUtil.sessionFactory.isClosed()) {
			HibernateDBUtil.sessionFactory.close();
		}
	}
	
	@Test
	public void testSave() {
		for (int i = 1; i <= 10; i++) {
			Category c = new Category();
			c.setName("c" + i);
			BasePoManager.saveOrUpdate(c);
		}

		for (int i = 1; i <= 10; i++) {
			Category c = new Category();
			c.setId(1L);
			Topic t = new Topic();
			t.setCategory(c);
			t.setTitle("t" + i);
			t.setCreateDate(new Date());
			BasePoManager.saveOrUpdate(t);
		}

		for (int i = 1; i <= 10; i++) {
			Topic t = new Topic();
			t.setId(1);
			Msg m = new Msg();
			m.setContent("m" + i);
			m.setTopic(t);
			BasePoManager.saveOrUpdate(m);
		}
	}
	
	
	/**
	 * 常见的hql
	 */
	@Test
	public void testHQL_01() {
		// 1、<from Category>
		// 2、<from Category c order by c.name desc>
		// 3、<from Category c where c.name > 'c5'>
		// 4、<select distinct c from Category c order by c.name desc>
		// 5、<from Topic t where t.category.id = 1>
		// 6、<from Msg m where m.topic.category.id = 1>
		// 7、<select new com.vo.MsgInfo(m.id, m.content, m.topic.title, m.topic.category.name) from Msg m>
		// 8、<select t.title, c.name from Topic t join t.category c>关联关系在实体类中已经设置了，得到的是一个List<Object[]>
		// 9、<from Msg m where m.id between 3 and 5>
		// 10、<from Msg m where m.id in (3,4,5)>
		// 11、<from Msg m where m.content is not null>
		// 11、<from Topic t where t.msgs is empty>
		// 12、<from Topic t where t.title like '%5'>
		// 13、<select lower(t.title),upper(t.title),concat(t.title, '***'),length(t.title) from Topic t >
		// 14、<select current_date, current_time, current_timestamp, t.id from Topic t >
		// 15、<select t.title, count(*) from Topic t group by t.title having count(*) >= 1 >
		// 16、<from Topic t where not exists (select m.id from Msg m where m.topic.id=t.id) >能用exists就不要用in
		List<Object> list = BasePoManager.executeQueryByHql("from Topic t where not exists (select m.id from Msg m where m.topic.id=t.id)");
		for(Object o : list) {
			Object[] arr = (Object[])o;
			System.out.println(arr[0]);
			System.out.println(arr[1]);
		}
	}
	
	/**
	 * 占位符和参数的使用方法
	 */
	@Test
	public void testHQL_02() {
		// 1、<from Category c where c.id > :min and c.id < :max> 这句hql的参数(叫占位符)要这样设置q.setInteger("min", 2).setInteger("max", 8)
		List<Object> list = BasePoManager.executeQueryByHql("from Category c where c.id > ? and c.id < ?", new Object[]{2, 8});
		for(Object o : list) {
			Category c = (Category) o;
			System.out.println(c.getName());
		}
	}
	
	/**
	 * 函数的使用和uniqueResult的使用
	 */
	@Test
	public void testHQL_03() {
		// 1、<Msg m = new Msg();m.setId(1L);BasePoManager.uniqueResult("from Msg m where m = ? ", new Object[]{m});>
		// 2、<select max(m.id), min(m.id), avg(m.id), sum(m.id) from Msg m>
		// 3、<select count(*) from Msg>
		Object[] arr = (Object[])BasePoManager.uniqueResultByHql("select max(m.id), min(m.id), avg(m.id), sum(m.id) from Msg m", null);
		System.out.println(arr[0] + "-" + arr[1] + "-" + arr[2] + "-" + arr[3]);
	}
	
	/**
	 * update的hql
	 */
	@Test
	public void testHQL_04() {
		// 1、<update Topic t set t.title = upper(t.title)>
		BasePoManager.executeUpdateByHql("update Topic t set t.title = upper(t.title)", null);
	}
	
	/**
	 * sql
	 */
	@Test
	public void testSQL_01() {
		// 1、<select * from msg>
		Object o = BasePoManager.uniqueResultBySql(Msg.class, "select * from msg m where m.id=?", new Object[]{1});
		System.out.println(o);
	}
	
	
}
