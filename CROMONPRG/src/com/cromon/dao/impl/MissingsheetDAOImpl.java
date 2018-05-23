package com.cromon.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cromon.dao.MissingsheetDAO;
import com.cromon.entity.Missingsheet;

import com.cromon.util.HibernateUtil;

public class MissingsheetDAOImpl implements MissingsheetDAO{

	@Override
	public void save(Missingsheet faltante) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(faltante);
		t.commit();
		
	}

	@Override
	public Missingsheet getMissingsheet(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Missingsheet)session.load(Missingsheet.class, id);
	}

	@Override
	public List<Missingsheet> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Missingsheet").list();
		t.commit();
		return lista;
	}

	@Override
	public void remove(Missingsheet faltante) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(faltante);
		t.commit();
	}

	@Override
	public void update(Missingsheet faltante) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(faltante);
		t.commit();
	}
}

