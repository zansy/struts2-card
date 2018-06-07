package com.edu.db_util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public final class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static ThreadLocal<Session> sess = new ThreadLocal<Session>();
	static {
		// 1.创建configuration，目的读取hibernate.cfg.xml，默认位置在classpath根
		// Configuration config = new Configuration().configure();		
		// 2.创建sessionFactory
		// ServiceRegistry serR = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build(); 
		// configures settings from hibernate.cfg.xml
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble
			// building the SessionFactory
			// so destroy it manually.
			
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
		}
	}

	public static Session getThreadLocalSession() {
		Session s = sess.get();
		if (s == null) {
			s = sessionFactory.openSession();
			sess.set(s);
		}
		return s;
	}

	public static void closeSession() {
		Session s = sess.get();
		if (s != null) {
			s.close();
			sess.set(null);
		}
	}

}
