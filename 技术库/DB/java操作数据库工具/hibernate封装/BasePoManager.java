package com.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.Session;

/**
 * 最好把此工具类定义成接口，给不同的类去实现，在这为了方便，就直接实现了
 */
public class BasePoManager {

	/**
	 * 根据id加载对象（懒加载）
	 * @param clazz	a persistent class
	 * @param id	a valid identifier of an existing persistent instance of the class
	 * @return
	 */
	public static Object load(Class clazz, Serializable id){
		Session session = null;
		Transaction t = null;
		Object o = null;
		try{
			// 这里如果你确定有这个人就用load，用load可能一定要做成事务方法，高效于get
			session = HibernateDBUtil.sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			o = session.load(clazz, id);
			
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(t != null){
				t.rollback();
			}
			throw new RuntimeException("加载失败");
		}finally{
			if(session != null && session.isOpen()){	// 这里可以不用写了，但是写了也没事
				session.close();
			}
		}
		return o;
	}
	
	/**
	 * 保存获取更新对象，如果数据库有此对象则更新，没有则保存
	 * @param o	 a transient or detached instance containing new or updated state
	 */
	public static void saveOrUpdate(Object o){
		Session session = null;
		Transaction t = null;
		try{
			session = HibernateDBUtil.sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			
			session.saveOrUpdate(o);
			
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(t!=null){
				t.rollback();
			}
			throw new RuntimeException("保存或者更新失败");
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
	}
	
	
	// ------------------------------------ 下面是使用hql
	
	/**
	 * 执行更新或者删除的hql
	 * @param hql		更新或者删除的hql
	 * @param params	预编译的参数集合
	 */
	public static void executeUpdateByHql(String hql, Object[] params){
		Session session = null;
		Transaction t = null;
		try{
			session = HibernateDBUtil.sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			
			Query query = session.createQuery(hql);
			if(params != null){
				for(int i = 0; i < params.length; i++){
					query.setParameter(i, params[i]);
				}
			}
			query.executeUpdate();
			
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(t != null){
				t.rollback();
			}
			throw new RuntimeException("更新失败");
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
	}

	/**
	 * 使用hql查询
	 * @param hql	hql
	 * @return
	 */
	public static List<Object> executeQueryByHql(String hql){
		return executeQueryByHql(hql, null, -1, -1);
	}
	
	/**
	 * 使用hql查询
	 * @param hql		hql
	 * @param params	预编译的参数集合
	 * @return
	 */
	public static List<Object> executeQueryByHql(String hql, Object[] params){
		return executeQueryByHql(hql, params, -1, -1);
	}
	
	/**
	 * 使用hql查询
	 * @param hql		hql
	 * @param params	预编译的参数集合
	 * @param start		结果集的开始下标(从0开始)，必须大于等于0否返回全部结果集
	 * @param limit		需要获取的总数，必须大于0否返回全部结果集
	 * @return
	 */
	public static List<Object> executeQueryByHql(String hql, Object[] params, int start, int limit){
		Session session = null;
		Transaction t = null;
		List<Object> list = null;
		try{
			session = HibernateDBUtil.sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			Query query = session.createQuery(hql);
			if(params != null){
				int len = params.length;
				for (int i = 0; i < len; i++) {
					query.setParameter(i, params[i]);
				}
			}
			//查询
			if (start >= 0 && limit > 0) {
				query.setFirstResult(start).setMaxResults(limit);
			}
			list = query.list();
			
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(t != null){
				t.rollback();
			}
			throw new RuntimeException("查询失败");
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return list;
	}
	
	/**
	 * 使用hql查询(返回单个对象实例)
	 * @param hql		hql
	 * @param params	预编译的参数集合
	 * @return
	 */
	public static Object uniqueResultByHql(String hql, Object[] params){
		Session session = null;
		Transaction t = null;
		Object o = null;
		try{
			session = HibernateDBUtil.sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			Query query = session.createQuery(hql);
			if(params != null){
				int len = params.length;
				for (int i = 0; i < len; i++) {
					query.setParameter(i, params[i]);
				}
			}
			o = query.uniqueResult();
			
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(t != null){
				t.rollback();
			}
			throw new RuntimeException("查询失败");
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return o;
	}
	
	
	// ------------------------------------ 下面是使用本地的sql
	
	/**
	 * 使用sql查询
	 * @param sql sql
	 * @return
	 */
	public static List<Object> executeQueryBySql(String sql) {
		return executeQueryBySql(null, sql, null, -1, -1);
	}
	
	/**
	 * 使用sql查询
	 * @param sql sql
	 * @return
	 */
	public static List<Object> executeQueryBySql(String sql, Object[] params) {
		return executeQueryBySql(null, sql, params, -1, -1);
	}
	
	/**
	 * 使用sql查询
	 * @param sql		sql
	 * @param params	预编译的参数集合
	 * @param start		结果集的开始下标(从0开始)，必须大于等于0否返回全部结果集
	 * @param limit		需要获取的总数，必须大于0否返回全部结果集
	 * @return
	 */
	public static List<Object> executeQueryBySql(String sql, Object[] params, int start, int limit) {
		return executeQueryBySql(null, sql, params, start, limit);
	}
	
	/**
	 * 使用sql查询
	 * @param klass	映射的实体类，如果映射成具体的实体类就要查此实体类的全部字段，否则抛异常，不需要做映射则可以查部分字段
	 * @param sql 	sql
	 * @return
	 */
	public static List<Object> executeQueryBySql(Class<?> klass, String sql) {
		return executeQueryBySql(klass, sql, null, -1, -1);
	}
	
	/**
	 * 使用sql查询
	 * @param sql sql
	 * @return
	 */
	public static List<Object> executeQueryBySql(Class<?> klass, String sql, Object[] params) {
		return executeQueryBySql(klass, sql, params, -1, -1);
	}
	
	/**
	 * 使用sql查询
	 * @param klass		映射的实体类，如果映射成具体的实体类就要查此实体类的全部字段，否则抛异常，不需要做映射则可以查部分字段
	 * @param sql		sql
	 * @param params	预编译的参数集合
	 * @param start		结果集的开始下标(从0开始)，必须大于等于0否返回全部结果集
	 * @param limit		需要获取的总数，必须大于0否返回全部结果集
	 * @return
	 */
	public static List<Object> executeQueryBySql(Class<?> klass, String sql, Object[] params, int start, int limit) {
		Session session = null;
		Transaction t = null;
		List<Object> list = null;
		try{
			session = HibernateDBUtil.sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			if (klass != null) {
				query = query.addEntity(klass);
			}
			if(params != null){
				int len = params.length;
				for (int i = 0; i < len; i++) {
					query.setParameter(i, params[i]);
				}
			}
			//查询
			if (start >= 0 && limit > 0) {
				query.setFirstResult(start).setMaxResults(limit);
			}
			list = query.list();
			
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(t != null){
				t.rollback();
			}
			throw new RuntimeException("查询失败");
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return list;
	}
	
	/**
	 * 使用sql查询(返回一条记录)
	 * @param klass		映射的实体类，如果映射成具体的实体类就要查此实体类的全部字段，否则抛异常，不需要做映射则可以查部分字段
	 * @param sql		sql
	 * @param params	预编译的参数集合
	 * @return
	 */
	public static Object uniqueResultBySql(Class<?> klass, String sql, Object[] params){
		Session session = null;
		Transaction t = null;
		Object o = null;
		try{
			session = HibernateDBUtil.sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			if (klass != null) {
				query = query.addEntity(klass);
			}
			if(params != null){
				int len = params.length;
				for (int i = 0; i < len; i++) {
					query.setParameter(i, params[i]);
				}
			}
			o = query.uniqueResult();
			
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(t != null){
				t.rollback();
			}
			throw new RuntimeException("查询失败");
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return o;
	}
	
	/**
	 * 执行更新或者删除的sql
	 * @param sql		更新或者删除的sql
	 * @param params	预编译的参数集合
	 */
	public static void executeUpdateBySql(String sql, Object[] params){
		Session session = null;
		Transaction t = null;
		try{
			session = HibernateDBUtil.sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			
			SQLQuery query = session.createSQLQuery(sql);
			if(params != null){
				for(int i = 0; i < params.length; i++){
					query.setParameter(i, params[i]);
				}
			}
			query.executeUpdate();
			
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(t != null){
				t.rollback();
			}
			throw new RuntimeException("更新失败");
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
	}
	
}
