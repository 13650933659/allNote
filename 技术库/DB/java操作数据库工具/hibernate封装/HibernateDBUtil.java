package com.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


/**
 * 数据库的工具类
 */
final public class HibernateDBUtil {

	/**
	 * sessionFactory整个应用一个就够了，除非如果多数据库
	 */
	public static SessionFactory sessionFactory = null;
	
	static{
		sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
	}
	
	
}
