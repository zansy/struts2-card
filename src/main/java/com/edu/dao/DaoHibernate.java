package com.edu.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.db_util.HibernateUtil;

public class DaoHibernate<T> implements IBaseDao<T> {
	public int insert(T o) {
		Session s=null;
		Transaction tx=null;
		int result=0;
		try {
			s=HibernateUtil.getThreadLocalSession();
			tx=s.beginTransaction();
			s.save(o);
			tx.commit();
			result=1;
		}catch(Exception e) {
			if(tx!=null) {tx.rollback();}
		}finally {
			HibernateUtil.closeSession();
		}
		return result;
	}
	public int insertList(List<T> list) {
		for(T t:list) {insert(t);}
		return list.size();
	}
	public int update(T o) {
		Session s=null;
		int result=0;
		Transaction tx=null;
		try {
			s=HibernateUtil.getThreadLocalSession();
			tx=s.beginTransaction();
			s.update(o);
			tx.commit();
			result=1;
		}catch(Exception e) {if(tx!=null) {tx.rollback();}
		}finally {
			HibernateUtil.closeSession();
		}
		return result;
	}
	public int deleteList(Class c,int...ids){
		for(int id:ids) {delete(c,id);}
		return ids.length;
	}
	public int delete(T o) {
		Session s=null;
		int result=0;
		Transaction tx=null;
		try {
			s=HibernateUtil.getThreadLocalSession();
			tx=s.beginTransaction();
			s.delete(o);
			tx.commit();
			result=1;
		}catch(Exception e) {if(tx!=null) {tx.rollback();}
		}finally {
			HibernateUtil.closeSession();
		}
		return result;
	}
	public int delete(Class c,int id) {
		Session s=null;
		int result=0;
		Transaction tx=null;
		try {
			s=HibernateUtil.getThreadLocalSession();
			tx=s.beginTransaction();
			s.delete(s.load(c, id));
			tx.commit();
			result=1;
		}catch(Exception e) {if(tx!=null) {tx.rollback();}
		}finally {
			HibernateUtil.closeSession();
		}
		return result;
	}
	public T findById(Class c,int id) {
		Session s=null;
		T t=null;
		try {
			s=HibernateUtil.getThreadLocalSession();
			t=(T)s.get(c, id);
		}finally {
			HibernateUtil.closeSession();
		}
		return t;
	}
	public T findOne(String hql,String[] param) {
		Session s=null;
		T t=null;
		try {
			s=HibernateUtil.getThreadLocalSession();
			Query query=s.createQuery(hql);
			if(param!=null) {
				for(int i=0;i<param.length;i++) {
					query.setParameter(i, param[i]);
				}
			}
			t=(T)query.uniqueResult();
		}finally {
			HibernateUtil.closeSession();
		}
		return t;
	}
	public List<T> find(String hql,String[] param) {
		Session s=null;
		List<T> list=null;
		try {
			s=HibernateUtil.getThreadLocalSession();
			Query query=s.createQuery(hql);
			if(param!=null) {
				for(int i=0;i<param.length;i++) {
					query.setParameter(i, param[i]);
				}
			}
			list=query.list();
		}finally {
			HibernateUtil.closeSession();
		}
		return list;
	}
	public List<T> findPage(String hql,String[] param,int page,int size) {
		Session s=null;
		List<T> list=null;
		try {
			s=HibernateUtil.getThreadLocalSession();
			Query query=s.createQuery(hql);
			if(param!=null) {
				for(int i=0;i<param.length;i++) {
					query.setParameter(i, param[i]);
				}
			}
			query.setFirstResult((page-1)*size);
			query.setMaxResults(size);
			list=query.list();
		}finally {
			HibernateUtil.closeSession();
		}
		return list;
	}
	public int getCount(String hql,String[] param) {
		Session s=null;
		int resu=0;
		try {
			s=HibernateUtil.getThreadLocalSession();
			Query q=s.createQuery(hql);
			if(param!=null) {
				for(int i=0;i<param.length;i++) {
					q.setString(i, param[i]);
				}
			}
			resu=Integer.valueOf(q.iterate().next().toString());
		}finally {
			HibernateUtil.closeSession();
		}
		return resu;
	}
	public List<T> findByFields(String hql,String fields[],String condition) {
		Session s=null;
		String findhql=hql;
		if(fields!=null&&condition!=null&&fields.length>0&&!condition.equals("")) {
			findhql=findhql+" where 1=1 and (";
			for(int i=0;i<fields.length-1;++i) {
				findhql+= fields[i]+" like '%"+condition +"%' or ";
			}
			findhql+=fields[fields.length-1]+" like '%"+condition +"%')";
		}
		try {
			s=HibernateUtil.getThreadLocalSession();
			Query query=s.createQuery(findhql);
			List<T> list=query.list();
			return list;
		}finally {
			HibernateUtil.closeSession();
		}
	}

}
