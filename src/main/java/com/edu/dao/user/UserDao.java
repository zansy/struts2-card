package com.edu.dao.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.edu.dao.DaoHibernate;
import com.edu.dao.IBaseDao;
import com.edu.db_util.HibernateUtil;
import com.edu.model.user.User;
public class UserDao extends DaoHibernate<User> {
    public User findBynameAndPassword(User user) {
    	String hql="from User u where u.userName=? and u.userPassword=?";
    	String param[]= {user.getUserName(),user.getUserPassword()};
    	User user1=this.findOne(hql, param);
    	return user1;
    }
    public User findByname(User user) {
    	String hql="from User u where u.userName=?";
    	String param[]= {user.getUserName()};
    	User user1=this.findOne(hql, param);
    	return user1;
    }
    public int updatePassword(User user,String newPassword) {
    	User user1=this.findBynameAndPassword(user);
    	user1.setUserPassword(newPassword);
    	return this.update(user1);
    }
}
