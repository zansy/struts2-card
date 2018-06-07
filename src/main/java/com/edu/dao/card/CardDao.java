package com.edu.dao.card;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.dao.DaoHibernate;
import com.edu.dao.IBaseDao;
import com.edu.db_util.HibernateUtil;
import com.edu.model.card.Card;

public class CardDao extends DaoHibernate<Card>{
	public List<Card> findByCondition(String condition,String flag){
		String hql="from Card ";
		String cardFields[]= {"name","sex","department","mobile","phone","email","address"};
		List<Card> cards1=this.findByFields(hql, cardFields, condition);
		List<Card> cards2=new ArrayList<Card>();
		for(Card card:cards1) {
			if(card.getFlag().equals(flag)) {cards2.add(card);}
		}
		return cards2;
	}
	public Card findById(int id,String flag) {
		Card cards2=findById(Card.class,id);
		if(cards2.getFlag().equals(flag)) {return cards2;} else {return null;}
	}
	public int delete(int id) {
		return this.delete(Card.class,id);
	}
	public int deleteList(int[] ids) {
		return this.deleteList(Card.class,ids);
	}
	public int retrieve(int... ids) {
		for(int id:ids) {
			Card card=this.findById(Card.class,id);
			card.setFlag("1");
			this.update(card);
		}
		return ids.length;
	}
	public int revert(int... ids) {
		for(int id:ids) {
			Card card=this.findById(Card.class,id);
			card.setFlag("0");
			this.update(card);
		}
		return ids.length;
	}
}