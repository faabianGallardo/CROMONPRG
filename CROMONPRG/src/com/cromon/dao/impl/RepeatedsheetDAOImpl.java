package com.cromon.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cromon.dao.RepeatedsheetDAO;
import com.cromon.entity.Repeatedsheet;
import com.cromon.util.HibernateUtil;

public class RepeatedsheetDAOImpl implements RepeatedsheetDAO{

	@Override
	public void save(Repeatedsheet repetida) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(repetida);
		t.commit();
	}

	@Override
	public Repeatedsheet getRepeatedsheet(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Repeatedsheet) session.load(Repeatedsheet.class, id);
	}

	@Override
	public List<Repeatedsheet> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Repeatedsheet").list();
		t.commit();
		return lista;
	}

	@Override
	public void remove(Repeatedsheet repetida) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(repetida);
		t.commit();
	}

	@Override
	public void update(Repeatedsheet repetida) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(repetida);
		t.commit();
	}

	
}
