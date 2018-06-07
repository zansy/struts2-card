package com.edu.action.user;

import com.edu.dao.user.UserDao;
import com.edu.model.user.User;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{
private User user;

private String re_password;
private String msg;
private UserDao userDao=new UserDao();
public String userLogin()throws Exception{
	
	String forward=null;
	User user2=userDao.findBynameAndPassword(user);
	if(user2!=null){
		forward="success";
		user.setUserRealName(user2.getUserRealName());
		
	}else{
		msg="用户不存在或者密码不正确，登录失败，请重新登录或者注册！";
	    forward="failure";
	}
	return forward;
	}
	public String userRegister()throws Exception{
		String forward=null;
		int flag=0;
		User user2=(userDao.findByname(user));
		if(user2!=null&&(user2.getUserName().trim()).equals((user.getUserName()).trim())){
		msg="该用户已经存在，请重新注册！";
		forward="error";
		}else{
			flag=userDao.insert(user);
		    if(flag==1){forward="success";
		}else{msg="数据库写错误！！";forward="error";}
	
		}
		return forward;
	
		
		
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRe_password() {
		return re_password;
	}
	public void setRe_password(String re_password) {
		this.re_password = re_password;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}	
}