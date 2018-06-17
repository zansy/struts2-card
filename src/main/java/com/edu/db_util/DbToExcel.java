package com.edu.db_util;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.dao.DaoHibernate;
import com.edu.model.card.Card;
import com.edu.model.user.User;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DbToExcel {

	public static void excelToDb(String excelpath, String table, String fieldList, int columnCount) throws Exception {
		Workbook workbook = null;
		Sheet sheet = null;
		String sql = "insert into " + table + " " + fieldList + " values(";
		for (int i = 1; i < columnCount; i++) {
			sql += "?,";
		}
		sql += "?)";
		workbook = Workbook.getWorkbook(new File(excelpath));
		sheet = workbook.getSheet(0);
		int r = sheet.getRows();
		for (int i = 1; i < r; i++) {
			Session session= HibernateUtil.getThreadLocalSession();
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.addEntity(Card.class);
			
			for(int j=0;j<columnCount;j++) {			
				sqlQuery.setParameter(j+1, sheet.getCell(j+1, i).getContents().toString());
			}
			Transaction tx =session.beginTransaction();
			int f=sqlQuery.executeUpdate();
			tx.commit();
			if(f>=1) {
				System.out.println("update completed!");
			}else {
				System.out.println("Failed to update.");
			}
		}
		workbook.close();
}

	public static void dBToExcel(String table, String[] fieldList, String[] titles, String condition, String order,
			String file) throws Exception {
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		String flist = "";
		int fl = fieldList.length;
		for (int i = 0; i < fl - 1; i++) {
			flist += fieldList[i] + ",";
		}
		flist += fieldList[fieldList.length - 1];
		String sql = "select " + flist + " from " + table + "  where 1=1 ";
		if (condition != null && !condition.equals("")) {
			sql = sql + " and " + condition;
		}
		if (order != null && !order.equals("")) {
			sql = sql + "order by " + order;
		}
		wwb = Workbook.createWorkbook(new File(file));
		ws = wwb.createSheet("sheet1", 0);
		for (int i = 0; i < fl; i++) {
			ws.addCell(new Label(i, 0, titles[i]));
		}
		Session session= HibernateUtil.getThreadLocalSession();
		Transaction tx=session.beginTransaction();
		SQLQuery sqlQuery=session.createSQLQuery(sql);
		sqlQuery.addEntity(Card.class);
		List<Card> list=sqlQuery.list();
		int count = 1;
		for(Card card:list) {
				ws.addCell(new Label(0, count, Integer.toString(card.getId())));
				ws.addCell(new Label(1, count, card.getName()));
				ws.addCell(new Label(2, count, card.getSex()));
				ws.addCell(new Label(3, count, card.getDepartment()));
				ws.addCell(new Label(4, count, card.getMobile()));
				ws.addCell(new Label(5, count, card.getPhone()));
				ws.addCell(new Label(6, count, card.getEmail()));
				ws.addCell(new Label(7, count, card.getAddress()));
				ws.addCell(new Label(8, count, card.getFlag()));
			count++;
		}
		wwb.write();
		tx.commit();
		if (wwb != null) {
			wwb.close();
		}
	}
}