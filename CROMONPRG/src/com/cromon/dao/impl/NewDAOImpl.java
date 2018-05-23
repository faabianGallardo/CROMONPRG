package com.cromon.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cromon.dao.NewDAO;
import com.cromon.entity.New;

import com.cromon.util.HibernateUtil;

public class NewDAOImpl implements NewDAO{

	@Override
	public void save(New noticia) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(noticia);
		t.commit();
	}

	@Override
	public New getNew(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (New) session.load(New.class, id);
	}

	@Override
	public List<New> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from New").list();
		t.commit();
		return lista;
	}

	@Override
	public void remove(New noticia) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(noticia);
		t.commit();
	}

	@Override
	public void update(New noticia) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(noticia);
		t.commit();
	}

	

}
